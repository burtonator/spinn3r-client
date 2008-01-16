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
 */

package com.spinn3r.api;

import java.util.*;
import java.io.*;
import java.util.regex.*;

/**
 * Command line debug client for testing the API.  Also shows example usage.
 * 
 * This class will fetch the current API results as of INTERVAL minutes and then
 * keep fetching until it's up to date.
 * 
 */
public class Main {

    public static long RETRY_INTERVAL = 10L * 1000L;

    /**
     * Default for show_results
     */
    public static int DEFAULT_SHOW_RESULTS = 10;
    
    /**
     * Maximum amount of time we should index the API.
     */
    public static int DEFAULT_RANGE = -1;
    
    /**
     * How far behind should be start by default?
     */
    public static long INTERVAL = 60L * 60L * 1000L;

    /**
     * The date of the last item we found.
     */
    private Date last = null;

    /**
     * Timings for each fetch.
     */
    private long fetch_before = -1;
    private long fetch_after = -1;

    /**
     * Keeps track of the client that the user wants to use from the command
     * line.
     */
    private Client client = null;

    /**
     * Results from the last call.
     */
    private List<BaseItem> results = null;

    /**
     * When true, filter results for each pass.
     */
    private static int show_results = DEFAULT_SHOW_RESULTS;

    /**
     * When true, filter results for each pass.
     */
    private static boolean show_progress = true;

    /**
     * When we have a value. Only print results that match a certain pattern.
     */
    private static String filter = null;

    /**
     * When to end processing the API.
     */
    private static long range = DEFAULT_RANGE;

    /**
     * Only return results before this date.
     */
    private static long before = -1;

    private static String save = null;

    private static boolean timing = true;

    public Main( Client client ) {
        this.client = client;
    }
    
    /**
     * Process results, handling them as necessary.
     */
    void process( List<BaseItem> results ) throws Exception {

        for( BaseItem item : results ) {

            //update the state internally so we have a copy of the last item
            //found.
            last = item.getPubDate();

            if ( before > 0 && last.getTime() > before )
                break;

            if ( filter != null ) {

                Pattern p = Pattern.compile( filter );
                Matcher m = p.matcher( item.getGuid() );

                if ( ! m.find() )
                    continue;

            }
            
            if ( show_results >= 1 ) {
            
                System.out.println( "----" );
                System.out.println( "link:                   " + item.getLink() );
                System.out.println( "guid:                   " + item.getGuid() );

            }

            if ( show_results >= 2 ) {

                System.out.println( "title:                  " + item.getTitle() );
                System.out.println( "source:                 " + item.getSource() );
                System.out.println( "pubDate:                " + item.getPubDate() );
                
                System.out.println( "weblog title:           " + item.getWeblogTitle() );
                System.out.println( "weblog tier:            " + item.getWeblogTier() );
                System.out.println( "weblog publisher type:  " + item.getWeblogPublisherType() );
                System.out.println( "weblog indegree:        " + item.getWeblogIndegree() );
                
                System.out.println( "author name:            " + item.getAuthorName() );
                System.out.println( "author email:           " + item.getAuthorEmail() );
                System.out.println( "author link:            " + item.getAuthorLink() );

                System.out.println( "lang:                   " + item.getLang() );
                System.out.println( "tags:                   " + item.getTags() );

                System.out.println( "description: " );
                System.out.println( "-" );
                System.out.println( item.getDescription() );
                System.out.println( "-" );

                System.out.println( "content extract: " );
                System.out.println( "-" );
                System.out.println( item.getContentExtract() );
                System.out.println( "-" );

            }

        }
        
    }

    /**
     * Print status of the API calls.
     */
    void progress() {

        System.out.println( "-------------------------------------------" );

        long fetch_duration = fetch_after - fetch_before;

        if ( timing ) {

            System.out.println( "API fetch duration (including sleep, download, and parse): " +
                                fetch_duration );
            
            System.out.println( "--" );
            
            System.out.println( "API call duration:        " + client.getCallDuration() );
            System.out.println( "API sleep duration:       " + client.getSleepDuration() );

        }
            
        System.out.println( "--" );

        System.out.println( "Number items returned:    " + results.size() );
        System.out.println( "Last request URL:         " + client.getLastRequestURL() );
        System.out.println( "Next request URL:         " + client.getNextRequestURL() );

        if ( last == null )
            return;
            
        long diff = System.currentTimeMillis() - last.getTime();

        if ( timing )
            System.out.println( "Seconds behind present:   " + ( diff / 1000 ) );

    }

    public void exec() throws Exception {

        Config config = client.getConfig();
        
        while( true ) {

            try {
            
                //fetch the most recent results.  This will block if necessary.

                long fetch_before = System.currentTimeMillis();

                client.fetch();

                long fetch_after  = System.currentTimeMillis();

                if ( save != null ) {

                    //save the results to disk if necessary.

                    File root = new File( save );
                    root.mkdirs();

                    File file = new File( root,
                                          System.currentTimeMillis() + ".xml" );
                    
                    InputStream is = client.getInputStream();
                    FileOutputStream os =
                        new FileOutputStream( file );

                    byte[] data = new byte[ 2048 ];

                    int readCount = 0;
                    
                    while( ( readCount = is.read( data )) > 0 ) {
                        os.write( data, 0, readCount );
                    }
                    
                    is.close();
                    os.close();

                }
                
                //get the results found from the last fetch.
                results = client.getResults();

                System.out.println( "Found N results: " + results.size() );

                process( results );

                progress();

                if ( last == null )
                    continue;
                
                if ( range > 0 && last.getTime() > config.getAfter().getTime() + range )
                    break;

                if ( before > 0 && last.getTime() > before )
                    break;

            } catch ( Exception e ) {

                System.out.println( "Caught exception while processing API:  " );
                System.out.println( e.getMessage() );
                System.out.println( "Retrying in " + RETRY_INTERVAL + "ms" );
                Thread.sleep( RETRY_INTERVAL );
                
            }
                
        } 

    }

    private static String getOpt( String v ) {
        return getOpt( v, null );
    }
    
    private static String getOpt( String v, String _default ) {

        int start = v.indexOf( "=" );
        if ( start == -1 )
            return _default;

        ++start;
        
        return v.substring( start, v.length() );
                           
    }

    private static long getOptAsTimeInMillis( String v ) {

        String opt = getOpt( v );

        if ( opt == null )
            return -1;
        
        try {
            return Long.parseLong( opt );
        } catch ( Throwable t ) {
            return ISO8601DateParser.parse( opt ).getTime();
        }

    }

    private static void syntax() {

        System.out.println( "Usage: " + Main.class.getName() + " [OPTION]" );
        System.out.println( "" );
        System.out.println( "Required params:" );
        System.out.println();
        System.out.println( "    --vendor=VENDOR       Specify the vendor name for provisioning." );
        System.out.println();
        System.out.println( "Optional params:" );
        System.out.println();
        System.out.println( "    --api=API             Specify the name of the API (feed or permalink)." );
        System.out.println( "                          Default: feed" );        
        System.out.println();
        System.out.println( "    --after=NNN           Time in millis for when we should start indexing." );
        System.out.println( "                          This can also be an ISO 8601 time stamp.  " );
        System.out.println();
        System.out.println( "                            Example: 2007-12-23T00:00:00Z" );
        System.out.println();
        System.out.println( "                          Default: last 60 minutes" );        
        System.out.println();
        System.out.println( "    --before=DATE         ISO date (or millis)  All results need to occur" );
        System.out.println( "                          before this date." );
        System.out.println();
        System.out.println( "                            Example: 2007-12-23T00:00:00Z" );
        System.out.println();
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --show_results=NN     Show each result returned by the API." );
        System.out.println( "                             0 - show no fields" );        
        System.out.println( "                             1 - show only the link" );        
        System.out.println( "                             2 - show title/description" );        
        System.out.println( "                          Default: 10" );        
        System.out.println();
        System.out.println( "    --filter=http://      URL filter.  Only print URLs that match the regex." );
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --range=NNNN          Unix time duration (in millis) to terminate the API." );
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --lang=xx             Two letter language code to use" );
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --limit=xx            Number of items to return per iteration." );
        System.out.println( "                          Default: 10" );        
        System.out.println();
        System.out.println( "    --save=DIRECTORY      Save result XML to disk in the specified directory." );
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --host=hostname       Custom hostname for making calls against. Dev use only." );
        System.out.println( "                          Default: api.spinn3r.com" );        
        System.out.println();
        System.out.println( "    --host=hostname       Custom hostname for making calls against. Dev use only." );
        System.out.println( "                          Default: api.spinn3r.com" );        
        System.out.println();
        System.out.println( "    --tier=start:end      Narrow results to a specific ranking by tier." );
        System.out.println( "                          Default: none" );        
        System.out.println();

    }

    public static void main( String[] args ) throws Exception {

        //NOTE this could be cleaned up to pass the values into the config
        //object directly.
        
        //parse out propeties.

        String api = null;

        for( int i = 0; i < args.length; ++i ) {
            String v = args[i];

            if ( v.startsWith( "--api" ) )
                api = getOpt( v );

        }

        if ( api == null )
            api = "feed";

        //First. Determine which API you'd like to use.  

        Config config = null;
        Client client = null;

        if ( api.equals( "feed" ) ) {
        
            config = new FeedConfig();
            client = new FeedClient();
                
        } else {

            config = new PermalinkConfig();
            client = new PermalinkClient();

        }
        
        long    after   = -1;

        for( int i = 0; i < args.length; ++i ) {

            String v = args[i];

            if ( v.startsWith( "--vendor" ) )
                config.setVendor( getOpt( v ) );

            if ( v.startsWith( "--lang" ) )
                config.setLang( getOpt( v, "en" ) );

           if ( v.startsWith( "--filter" ) )
                filter = getOpt( v );

            if ( v.startsWith( "--show_results" ) )
                show_results = Integer.parseInt( getOpt( v ) );

            if ( v.startsWith( "--after" ) ) 
                after = getOptAsTimeInMillis( v );

            if ( v.startsWith( "--before" ) ) 
                before = getOptAsTimeInMillis( v );

            if ( v.startsWith( "--range" ) )
                range = Long.parseLong( getOpt( v ) );

            if ( v.startsWith( "--limit" ) )
                config.setLimit( Integer.parseInt( getOpt( v ) ) );

            if ( v.startsWith( "--sleep_duration" ) ) 
                client.setSleepDuration( Long.parseLong( getOpt( v ) ) );

            if ( v.startsWith( "--save" ) ) 
                save = getOpt( v );

            if ( v.startsWith( "--timing" ) ) 
                timing = "true".equals( getOpt( v ) );

            if ( v.startsWith( "--host" ) ) 
                client.setHost( getOpt( v ) );

            if ( v.startsWith( "--tier" ) ) {

                String[] split = getOpt( v ).split( ":" );

                int start = Integer.parseInt( split[ 0 ] );
                int end = Integer.parseInt( split[ 1 ] );
                
                config.setTier( start, end );
            }

        }

        //assert that we have all required options.

        if ( config.getVendor() == null ) {
            syntax();
            System.exit( 1 );
        }

        //use defaults

        System.out.println( "Using vendor: " + config.getVendor() );
        System.out.println( "Using api:    " + api );

        if ( after > -1 ) 
            System.out.println( "After: " + new Date( after ) );

        if ( before > -1 ) 
            System.out.println( "Before: " + new Date( before ) );

        System.out.println( "Saving results to disk: " + save );
        
        // Fetch for the last 5 minutes and then try to get up to date.  In
        // production you'd want to call setFirstRequestURL from the
        // getLastRequestURL returned from fetch() below
        
        if ( after == -1 ) {
            after = System.currentTimeMillis();
            after = after - INTERVAL;
        }

        config.setAfter( new Date( after ) );
        
        client.setConfig( config );

        new Main( client ).exec();
        
    }
    
}