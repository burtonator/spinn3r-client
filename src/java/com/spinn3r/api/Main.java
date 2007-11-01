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

/**
 * Command line debug client for testing the API.  Also shows example usage.
 * 
 * This class will fetch the current API results as of INTERVAL minutes and then
 * keep fetching until it's up to date.
 * 
 */
public class Main {

    public static long INTERVAL = 10L * 60L * 1000L;
    
    public static void main( String[] args ) throws Exception {

        if ( args.length == 0 ) {
            System.out.println( Main.class.getName() + " [vendor_name]" );
            System.exit( 1 );
        }

        String vendor = args[1];
        
        //First. Determine which API you'd like to use.  
        
        Config config = new FeedConfig();
        Client client = new FeedClient();

//         Config config = new PermalinkConfig();
//         Client client = new PermalinkClient();

        // set your vendor.  This is required.  Don't use the default.
        
        config.setVendor( vendor );

        // just english for right now
        config.setLang( "en" );

        //just 10 items.  this is just a test after all.
        config.setLimit( 100 );

        // Fetch for the last 5 minutes and then try to get up to date.  In
        // production you'd want to call setFirstRequestURL from the
        // getLastRequestURL returned from fetch() below
        
        long after = System.currentTimeMillis();
        after = after - INTERVAL;
        config.setAfter( new Date( after ) );
        
        client.setConfig( config );

        while( true ) {

            //fetch the most recent results.  This will block if necessary.

            long fetch_before = System.currentTimeMillis();
            client.fetch();
            long fetch_after  = System.currentTimeMillis();

            //get the results found from the last fetch.
            List<BaseItem> results = client.getResults();

            System.out.println( "Found N results: " + results.size() );

            Date last = null;
            
            for( BaseItem item : results ) {

                System.out.println( "----" );
                System.out.println( "title:          " + item.getTitle() );
                System.out.println( "link:           " + item.getLink() );
                System.out.println( "guid:           " + item.getGuid() );
                System.out.println( "source:         " + item.getSource() );
                System.out.println( "pubDate:        " + item.getPubDate() );

                System.out.println( "weblog title:   " + item.getWeblogTitle() );
                System.out.println( "weblog tier:    " + item.getWeblogTier() );

                System.out.println( "author name:    " + item.getAuthorName() );
                System.out.println( "author email:   " + item.getAuthorEmail() );
                System.out.println( "author link:    " + item.getAuthorLink() );

                last = item.getPubDate();
                
            }

            System.out.println( "-------------------------------------------" );

            long fetch_duration = fetch_after - fetch_before;
            
            System.out.println( "API fetch duration (including sleep, download, and parse): " +
                                fetch_duration );
            
            System.out.println( "API call duration:        " + client.getCallDuration() );
            System.out.println( "API sleep duration:       " + client.getSleepDuration() );
            
            System.out.println( "Number items returned:    " + results.size() );
            System.out.println( "Last request URL:         " + client.getLastRequestURL() );
            System.out.println( "Next request URL:         " + client.getNextRequestURL() );
            
            if ( last == null )
                continue;
                
            long diff = System.currentTimeMillis() - last.getTime();
            
            System.out.println( "Seconds behind present:   " + ( diff / 1000 ) );
            
        } 

    }
    
}