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

import com.spinn3r.api.protobuf.*;
import com.spinn3r.api.util.CompressedBLOB;

import static com.spinn3r.api.XMLUtils.*;

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
public abstract class BaseClient<ResultType extends BaseResult> implements Client<ResultType> {

    private static String X_MORE_RESULTS = "X-More-Results";

    /**
     * Maximum number of retries.
     */
    public static long RETRY_MAX = 1;

    /**
     * Go back in time to make sure we recrawl everything.
     */
    public static final int RESTART_BUFFER = 30 * 1000;

    
    public static final String USER_AGENT_HEADER       = "User-Agent";
    public static final String ACCEPT_ENCODING_HEADER  = "Accept-Encoding";

    public static String FEED_HANDLER       = "feed3";
    public static String PERMALINK_HANDLER  = "permalink3";
    public static String COMMENT_HANDLER    = "comment3";
    public static String LINK_HANDLER       = "link3";
    
    public static final String GZIP_ENCODING = "gzip";

    // Would be nice to have this use String.format() but this isn't really
    // compatible back to Java 1.4.. are we requiring Java 1.5 now?
    //
    // TODO: include OS name, kernel version, etc.

    public static String USER_AGENT = String.format( "Spinn3r API Reference Client %s (Java %s, maxMemory=%s)",
                                                     Config.DEFAULT_VERSION,
                                                     System.getProperty( "java.version" ),
                                                     Runtime.getRuntime().maxMemory() );
        
    
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

    protected boolean disable_parse = false;

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

    private long parseDuration = -1;

    protected List<ResultType> results = new ArrayList<ResultType>();


    /**
     * True if the last API call was compressed.
     */
    protected boolean isCompressed = false;

    /**
     * Get the last localInputStream we're using.  This is a
     * ByteArrayInputStream based InputStream.
     */
    InputStream localInputStream = null;


    /**
     * Sample performance times...
     */
    BandwidthSampler bs1   = new BandwidthSampler( 1L  * 60L * 1000L );
    BandwidthSampler bs5   = new BandwidthSampler( 5L  * 60L * 1000L );
    BandwidthSampler bs15  = new BandwidthSampler( 15L * 60L * 1000L );

    private boolean hasMoreResults        = true;
    private boolean hasMoreResultsHeadder = false;
    
    // **** fetching support ****************************************************

    /**
     * Get the InputStream for dealing with the XML of the API directly.  This
     * is a LOCAL input stream so once fetch() has been called you can call this
     * API multiple times and you're reading from a local buffer.
     */
    public InputStream getInputStream() throws IOException {

        InputStream is = localInputStream;

        //TODO: why do we need to reset?  I don't think we need reset and I
        //think this was added for gzip detection which we don't use.
        is.reset();
        
        //wrap the downloaded input stream with a gzip input stream when
        //necessary.
        if ( isCompressed ) {

            //NOTE: this is a bug fix for Apache2.  If mod_compress is disabled
            //during LIVE http connections the result won't be compressed
            //content.  The GZIPInputStream class will first attempt to read the
            //gzip magic number in its constructor.  If the magic number is
            //incorrect then it will throw an exception.

            if ( disable_parse == false ) {

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
                
        }

        return is;

    }

    public void fetch( Config<ResultType> config ) throws IOException,
                                              ParseException,
                                              InterruptedException {

        int retry_ctr = 0;

        while( true ) {

            try {

                // set the optimal limit if necessary
                if ( retry_ctr == 0 )
                    config.setLimit( getLimit( config ) );
                else
                    config.setLimit( config.getConservativeLimit() );
                
                doFetch( config );

                //We performed one HTTP fetch successfully, restore the limit.
                //NOTE: that if the user sets the limit to say 20, and then we
                //break, it could revert to ten, and then revert to the optimal
                //limit (which could be 100).  This is fine for now as I want to
                //totally remove the ability for customers to change the limit.
                config.setLimit( config.getOptimalLimit() );
                
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
    private void doFetch( Config<ResultType> config ) throws IOException,
                                                 ParseException,
                                                 InterruptedException {

        if ( config.getVendor() == null )
            throw new RuntimeException( "Vendor not specified" );

        String resource = getNextRequestURL();

        setSleepDuration( 0 );

        int requestLimit = config.getLimit();

        //enforce max limit so that we don't generate runtime exceptions.
        if ( requestLimit > config.getMaxLimit() )
            requestLimit = config.getMaxLimit();
        
        if ( resource == null ) {

            resource = config.getFirstRequestURL();

            // if the API has NEVER been used before then generate the first
            // request URL from the config parameters.
            if ( resource == null )
                resource = config.generateFirstRequestURL( );

        } else if ( ! hasMoreResults ) {

            if ( ! disable_parse ) {
            
                long sleepInterval = config.getSleepInterval();
                
                //we've fetched before so determine if we need to spin.
                Thread.sleep( sleepInterval );
                setSleepDuration( sleepInterval );
                
            }
            
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

            long before = System.currentTimeMillis();

            if ( config.getUseProtobuf() ) {
                protobufParse( doProtobufFetch( resource ), config );
            } else {

                Document doc = doXmlFetch( resource, config );

                if ( doc != null ) {
                    xmlParse( doc, config );
                }
                
            }

            long after = System.currentTimeMillis();

            setParseDuration( after - before );
            
        } 

        catch ( Exception e ) {
            throw new ParseException( e, "Unable to handle request: " + resource );
        }

        if ( ! hasMoreResultsHeadder )
            hasMoreResults = results.size() == requestLimit;
    }

    private URLConnection getConnection ( String resource ) throws IOException {

        URLConnection conn = null;
        
        try {

            // create the HTTP connection.
            URL request = new URL( resource );
            conn = request.openConnection();

            // set the UserAgent so Spinn3r know which client lib is calling.
            conn.setRequestProperty( USER_AGENT_HEADER, USER_AGENT );
            conn.setRequestProperty( ACCEPT_ENCODING_HEADER, GZIP_ENCODING );
                        
            conn.connect();

        } 

        catch ( IOException ioe ) {

            //create a custom exception message with the right error.
            String message = conn.getHeaderField( null );
            IOException ce = new IOException( message );
            ce.setStackTrace( ioe.getStackTrace() );
            
            throw ce;
        }

        return conn;
    }

    private void setMoreRsults( URLConnection conn ) {

        String more = conn.getHeaderField( X_MORE_RESULTS );

        if ( more == null )
            hasMoreResultsHeadder = false;
            
        else {
            hasMoreResultsHeadder = true;

            if ( "true".equals( more ) ) 
                hasMoreResults = true;
            else
                hasMoreResults = false;
        }
    }

    public ContentApi.Response doProtobufFetch( String resource ) throws IOException, InterruptedException {
 
        long call_before = System.currentTimeMillis();

        URLConnection conn = getConnection( resource );

        setMoreRsults( conn );

        ContentApi.Response res = null;
        
        if ( disable_parse ) {
            
            // Only use a local input stream if we're about to write to disk.  I
            // think we can stream parse.  needed for --save to persist output to
            // disk.
            
            localInputStream = getLocalInputStream( conn.getInputStream() );
            res  = ContentApi.Response.parseFrom( localInputStream );
            
        } 

        else {
            res  = ContentApi.Response.parseFrom( conn.getInputStream() );
        }
        

        long call_after = System.currentTimeMillis();

        setCallDuration( call_after - call_before );
        
        return res;
    }

    public Document doXmlFetch( String resource, Config<ResultType> config  ) throws IOException,
                                                      ParseException,
                                                      InterruptedException {

        URLConnection conn = null;
        
        try {

            long call_before = System.currentTimeMillis();

            conn = getConnection( resource );

            setMoreRsults( conn );

            //TODO: clean up the naming here.  getLocalInputStream actually
            //reads everything into a byte array in memory.
            localInputStream = getLocalInputStream( conn.getInputStream() ); 

            if ( GZIP_ENCODING.equals( conn.getContentEncoding() ) ) {
                isCompressed = true;
            }

            String content_type = conn.getContentType();

            InputStream is = getInputStream();
            
            long call_after = System.currentTimeMillis();

            setCallDuration( call_after - call_before );

            setNextRequestURL( conn.getHeaderField( "X-Next-Request-URL" ), config );

            if ( disable_parse )
                return null;
            

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

        } 

        catch ( IOException ioe ) {
            throw ioe;
        } 

        catch ( Exception e ) {

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

        ByteArrayOutputStream bos = new ByteArrayOutputStream( 500000 ); 
      
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
    protected void xmlParse( Document doc, Config<ResultType> config ) throws Exception {

        Element root = (Element)doc.getFirstChild();

        Element channel = getElementByTagName( root, "channel" );

        List<ResultType> result = new ArrayList<ResultType>();

        NodeList items = root.getElementsByTagName( "item" );

        for( int i = 0; i < items.getLength(); ++i ) {

            Element current = (Element)items.item( i );
            
            result.add( config.createResultObject( current ) );
            
        }

        this.results = result;
        
    }

    /**
     * We've received a response from the API so parse it out.
     *
     */
    protected void protobufParse( ContentApi.Response response, Config<ResultType> config ) throws Exception {

        List<ResultType> result = new ArrayList<ResultType>();

        for ( ContentApi.Entry entry : response.getEntryList() ) {
            result.add( config.createResultObject( entry ) );
        }

        this.results = result;
        
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
    public void setNextRequestURL( String next, Config<ResultType> config ) { 

        //TODO: apply the correct hostname to the next request.

        if ( config.getHost() != null ) {
            String path = next.substring( next.indexOf( "/", "http://".length() ), next.length() );
            next = String.format( "http://%s%s", config.getHost(), path );
        }

        this.nextRequestURL = next;
    }

     /**
      * 
      * Get the value of <code>result</code>.
      *
      */
     public List<ResultType> getResults() { 
         return this.results;
     }

//BOOG     /**
//      * 
//      * Set the value of <code>result</code>.
//      *
//      */
//     public void setResults( List<BaseResult> results ) { 
//         this.results = results;
//     }


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
     * 
     * Get the value of <code>parseDuration</code>.
     *
     */
    public long getParseDuration() { 
        return this.parseDuration;
    }

    /**
     * 
     * Set the value of <code>parseDuration</code>.
     *
     */
    public void setParseDuration( long parseDuration ) { 
        this.parseDuration = parseDuration;
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
    public int getLimit( Config<ResultType> config ) {

        int limit = config.getLimit();

        if ( limit == -1 )
            return config.getOptimalLimit();

        return limit;
        
    }

    /**
     * Return true if more results are available.
     *
     */
    public boolean hasMoreResults() {
        return hasMoreResults;
    }
    
    /**
     * Parse command line arguments like --foo=bar where foo is the key and bar
     * is the value.
     *
     */
    public static Map<String,String> getopt( String[] args ) {

        Map<String,String> result = new HashMap();
        
        for( String arg : args ) {

            String[] split = arg.split( "=" );

            String key = split[0];

            if ( key.startsWith( "--" ) )
                key = key.substring( 2, key.length() );
            
            String value = split[1];

            result.put( key, value );
            
        }

        return result;
        
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
