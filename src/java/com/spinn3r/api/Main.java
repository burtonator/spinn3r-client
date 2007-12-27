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

    /**
     * Default for show_results
     */
    public static int DEFAULT_SHOW_RESULTS = 0;
    
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

            if ( filter != null ) {

                Pattern p = Pattern.compile( filter );
                Matcher m = p.matcher( item.getGuid() );

                if ( ! m.find() )
                    continue;

            }
            
            if ( show_results == 0 || show_results >= 1 ) {
            
                System.out.println( "----" );
                System.out.println( "link:           " + item.getLink() );
            }

            if ( show_results == 0 || show_results >= 2 ) {

                System.out.println( "title:          " + item.getTitle() );
                System.out.println( "guid:           " + item.getGuid() );
                System.out.println( "source:         " + item.getSource() );
                System.out.println( "pubDate:        " + item.getPubDate() );
                
                System.out.println( "weblog title:   " + item.getWeblogTitle() );
                System.out.println( "weblog tier:    " + item.getWeblogTier() );
                
                System.out.println( "author name:    " + item.getAuthorName() );
                System.out.println( "author email:   " + item.getAuthorEmail() );
                System.out.println( "author link:    " + item.getAuthorLink() );

                System.out.println( "lang:           " + item.getLang() );
                System.out.println( "tags:           " + item.getTags() );

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
        
        System.out.println( "API fetch duration (including sleep, download, and parse): " +
                            fetch_duration );

        System.out.println( "--" );
        
        System.out.println( "API call duration:        " + client.getCallDuration() );
        System.out.println( "API sleep duration:       " + client.getSleepDuration() );

        System.out.println( "--" );

        System.out.println( "Number items returned:    " + results.size() );
        System.out.println( "Last request URL:         " + client.getLastRequestURL() );
        System.out.println( "Next request URL:         " + client.getNextRequestURL() );

        if ( last == null )
            return;
            
        long diff = System.currentTimeMillis() - last.getTime();
        
        System.out.println( "Seconds behind present:   " + ( diff / 1000 ) );

    }

    public void exec() throws Exception {

        try {

            Config config = client.getConfig();
            
            while( true ) {

                //fetch the most recent results.  This will block if necessary.

                long fetch_before = System.currentTimeMillis();

                client.fetch();

                long fetch_after  = System.currentTimeMillis();

                //get the results found from the last fetch.
                results = client.getResults();

                System.out.println( "Found N results: " + results.size() );

                process( results );

                progress();

                if ( range > 0 && last.getTime() > config.getAfter().getTime() + range )
                    break;

            } 

        } finally {

            // persist call pointer settings by recording the offset and 'after'
            // parameters in the config used the by the client by storing the
            // last requested URL.  Note that since this is in a finally block
            // it will be called even when a Throwable or IOException is called.

            //get the config object that the client is using.

            String lastRequestURL = client.getLastRequestURL();            
            
        }
            
    }

    private static String getOpt( String v ) {

        int start = v.indexOf( "=" );
        if ( start == -1 )
            return null;

        ++start;
        
        return v.substring( start, v.length() );
                           
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
        System.out.println( "    --after=NNN           Unix time in millis for when we should start indexing." );
        System.out.println( "                          This can also be an ISO 8601 time stamp.  " );
        System.out.println();
        System.out.println( "                            Example: 2007-12-23T00:00:00Z" );
        System.out.println();
        System.out.println( "                          Default: last 60 minutes" );        
        System.out.println();
        System.out.println( "    --show_results=NN     Show each result returned by the API." );
        System.out.println( "                             0 - show all fields" );        
        System.out.println( "                             1 - show only the link" );        
        System.out.println( "                             2 - show title/description" );        
        System.out.println( "                          Default: 0" );        
        System.out.println();
        System.out.println( "    --filter=http://      URL filter.  Only print URLs that match the regex." );
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --after=NNNN          Unix time (in millis) to start the API." );
        System.out.println( "                          Default: none" );        
        System.out.println();
        System.out.println( "    --range=NNNN          Unix time duration (in millis) to terminate the API." );
        System.out.println( "                          Default: none" );        
        System.out.println();

    }

    public static void main( String[] args ) throws Exception {

        //parse out propeties.
        
        String  vendor  = null;
        String  api     = null;
        long    after   = -1;

        for( int i = 0; i < args.length; ++i ) {

            String v = args[i];

            if ( v.startsWith( "--vendor" ) )
                vendor = getOpt( v );

            if ( v.startsWith( "--api" ) )
                api = getOpt( v );

           if ( v.startsWith( "--filter" ) )
                filter = getOpt( v );

            if ( v.startsWith( "--show_results" ) )
                show_results = Integer.parseInt( getOpt( v ) );

            if ( v.startsWith( "--after" ) ) {

                String opt = getOpt( v );

                after = ISO8601DateParser.parseInt( opt );

                if ( after == -1 )
                    after = ISO8601DateParser.parse( opt ).getTime();
                
            }

            if ( v.startsWith( "--range" ) ) 
                range = Long.parseLong( getOpt( v ) );

        }

        //assert that we have all required options.

        if ( vendor == null ) {
            syntax();
            System.exit( 1 );
        }

        //use defaults

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

        System.out.println( "Using vendor: " + vendor );
        System.out.println( "Using api:    " + api );
        
        // set your vendor.  This is required.  Don't use the default.
        
        config.setVendor( vendor );

        // just english for right now
        config.setLang( "en" );

        //just 10 items.  this is just a test after all.
        config.setLimit( 10 );

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