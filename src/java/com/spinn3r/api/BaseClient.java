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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.protobuf.CodedInputStream;
import com.spinn3r.api.Config.Format;
import com.spinn3r.api.protobuf.ContentApi;
import com.spinn3r.api.util.ProtoStreamDecoder;

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

    public static int PROTOBUF_SIZE_LIMIT = 256 * 1024 * 1024;
    
    private static final String X_MORE_RESULTS = "X-More-Results";

    /**
     * Maximum number of retries.
     */
    public static final long RETRY_MAX = 1;

    public static final String USER_AGENT_HEADER       = "User-Agent";
    public static final String ACCEPT_ENCODING_HEADER  = "Accept-Encoding";

    public static final String FEED_HANDLER       = "feed";
    public static final String PERMALINK_HANDLER  = "permalink3";
    public static final String COMMENT_HANDLER    = "comment3";
    public static final String LINK_HANDLER       = "link3";
    
    public static final String GZIP_ENCODING = "gzip";

    // Would be nice to have this use String.format() but this isn't really
    // compatible back to Java 1.4.. are we requiring Java 1.5 now?
    //
    // TODO: include OS name, kernel version, etc.

    public static final String USER_AGENT = String.format( "Spinn3r API Reference Client %s (Java %s, maxMemory=%s)",
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
    public static final int NETWORKADDRESS_CACHE_TTL = 5 * 60;

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
    public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 60 * 1000;

    /**
     * Lower read timeout.  Makes NO sense to wait for five minutes to read a
     * byte from spinn3r.
     */
    public static final int DEFAULT_READ_TIMEOUT = DEFAULT_CONNECT_TIMEOUT;

    /**
     * Specify the maximum number of redirects to use.
     */
    public static final int DEFAULT_MAX_REDIRECTS = 5;

    /**
     * Whether we should use HTTP Keep Alive in java.net.URL.  We default to
     * true here because MOST of our TCP connections WILL be used again since
     * everything is to spinn3r.com.
     */
    public static final boolean DEFAULT_HTTP_KEEPALIVE = true;
    
    /**
     * Keeps track of the number of connections that this client has used.
     */
    private long connectionCount = 0;

    abstract public boolean getIsCompressed();
    
    // **** fetching support ****************************************************

    public BaseClientResult<ResultType> fetch( Config<ResultType> config ) throws IOException,
                                              ParseException,
                                              InterruptedException {

        PartialBaseClientResult<ResultType> partial_result = partialFetch( config );
        try {
            return completeFetch( partial_result );
        } finally {
            closeQuietly(partial_result.getConnection());
        }
    }

    public static void closeQuietly(URLConnection conn) {
        try {
            if (conn != null) {
                InputStream is = conn.getInputStream();
                if (is != null) {
                    is.close();
                }
            }
        } catch (IOException ignore) {
        }
    }


    public PartialBaseClientResult<ResultType> partialFetch( Config<ResultType> config ) throws IOException,
                                              ParseException,
                                              InterruptedException {

        PartialBaseClientResult<ResultType> res;

        int retry_ctr = 0;
        int limit     = getLimit( config );

        while( true ) {

            try {

                // set the optimal limit if necessary
                if ( retry_ctr > 0 )
                    limit = config.getConservativeLimit();
                
                res = startFetch( config, limit );
                
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

                throw new IOException(e);

            }
            
        }
        
        return res;
    }
    
    /**
     * Fetch the API with the given FeedConfig
     * 
     * @throws IOException if there's an error with network transport.
     * @throws ParseException if there's a problem parsing the resulting XML.
     */
    private PartialBaseClientResult<ResultType> startFetch( Config<ResultType> config, int request_limit ) throws IOException,
                                                 InterruptedException {

        PartialBaseClientResult<ResultType> result = new PartialBaseClientResult<ResultType>( config );

        if ( config.getVendor() == null )
            throw new RuntimeException( "Vendor not specified" );

        String resource = config.getNextRequestURL();

        //enforce max limit so that we don't generate runtime exceptions.
        if ( request_limit > config.getMaxLimit() )
            request_limit = config.getMaxLimit();
        
        if ( resource == null ) {

            resource = config.getFirstRequestURL();

            // if the API has NEVER been used before then generate the first
            // request URL from the config parameters.
            if ( resource == null )
                resource = config.generateFirstRequestURL( request_limit );

        } 

        //apply the request_limit to the current URL.  This needs to be done so
        //that we can change the limit at runtime.  When I originally designed
        //the client I didn't want to support introspecting and mutating the URL
        //on the client but with the optimial limit performance optimization
        //this is impossible.
        resource = setParam( resource, "limit", request_limit );
        
        // add a connection number to the vendor code
        resource = addConnectionNumber(resource);

        // store the last requested URL so we can expose this to the caller for
        // debug purposes.

        result.setLastRequestURL( resource );
        result.setRequestLimit( request_limit );


        URLConnection conn = getConnection( resource );

        result.setConnection( conn );

        setMoreResults( conn, result );

        result.setNextRequestURL( conn.getHeaderField( "X-Next-Request-URL" ) );            

        return result;
    }
    
    private String addConnectionNumber(String url) {
        
        String[] parts = url.split("\\?");
        
        if(parts.length < 2) {
            return url;
        }
        
        String newURL = parts[0] + "?";
        List<String> newParams = new LinkedList<String>();
        
        for(String param : parts[1].split("&")) {
            String[] paramParts = param.split("=");
            
            if(paramParts.length >= 2) {
                if(paramParts[0].equals("vendor")) {
                    newParams.add(String.format("vendor=%s[%d]", getConfig().getVendor(), 
                            connectionCount++ % LegacyWrapperClient.PARALLELISM));
                } else {
                    newParams.add(param);
                }
            } 
            else {
                newParams.add(param);
            }
        }
        
        return newURL + StringUtils.join(newParams, "&");
    }

    /**
     * Fetch the API with the given FeedConfig
     * 
     * @throws IOException if there's an error with network transport.
     * @throws ParseException if there's a problem parsing the resulting XML.
     */
    public BaseClientResult<ResultType> completeFetch( PartialBaseClientResult<ResultType> partial_result ) throws IOException,
                                                 ParseException,
                                                 InterruptedException {

        Config<ResultType> config             = partial_result.getConfig();
        int                request_limit      = partial_result.getRequestLimit();
        String             resource           = partial_result.getLastRequestURL();
        boolean            has_results_header = partial_result.getHasMoreResultsHeader();
        boolean            has_more_results   = partial_result.getHasMoreResults();
        String             next_request       = partial_result.getNextRequestURL();
        
        BaseClientResult<ResultType> result = new BaseClientResult<ResultType> ( config );



        result.setLastRequestURL( resource );
        result.setRequestLimit( request_limit );
        result.setHasMoreResultsHeadder( has_results_header );
        result.setHasMoreResults( has_more_results );
        result.setNextRequestURL( next_request );


        try {

            long before = System.currentTimeMillis();

            long call_before = System.currentTimeMillis();

            URLConnection conn = partial_result.getConnection();

            //TODO: clean up the naming here.  getLocalInputStream actually
            //reads everything into a byte array in memory.
            InputStream localInputStream = getLocalInputStream( conn.getInputStream(), result ); 

            result.setLocalInputStream( localInputStream );

            if ( GZIP_ENCODING.equals( conn.getContentEncoding() ) )
                result.setIsCompressed( true );


            InputStream is = result.getInputStream();

            long call_after = System.currentTimeMillis();

            result.setCallDuration( call_after - call_before );


            if ( ! config.getDisableParse() ) {

                
               if(config.getFormat() == Format.PROTOSTREAM)
                		result.setResults(protobufParse( doProtoStreamFetch(localInputStream, config), config));
                else if(config.getFormat() == Format.PROTOBUF)
                		result.setResults( protobufParse( doProtobufFetch( localInputStream, config ), config ) );

                else {

                    Document doc = doXmlFetch( is, config );

                    if ( doc != null ) {
                        result.setResults( xmlParse( doc, config ) );
                    }
                
                }
            }

            long after = System.currentTimeMillis();

            result.setParseDuration( after - before );
            
        } 

        catch ( Exception e ) {
            throw new ParseException( e, "Unable to handle request: " + resource );
        }

        if ( ! result.getHasMoreResultsHeadder() )
            result.setHasMoreResults( result.getResults().size() == request_limit );

        return result;
    }


    protected URLConnection getConnection ( String resource ) throws IOException {

        URLConnection conn = null;
        
        try {

            // create the HTTP connection.
            URL request = new URL( resource );
            conn = request.openConnection();

            // set the UserAgent so Spinn3r know which client lib is calling.
            conn.setRequestProperty( USER_AGENT_HEADER, USER_AGENT + "; " +  getConfig().getCommandLine());
            conn.setRequestProperty( ACCEPT_ENCODING_HEADER, GZIP_ENCODING );
            conn.setConnectTimeout(20000);                        
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

    private void setMoreResults( URLConnection conn, PartialBaseClientResult<ResultType> result ) {

        String more = conn.getHeaderField( X_MORE_RESULTS );

        if ( more == null )
            result.setHasMoreResultsHeader( false );
            
        else {
            result.setHasMoreResultsHeader( true );

            if ( "true".equals( more ) ) 
                result.setHasMoreResults( true );
            else
                result.setHasMoreResults( false );
        }
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
    
    public List<ContentApi.Entry> doProtoStreamFetch( InputStream inputStream, Config<?> config ) throws IOException {

        List<ContentApi.Entry> res = new ArrayList<ContentApi.Entry> ();

        ContentApi.Entry.Builder builder = ContentApi.Entry.newBuilder();

        ProtoStreamDecoder<ContentApi.Entry> decoder =
            new ProtoStreamDecoder<ContentApi.Entry> ( inputStream, builder );

        for ( ContentApi.Entry entry = decoder.read() ; entry != null ; entry = decoder.read() ) {
            res.add( entry );
        }
    	
    	return res;
    }

    public ContentApi.Response doProtobufFetch( InputStream inputStream, Config<?> config ) throws IOException, InterruptedException {
        CodedInputStream cis = CodedInputStream.newInstance( inputStream );
        cis.setSizeLimit( PROTOBUF_SIZE_LIMIT );
        return ContentApi.Response.parseFrom( cis );
    }

    public Document doXmlFetch( InputStream inputStream, Config<ResultType> config  ) throws IOException,
                                                      ParseException,
                                                      InterruptedException {

        try {
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
            
            Document doc = parser.parse( inputStream );
            
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
    private InputStream getLocalInputStream( InputStream is, BaseClientResult<ResultType> result ) throws IOException {
        return new ByteArrayInputStream( getInputStreamAsByteArray( is, result ) );
    }
    
    /**
     * Get the input stream as a byte array.
     */
    private byte[] getInputStreamAsByteArray( InputStream is, BaseClientResult<ResultType> result ) throws IOException {

        //include length of content from the original site with contentLength

        ByteArrayOutputStream bos = new ByteArrayOutputStream( 500000 );

        //now process the Reader...
        byte data[] = new byte[2048];

        int readCount = 0;

        int total = 0;

        try {
            while( ( readCount = is.read( data )) >= 0 ) {
                bos.write( data, 0, readCount );
                total += readCount;
            }
        } finally {
            IOUtils.closeQuietly(is);

            result.getBs1().sample( total );
            result.getBs5().sample( total );
            result.getBs15().sample( total );
        }

        bos.close();

        return bos.toByteArray();

    }

    /**
     * We've received a response from the API so parse it out.
     *
     */
    protected List<ResultType> xmlParse( Document doc, Config<ResultType> config ) throws Exception {

        Element root = (Element)doc.getFirstChild();

        List<ResultType> result = new ArrayList<ResultType>();

        NodeList items = root.getElementsByTagName( "item" );

        for( int i = 0; i < items.getLength(); ++i ) {

            Element current = (Element)items.item( i );
            
            result.add( config.createResultObject( current ) );
            
        }

        return result;
        
    }

    /**
     * We've received a response from the API so parse it out.
     *
     */
    protected List<ResultType> protobufParse( ContentApi.Response response, Config<ResultType> config ) throws Exception {

        return protobufParse(response.getEntryList(), config);
        
    }
    
    protected List<ResultType> protobufParse(List<ContentApi.Entry> entries, Config<ResultType> config) throws Exception {
    	
    	List<ResultType> result = new ArrayList<ResultType>();

        for ( ContentApi.Entry entry : entries ) {
            result.add( config.createResultObject( entry ) );
        }

        return result;
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

    /**
     * Parse command line arguments like --foo=bar where foo is the key and bar
     * is the value.
     *
     */
    public static Map<String,String> getopt( String[] args ) {

        Map<String,String> result = new HashMap<String, String>();
        
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
