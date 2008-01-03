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
 * Generic methods which need to be in all clients.
 */
public abstract class BaseClient {

    public static final String NS_API     = "http://tailrank.com/ns/#api";
    public static final String NS_DC      = "http://purl.org/dc/elements/1.1/" ;
    public static final String NS_ATOM    = "http://www.w3.org/2005/Atom" ;
    public static final String NS_WEBLOG  = "http://tailrank.com/ns/#weblog" ;
    public static final String NS_POST    = "http://tailrank.com/ns/#post" ;

    public static final String USER_AGENT_HEADER = "User-Agent";
    public static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";

    public static final String GZIP_ENCODING = "gzip";

    public static String USER_AGENT = "Spinn3r API Reference Client (Java)";

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
    public static int DEFAULT_CONNECT_TIMEOUT = 1 * 60 * 1000;

    public static int DEFAULT_READ_TIMEOUT = DEFAULT_CONNECT_TIMEOUT;

    /**
     * Specify the maximum number of redirects to use.
     */
    public static int DEFAULT_MAX_REDIRECTS = 5;

    /**
     * Whether we should use HTTP Keep Alive in java.net.URL.  We default to
     * false here because MOST of our TCP connections won't ever be re-used and
     * we're just wasting file handles on the robot and keeping a connection
     * open to the remote host which uses one of their threads.  I also think
     * this yields a bug in Tailrank's robot where numerous threads continually
     * access thousands of hosts and then we start to run out of available file
     * handles.  
     */
    public static boolean DEFAULT_HTTP_KEEPALIVE = false;

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
    
    private List results = null;

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
    
    // **** fetching support ****************************************************

    /**
     * Get the InputStream for dealing with the XML of the API directly.  This
     * is a LOCAL input stream so once fetch() has been called you can call this
     * API multiple times and you're reading from a local buffer.
     */
    public InputStream getInputStream() throws IOException {

        InputStream is = localInputStream;
        
        //wrap the downloaded input stream with a gzip input stream when
        //necessary.
        if ( isCompressed )
            is = new GZIPInputStream( is );

        return is;

    }
    
    /**
     * Fetch the API with the given FeedConfig
     * 
     * @throws IOException if there's an error with network transport.
     * @throws ParserException if there's a problem parsing the resulting XML.
     */
    public void fetch( Config config ) throws IOException,
                                              ParseException,
                                              InterruptedException {

        if ( config.getVendor() == null )
            throw new RuntimeException( "Vendor not specified" );

        String resource = getNextRequestURL();

        setSleepDuration( 0 );

        int requestLimit = config.getLimit();

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

        // store the last requested URL so we can expose this to the caller for
        // debug purposes.

        setLastRequestURL( resource );
        
        // create the HTTP connection.
        URL request = new URL( resource );
        URLConnection conn = request.openConnection();

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
        
        try {

            // now get the system XML parser using JAXP

            DocumentBuilderFactory docBuildFactory =
                DocumentBuilderFactory.newInstance();

            //namespaces won't work at ALL if this isn't enabled.
            docBuildFactory.setNamespaceAware( true );

            //FIXME: ok ... don't I need to ADD the namespaces?
            
            DocumentBuilder parser =
                docBuildFactory.newDocumentBuilder();
            
            // parse the document into a DOM.... I'd like to use JDOM here but
            // it's yet another lib to support and we want to keep things thin
            // and lightweight.
            //
            // Another advantage to DOM is that it's very portable.
            
            Document doc = parser.parse( is );

            parse( doc );

        } catch ( Exception e ) {
            throw new ParseException( e );
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream( 500000 );
      
        //now process the Reader...
        byte data[] = new byte[2048];
    
        int readCount = 0;

        while( ( readCount = is.read( data )) > 0 ) {
            bos.write( data, 0, readCount );
        }

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

        this.setResults( result );
        
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

        //AFTER param needs to be added which should be ISO8601
        addParam( params, "after",   toISO8601( config.getAfter() ) );

        //add optional params
        addParam( params, "lang", config.getLang(), true );
        if ( config.getTierStart() >= 0 ) {
            String param_tier = "" + config.getTierStart() + ":" + config.getTierEnd();
            addParam( params, "tier", param_tier );
        }
            
        return getRouter() + params.toString();
        
    }

    /**
     * Return the maximum number of request per call.  If the API has more
     * content this might cause an error on our end if the limit is more than 10
     * or so.
     */
    protected abstract int getMaxLimit();

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
    protected abstract String getRouter();
    
    /**
     * Parse an individual item which might be specific to this client.
     */
    protected abstract BaseItem parseItem( Element current ) throws Exception;

    protected BaseItem parseItem( Element current,
                                  BaseItem item ) throws Exception {

        //base elements.
        item.setTitle( getElementCDATAByTagName( current, "title" ) );

        item.setDescription( getElementCDATAByTagName( current,
                                                       "description" ) );

        item.setLink(  getElementCDATAByTagName( current, "link" )  );
        item.setGuid(  getElementCDATAByTagName( current, "guid" )  );

        // dc:lang
        item.setLang(  getElementCDATAByTagName( current, "lang", NS_DC ) );

        // dc:source
        item.setSource(  getElementCDATAByTagName( current, "source", NS_DC ) );

        // weblog:title
        // weblog:description
        item.setWeblogTitle(  getElementCDATAByTagName( current,
                                                        "title",
                                                        NS_WEBLOG ) );

        item.setWeblogDescription( getElementCDATAByTagName( current, "description", NS_WEBLOG ) );

        String str_tier = getElementCDATAByTagName( current, "tier", NS_WEBLOG );
        
        if ( str_tier != null )
            item.setWeblogTier( Integer.parseInt( str_tier ) );

        String pubDate = getElementCDATAByTagName( current, "pubDate" );
        item.setPubDate( RFC822DateParser.parse( pubDate ) );
        
        //FIXME: weblog:indegree
        //FIXME: weblog:iranking
        
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

        return item;
        
    }

    // **** XML parsing utilities ***********************************************

    protected List parseTags( Element current ) {
        return parseChildNodesAsList( current, "category" );
    }

    protected List parseChildNodesAsList( Element current, String name ) {

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

    protected Element getElementByTagName( Element current,
                                           String name ) {
        return getElementByTagName( current, name, null );
    }
    
    protected Element getElementByTagName( Element current,
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
    
    protected String getElementCDATAByTagName( Element current, String name ) {

        return getElementCDATAByTagName( current, name, null );
    }

    protected String getElementCDATAByTagName( Element current,
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

    protected void addParam( StringBuffer buff,
                             String name,
                             Object value ) {
        addParam( buff, name, value, false );
    }

    /**
     * Add a parameter to the first request URL.  After the first call this is
     * no longer needed. 
     */
    protected void addParam( StringBuffer buff,
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
    protected void setNextRequestURL( String nextRequestURL ) { 
        this.nextRequestURL = nextRequestURL;
    }

    /**
     * 
     * Get the value of <code>result</code>.
     *
     */
    public List getResults() { 
        return this.results;
    }

    /**
     * 
     * Set the value of <code>result</code>.
     *
     */
    public void setResults( List results ) { 
        this.results = results;
    }

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

        System.setProperty( "http.keepAlive", Boolean.toString( DEFAULT_HTTP_KEEPALIVE ) );

    }
    
}
