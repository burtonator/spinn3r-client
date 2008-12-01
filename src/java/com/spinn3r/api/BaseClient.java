/*
 * Copyright 2007 Tailrank, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * For more information see:
 * 
 * <a href="http://tailrank.com">http://tailrank.com</a>
 * <a href="http://spinn3r.com">http://spinn3r.com</a>
 * <a href="http://feedblog.org">http://feedblog.org</a>
 */

package com.spinn3r.api;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * Generic client support used which need to be in all APIs.
 *
 * See Main.java for usage example.
 * 
 * All implementations need to catch and handle Exceptions.
 *
 * <h2>Restarting</h2>
 *
 * When stopping/starting the API you need to persist an 'after' point where to
 * start the URL from again.  This is just the timestamp of the most recent URL
 * you found minus a buffer.
 *
 * 
 * 
 */
public abstract class BaseClient implements Client {

    /**
     * Maximum number of retries.
     */
    public static long RETRY_MAX = 1;

    /**
     * Go back in time to make sure we recrawl everything.
     */
    public static final int RESTART_BUFFER = 30 * 1000;
    
    public static final String NS_API     = "http://tailrank.com/ns/#api";
    public static final String NS_DC      = "http://purl.org/dc/elements/1.1/" ;
    public static final String NS_ATOM    = "http://www.w3.org/2005/Atom" ;
    public static final String NS_WEBLOG  = "http://tailrank.com/ns/#weblog" ;
    public static final String NS_SOURCE  = "http://tailrank.com/ns/#source" ;
    public static final String NS_POST    = "http://tailrank.com/ns/#post" ;
    public static final String NS_FEED    = "http://tailrank.com/ns/#feed" ;
    public static final String NS_LINK    = "http://spinn3r.com/ns/link" ;
    public static final String NS_TARGET  = "http://spinn3r.com/ns/#target" ;
    
    public static final String USER_AGENT_HEADER       = "User-Agent";
    public static final String ACCEPT_ENCODING_HEADER  = "Accept-Encoding";

    public static String FEED_HANDLER       = "feed3";
    public static String PERMALINK_HANDLER  = "permalink3";
    public static String COMMENT_HANDLER    = "comment3";
    public static String LINK_HANDLER       = "link3";
    
    public static final String GZIP_ENCODING = "gzip";

    // Would be nice to have this use String.format() but this isn't really
    // compatible back to Java 1.4.. are we requiring Java 1.5 now?

    public static String USER_AGENT = String.format( "Spinn3r API Reference Client %s (Java %s)",
                                                     Config.DEFAULT_VERSION,
                                                     System.getProperty( "java.version" ) );
        
    /**
     * Default hostname for building the router URL.  This can be changed to
     * use an additional dedicated host if necessary.
     */
    public static String DEFAULT_HOST = "api.spinn3r.com";
    
    /**
     * Specified in java.security to indicate the caching policy for successful
     * name lookups from the name service.. The value is specified as as integer
     * to indicate the number of seconds to cache the successful lookup.
     * 
     *
     * sun.net.inetaddr.ttl:
     * 
     * This is a sun private system property which corresponds to
     * networkaddress.cache.ttl. It takes the same value and has the same meaning,
     * but can be set as a command-line option. However, the preferred way is to
     * use the security property mentioned above.
     * 
     * A value of -1 indicates "cache forever".
     */
    public static int NETWORKADDRESS_CACHE_TTL = 5 * 60;

    /**
     * These properties specify the default connect and read timeout (resp.) for
     * the protocol handler used by java.net.URLConnection.
     * 
     * sun.net.client.defaultConnectTimeout specifies the timeout (in
     * milliseconds) to establish the connection to the host. For example for
     * http connections it is the timeout when establishing the connection to
     * the http server. For ftp connection it is the timeout when establishing
     * the connection to ftp servers.
     * 
     * sun.net.client.defaultReadTimeout specifies the timeout (in milliseconds)
     * when reading from input stream when a connection is established to a
     * resource.
     */
    public static int DEFAULT_CONNECT_TIMEOUT = 5 * 60 * 1000;

    /**
     * Lower read timeout.  Makes NO sense to wait for five minutes to read a
     * byte from spinn3r.
     */
    public static int DEFAULT_READ_TIMEOUT = DEFAULT_CONNECT_TIMEOUT;

    /**
     * Specify the maximum number of redirects to use.
     */
    public static int DEFAULT_MAX_REDIRECTS = 5;

    /**
     * Whether we should use HTTP Keep Alive in java.net.URL.  We default to
     * true here because MOST of our TCP connections WILL be used again since
     * everything is to spinn3r.com.
     */
    public static boolean DEFAULT_HTTP_KEEPALIVE = true;

    private String lastRequestURL = null;
    private String nextRequestURL = null;

    /**
     * Total time we took calling a method.
     */
    private long callDuration = -1;

    /**
     * How long actually we slept (duration) while performing the last API call.
     */
    private long sleepDuration = -1;
    
    protected List results = new ArrayList();

    protected Config config = null;

    /**
     * True if the last API call was compressed.
     */
    boolean isCompressed = false;

    /**
     * Get the last localInputStream we're using.  This is a
     * ByteArrayInputStream based InputStream.
     */
    InputStream localInputStream = null;

    /**
     * The host for calling API methods.
     */
    String host = DEFAULT_HOST;

    /**
     * Sample performance times...
     */
    BandwidthSampler bs1   = new BandwidthSampler( 1L  * 60L * 1000L );
    BandwidthSampler bs5   = new BandwidthSampler( 5L  * 60L * 1000L );
    BandwidthSampler bs15  = new BandwidthSampler( 15L * 60L * 1000L );

    // **** fetching support ****************************************************

    /**
     * Get the InputStream for dealing with the XML of the API directly.  This
     * is a LOCAL input stream so once fetch() has been called you can call this
     * API multiple times and you're reading from a local buffer.
     */
    public InputStream getInputStream() throws IOException {

        InputStream is = localInputStream;

        //the first item we don't need this but we do need it for additional
        //calls.
        is.reset();
        
        //wrap the downloaded input stream with a gzip input stream when
        //necessary.
        if ( isCompressed ) {

            //NOTE: this is a bug fix for Apache2.  If mod_compress is disabled
            //during LIVE http connections the result won't be compressed
            //content.  The GZIPInputStream class will first attempt to read the
            //gzip magic number in its constructor.  If the magic number is
            //incorrect then it will throw an exception.
            try {
                InputStream gz = new GZIPInputStream( is );
                is = gz;
            } catch ( IOException e ) {

                //TODO: are we going to add log4j support?
                //log.warn( "Detected invalid gzip stream.  Using uncompressed stream.", e );
                //reset since GZIPInputStream might have read some content
                is.reset();
                
            }
            
        }

        return is;

    }

    public void fetch( Config config ) throws IOException,
                                              ParseException,
                                              InterruptedException {

        int retry_ctr = 0;

        while( true ) {

            try {
            
                // set the optimial limit if necessary
                if ( retry_ctr == 0 )
                    config.setLimit( getLimit() );
                else
                    config.setLimit( getConservativeLimit() );
                
                doFetch( config );
                break;
                
            } catch ( Exception e ) {
                
                //revert limit to conservative values.
                if ( retry_ctr < RETRY_MAX ) {
                    ++retry_ctr;
                    continue;
                }

                //this is slightly ugly but prevents nested exceptions.
                if ( e instanceof IOException )
                    throw (IOException)e;

                if ( e instanceof ParseException )
                    throw (ParseException)e;

                if ( e instanceof InterruptedException )
                    throw (InterruptedException)e;

                IOException ioe = new IOException();
                ioe.initCause( e );
                throw ioe;

            }
            
        }
        
    }
    
    /**
     * Fetch the API with the given FeedConfig
     * 
     * @throws IOException if there's an error with network transport.
     * @throws ParserException if there's a problem parsing the resulting XML.
     */
    private void doFetch( Config config ) throws IOException,
                                                 ParseException,
                                                 InterruptedException {

        if ( config.getVendor() == null )
            throw new RuntimeException( "Vendor not specified" );

        String resource = getNextRequestURL();

        setSleepDuration( 0 );

        int requestLimit = config.getLimit();

        //enforce max limit so that we don't generate runtime exceptions.
        if ( requestLimit > getMaxLimit() )
            requestLimit = getMaxLimit();
        
        if ( resource == null ) {

            resource = config.getFirstRequestURL();

            // if the API has NEVER been used before then generate the first
            // request URL from the config parameters.
            if ( resource == null )
                resource = generateFirstRequestURL();

        } else if ( results.size() < requestLimit ) {

            long sleepInterval = config.getSleepInterval();
            
            //we've fetched before so determine if we need to spin.
            Thread.sleep( sleepInterval );
            setSleepDuration( sleepInterval );
            
        } 

        //apply the requestLimit to the current URL.  This needs to be done so
        //that we can change the limit at runtime.  When I originally designed
        //the client I didn't want to support introspecting and mutating the URL
        //on the client but with the optimial limit performance optimization
        //this is impossible.
        resource = setParam( resource, "limit", requestLimit );

        // store the last requested URL so we can expose this to the caller for
        // debug purposes.

        setLastRequestURL( resource );

        try {
            parse( doFetch( resource ) );
        } catch ( Exception e ) {
            throw new ParseException( e );
        }

    }

    public Document doFetch( String resource ) throws IOException,
                                                      ParseException,
                                                      InterruptedException {

        URLConnection conn = null;
        
        try {

            // create the HTTP connection.
            URL request = new URL( resource );
            conn = request.openConnection();

            // set the UserAgent so Spinn3r know which client lib is calling.
            conn.setRequestProperty( USER_AGENT_HEADER, USER_AGENT );
            conn.setRequestProperty( ACCEPT_ENCODING_HEADER, GZIP_ENCODING );
            
            long call_before = System.currentTimeMillis();
            
            conn.connect();

            //NOTE: because this is XML we don't need to use the Content-Type
            //returned by the HTTP server as the XML encoding declaration should be
            //used.

            localInputStream = getLocalInputStream( conn.getInputStream() );

            if ( GZIP_ENCODING.equals( conn.getContentEncoding() ) )
                isCompressed = true;

            InputStream is = getInputStream();
            
            long call_after = System.currentTimeMillis();

            setCallDuration( call_after - call_before );

            // now get the system XML parser using JAXP

            DocumentBuilderFactory docBuildFactory =
                DocumentBuilderFactory.newInstance();

            //namespaces won't work at ALL if this isn't enabled.
            docBuildFactory.setNamespaceAware( true );
            
            DocumentBuilder parser =
                docBuildFactory.newDocumentBuilder();
            
            // parse the document into a DOM.... I'd like to use JDOM here but
            // it's yet another lib to support and we want to keep things thin
            // and lightweight.
            //
            // Another advantage to DOM is that it's very portable.

            long before = System.currentTimeMillis();
            
            Document doc = parser.parse( is );

            long after = System.currentTimeMillis();
            
            return doc;

        } catch ( IOException ioe ) {

            //create a custom exception message with the right error.
            String message = conn.getHeaderField( null );
            IOException ce = new IOException( message );
            ce.setStackTrace( ioe.getStackTrace() );
            
            throw ce;

        } catch ( Exception e ) {

            String message = String.format( "Unable to parse %s: %s" , getLastRequestURL(), e.getMessage() );

            ParseException pe = new ParseException( message );
            pe.initCause( e );
            
            throw pe;
        }
        
    }
    
    /**
     * Get a local copy of the input stream so we can benchmark the download
     * time.
     */
    private InputStream getLocalInputStream( InputStream is ) throws IOException {
        return new ByteArrayInputStream( getInputStreamAsByteArray( is ) );
    }
    
    /**
     * Get the input stream as a byte array.
     */
    private byte[] getInputStreamAsByteArray( InputStream is ) throws IOException {

        //include length of content from the original site with contentLength
        ByteArrayOutputStream bos = new ByteArrayOutputStream( 500000 ); /* reasonable sized buffer */
      
        //now process the Reader...
        byte data[] = new byte[2048];
    
        int readCount = 0;

        int total = 0;
        
        while( ( readCount = is.read( data )) > 0 ) {
            bos.write( data, 0, readCount );
            total += readCount;
        }

        bs1.sample( total );
        bs5.sample( total );
        bs15.sample( total );
        
        is.close();
        bos.close();

        return bos.toByteArray();

    }

    /**
     * We've received a response from the API so parse it out.
     *
     */
    protected void parse( Document doc ) throws Exception {

        Element root = (Element)doc.getFirstChild();

        Element channel = getElementByTagName( root, "channel" );

        //determine the next_request_url so that we can fetch the second page of
        //results.
        setNextRequestURL( getElementCDATAByTagName( channel, "next_request_url", NS_API ) );

        List result = new ArrayList();

        NodeList items = root.getElementsByTagName( "item" );

        for( int i = 0; i < items.getLength(); ++i ) {

            Element current = (Element)items.item( i );
            
            result.add( parseItem( current ) );
            
        }

        this.results = result;
        
    }

    /**
     * Generate the first request URL based just on configuration directives.
     */
    protected String generateFirstRequestURL() {

        StringBuffer params = new StringBuffer( 1024 ) ;

        int limit = config.getLimit();
        
        if ( limit > getMaxLimit() )
            limit = getMaxLimit();
        
        addParam( params, "limit",   limit );
        addParam( params, "vendor",  config.getVendor() );
        addParam( params, "version", config.getVersion() );

        if ( config.getSpamProbability() != Config.DEFAULT_SPAM_PROBABILITY )
            addParam( params, "spam_probability", config.getSpamProbability() );
        
        //AFTER param needs to be added which should be ISO8601
        addParam( params, "after",   toISO8601( config.getAfter() ) );

        //add optional params
        //OBSOLETE/REMOVED
        //addParam( params, "lang", config.getLang(), true );

        /*
        if ( config.getTierStart() >= 0 ) {
            String param_tier = "" + config.getTierStart() + ":" + config.getTierEnd();
            addParam( params, "tier", param_tier );
        }
        */

        if ( config.getSkipDescription() ) {
            addParam( params, "skip_description", "true" );
        }

        String result = getRouter() + params.toString();

        //System.out.printf( "FIXME: %s\n", result );
        
        return result;
        
    }

    /**
     * Return the maximum number of request per call.  If the API has more
     * content this might cause an error on our end if the limit is more than 10
     * or so.
     */
    protected int getMaxLimit() {
        return 10;
    }

    /**
     * Return the optimal limit for fetches. This is used to boost performance
     * in some situations.  We have to resort to the conservative limit if the
     * HTTP server can't handle the result set size.
     */
    protected int getOptimalLimit() {
        return 10;
    }
    
    /**
     * Conservative limit for items which should work in all situations (but
     * might be slower)
     */
    protected int getConservativeLimit() {
        return 10;
    }
    
    /**
     * Return the router for this client.  Right now this is either:
     * 
     * http://api.spinn3r.com/rss/feed.getDelta?
     * 
     * or
     * 
     * http://api.spinn3r.com/rss/permalink.getDelta?
     * 
     */
    public abstract String getRouter();
    
    /**
     * Parse an individual item which might be specific to this client.
     */
    protected BaseResult parseItem( Element current ) throws Exception {
        throw new Exception( "Not implemented" );
    }

    protected BaseResult parseItem( Element current,
                                    BaseResult result ) throws Exception {
        
        BaseItem item = (BaseItem)result;
        
        //base elements.
        item.setTitle( getElementCDATAByTagName( current, "title" ) );

        item.setDescription( getElementCDATAByTagName( current, "description" ) );

        item.setLink( getElementCDATAByTagName( current, "link" )  );
        item.setGuid( getElementCDATAByTagName( current, "guid" )  );

        // dc:lang
        item.setLang( getElementCDATAByTagName( current, "lang", NS_DC ) );

        // dc:source
        item.setSource( getElementCDATAByTagName( current, "source", NS_DC ) );
        
        // weblog:title
        // weblog:description
        item.setWeblogTitle( getElementCDATAByTagName( current, "title", NS_WEBLOG ) );

        item.setWeblogDescription( getElementCDATAByTagName( current, "description", NS_WEBLOG ) );

        String str_tier = getElementCDATAByTagName( current, "tier", NS_WEBLOG );
        
        if ( str_tier != null )
            item.setWeblogTier( Integer.parseInt( str_tier ) );

        String pubDate = getElementCDATAByTagName( current, "pubDate" );
        item.setPubDate( RFC822DateParser.parse( pubDate ) );

        String atom_published = getElementCDATAByTagName( current, "published", NS_ATOM );
        if ( atom_published != null && ! atom_published.equals( "" ) )
            item.setPublished( ISO8601DateParser.parse( atom_published ) );
        
        //FIXME: weblog:iranking

        String weblog_indegree = getElementCDATAByTagName( current, "indegree", NS_WEBLOG );
        if ( weblog_indegree != null )
            item.setWeblogIndegree( Integer.parseInt( weblog_indegree ) );
        
        String publisher_type = getElementCDATAByTagName( current, "publisher_type", NS_WEBLOG );
        if ( publisher_type != null )
            publisher_type = publisher_type.trim();
        
        item.setWeblogPublisherType( publisher_type );

        item.setTags( parseTags( current ) );

        Element author = getElementByTagName( current, "author", NS_ATOM );
        
        if ( author != null ) {

            item.setAuthorName(  getElementCDATAByTagName( author, "name",  NS_ATOM ) );
            item.setAuthorEmail( getElementCDATAByTagName( author, "email", NS_ATOM ) );
            item.setAuthorLink(  getElementCDATAByTagName( author, "link",  NS_ATOM ) );

        }

        // Spinn3r 2.1 post content.

        item.setContentExtract( getElementCDATAByTagName( current,
                                                          "content_extract",
                                                          NS_POST ) );

        item.setCommentExtract( getElementCDATAByTagName( current,
                                                          "comment_extract",
                                                          NS_POST ) );

        item.setFeedURL( getElementCDATAByTagName( current, "url", NS_FEED ) );

        item.setResourceGUID( getElementCDATAByTagName( current, "resource_guid", NS_POST ) );

        //spinn3r 3.x values

        //FIXME: post:timestamp, feed:link
        
        item.setPostTitle( getElementCDATAByTagName( current, "title", NS_POST ) );
        item.setPostBody( getElementCDATAByTagName( current, "body", NS_POST ) );

        item.setPostHashcode( getElementCDATAByTagName( current,   "hashcode", NS_POST ) );
        item.setSourceHashcode( getElementCDATAByTagName( current, "hashcode", NS_SOURCE ) );
        item.setFeedHashcode( getElementCDATAByTagName( current,   "hashcode", NS_FEED ) );

        return item;
        
    }

    // **** XML parsing utilities ***********************************************

    public static int parseInt( String v ) {

        if ( v == null || v.equals( "" ) )
            return 0;

        return Integer.parseInt( v );
        
    }

    public static float parseFloat( String v, float _default ) {

        if ( v == null || v.equals( "" ) )
            return _default;

        return Float.parseFloat( v );

    }
    
    public static List parseTags( Element current ) {
        return parseChildNodesAsList( current, "category" );
    }

    public static List parseChildNodesAsList( Element current, String name ) {

        List result = new ArrayList();
        
        NodeList nodes = current.getElementsByTagName( name );
        
        for( int i = 0; i < nodes.getLength(); ++i ) {

            Element e = (Element)nodes.item( i );

            Node child = e.getFirstChild();
            
            if ( child == null )
                continue;
        
            result.add( child.getNodeValue() );

        }

        return result;
    }

    public static Element getElementByTagName( Element current,
                                               String name ) {
        return getElementByTagName( current, name, null );
    }
    
    public static Element getElementByTagName( Element current,
                                               String name,
                                               String namespace ) {

        NodeList elements = null;

        if ( namespace == null )
            elements = current.getElementsByTagName( name );
        else 
            elements = current.getElementsByTagNameNS( namespace, name );

        if ( elements == null )
            return null;
        
        if ( elements.getLength() != 1 )
            return null;

        return (Element)elements.item( 0 );

    }
    
    public static String getElementCDATAByTagName( Element current, String name ) {

        return getElementCDATAByTagName( current, name, null );
    }

    public static String getElementCDATAByTagName( Element current,
                                                   String name,
                                                   String namespace ) {

        Element e = getElementByTagName( current, name, namespace );

        if ( e == null )
            return null;

        Node child = e.getFirstChild();

        if ( child == null )
            return null;
        
        return child.getNodeValue();

    }

    /**
     * Return a date to an ISO 8601 value for specifying to the URL with an
     * 'after' param.
     */
    public static String toISO8601( Date date ) {

        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        
        Calendar c = Calendar.getInstance( tz );

        c.setTime( date );
        c.setTimeZone( tz );
        
        StringBuffer buff = new StringBuffer();

        buff.append( c.get( Calendar.YEAR ) );
        buff.append( "-" );
        padd( c.get( Calendar.MONTH ) + 1, buff );
        buff.append( "-" );
        padd( c.get( Calendar.DAY_OF_MONTH ), buff );
        buff.append( "T" );
        padd( c.get( Calendar.HOUR_OF_DAY ), buff );
        buff.append( ":" );
        padd( c.get( Calendar.MINUTE ), buff );
        buff.append( ":" );
        padd( c.get( Calendar.SECOND ), buff );
        buff.append( "Z" );

        return buff.toString();
        
    }

    protected static void padd( int v, StringBuffer buff ) {
        if ( v < 10 )
            buff.append( "0" );

        buff.append( v );
        
    }

    // **** URL request handling ************************************************

    public static void addParam( StringBuffer buff,
                                 String name,
                                 Object value ) {
        addParam( buff, name, value, false );
    }

    /**
     * Add a parameter to the first request URL.  After the first call this is
     * no longer needed. 
     */
    public static void addParam( StringBuffer buff,
                                 String name,
                                 Object value,
                                 boolean optional ) {

        if ( optional && value == null )
            return;             
        
        if ( buff.length() > 0 )
            buff.append( "&" );
        
        buff.append( name );
        buff.append( "=" );
        buff.append( value );

    }

    /**
     * Set a parameter in the HTTP URL.
     *
     */
    protected String setParam( String v,
                               String key,
                               Object value ) {

        int start = v.indexOf( String.format( "%s=", key ) );

        if ( start != -1 ) {
            int end = v.indexOf( "&", start );

            if ( end == -1 )
                end = v.length();

            StringBuffer buff = new StringBuffer( v );

            buff.replace( start, end, String.format( "%s=%s", key, value ) );
            return buff.toString();
        }
        
        return v;
        
    }

    // **** Getter and setters **************************************************

    /**
     * 
     * Get the last requested URL for debug and logging purposes.
     *
     */
    public String getLastRequestURL() { 
        return this.lastRequestURL;
    }

    /**
     * 
     * Set the value of <code>lastRequestURL</code>.
     *
     */
    public void setLastRequestURL( String lastRequestURL ) { 
        this.lastRequestURL = lastRequestURL;
    }

    /**
     * 
     * Get the value of <code>nextRequestURL</code>.
     *
     */
    public String getNextRequestURL() { 
        return this.nextRequestURL;
    }

    /**
     * 
     * Set the value of <code>nextRequestURL</code>.
     *
     */
    public void setNextRequestURL( String nextRequestURL ) { 
        this.nextRequestURL = nextRequestURL;
    }

    
//     /**
//      * 
//      * Get the value of <code>result</code>.
//      *
//      */
//     public List<BaseResult> getResults() { 
//         return this.results;
//     }

//     /**
//      * 
//      * Set the value of <code>result</code>.
//      *
//      */
//     public void setResults( List<BaseResult> results ) { 
//         this.results = results;
//     }

    /**
     * 
     * Get the value of <code>config</code>.
     *
     */
    public Config getConfig() { 
        return this.config;
    }

    /**
     * 
     * Set the value of <code>config</code>.
     *
     */
    public void setConfig( Config config ) { 
        this.config = config;
    }

    /**
     * 
     * Get the value of <code>callDuration</code>.
     *
     */
    public long getCallDuration() { 
        return this.callDuration;
    }

    /**
     * 
     * Set the value of <code>callDuration</code>.
     *
     */
    public void setCallDuration( long callDuration ) { 
        this.callDuration = callDuration;
    }

    /**
     * 
     * Get the value of <code>sleepDuration</code>.
     *
     */
    public long getSleepDuration() { 
        return this.sleepDuration;
    }

    /**
     * 
     * Set the value of <code>sleepDuration</code>.
     *
     */
    public void setSleepDuration( long sleepDuration ) { 
        this.sleepDuration = sleepDuration;
    }

    /**
     * Set the host for API alls.
     */
    public void setHost( String v ) {
        this.host = v;
    }

    public String getHost() {
        return host;
    }
    
    /**
     * When the API needs to shutdown you need to call this method FIRST and
     * persist it.  Then when the API starts you need to call config.setAfter()
     * with this value.
     */
    public Date getRestartPoint() {

        if ( results == null || results.size() == 0 )
            return null;

        BaseResult item = (BaseResult)results.get( results.size() - 1 );

        return new Date( item.getPubDate().getTime() - RESTART_BUFFER );
        
    }

    /**
     * Return the correct limit, factoring in the limit set by the user. 
     *
     */
    public int getLimit() {

        int limit = config.getLimit();

        if ( limit == -1 )
            return getOptimalLimit();

        return limit;
        
    }
    
    /**
     * Set reasonable HTTP timeouts and DNS caching settings via a static
     * constructor BEFORE any HTTP calls are used.
     */
    static {

        // A full list of properties is available here:

        // http://java.sun.com/j2se/1.4.2/docs/guide/net/properties.html

        //NOTE: Thu Aug 10 2006 05:17 PM (burton@tailrank.com): its not a good
        //idea to set these values since they modify the defaults for ALL IO
        //applications. It woould be BETTER to select more realistic values
        //instead of infinity though.
        
        System.setProperty( "sun.net.inetaddr.ttl",
                            Integer.toString( NETWORKADDRESS_CACHE_TTL ) );

        System.setProperty( "networkaddress.cache.ttl",
                            Integer.toString( NETWORKADDRESS_CACHE_TTL ) );

        System.setProperty( "sun.net.client.defaultReadTimeout",
                            Integer.toString( DEFAULT_READ_TIMEOUT ) );

        System.setProperty( "sun.net.client.defaultConnectTimeout",
                            Integer.toString( DEFAULT_CONNECT_TIMEOUT ) );

        System.setProperty( "http.maxRedirects",
                            Integer.toString( DEFAULT_MAX_REDIRECTS ) );

        System.setProperty( "http.keepAlive",
                            Boolean.toString( DEFAULT_HTTP_KEEPALIVE ) );

    }
    
}
