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

package com.spinn3r.api.qa;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.spinn3r.api.BaseItem;
import com.spinn3r.api.Client;
import com.spinn3r.api.Config;
import com.spinn3r.api.PermalinkClient;
import com.spinn3r.api.PermalinkConfig;
import com.spinn3r.api.PermalinkItem;

/**
 * 
 */
public class PostRate {

    /**
     * How far behind should be start by default?
     */
    public static long INTERVAL = 60L * 60L * 1000L;

    /**
     * The date of the last item we found.
     */
    private Date last = null;

    /**
     * Keeps track of the client that the user wants to use from the command
     * line.
     */
    private Client<? extends BaseItem> client = null;

    /**
     * Results from the last call.
     */
    private List<? extends BaseItem> results = null;

    private static long start = -1;

    private HashMap<Long,Integer> countRegistry = new HashMap<Long, Integer>();

    /**
     * Total number of downloaded items.
     */
    private int total = 0;
    
    public PostRate( Client<? extends BaseItem> client ) {
        this.client = client;
    }
    
    /**
     * Process results, handling them as necessary.
     */
    void process( List<? extends BaseItem> results ) throws Exception {

        //update the number of values for this item interval
        long interval = 60L * 1000L;

        for( BaseItem item : results ) {

            //update the state internally so we have a copy of the last item
            //found.
            Date ts   = item.getPubDate();
            last      = item.getPubDate();

            long slice = (long)(Math.floor((double)ts.getTime() / (double)interval) * interval);

            if ( ! countRegistry.containsKey( slice ) )
                countRegistry.put( slice, 0 );
            
            int current = countRegistry.get( slice );
            ++current ;
            countRegistry.put( slice, current );

        }

        total += results.size();

        long diff = (start + INTERVAL) - last.getTime();
        
        System.out.println( "---" );
        System.out.println( "Seconds behind:   " + ( diff / 1000 ) );
        System.out.println( "Minutes behind:   " + ( diff / (60*1000) ) );
        System.out.println( "Total :   " + total );

        if ( total % 100 == 0 && total != 0 ) {
            printStats();
        }
        
    }

    private void printStats() {

        System.out.println( "------" );
        System.out.println( "Current stats: " );
        
        for( long slice : countRegistry.keySet() ) {

            System.out.printf( "%s: %s\n", (int)(slice / 1000), countRegistry.get( slice ) );
            
        }
        
    }
    
    public void exec() throws Exception {

        while( true ) {

            //fetch the most recent results.  This will block if necessary.

            client.fetch();

            //get the results found from the last fetch.
            results = client.getResults();

            System.out.println( "Found N results: " + results.size() );

            process( results );

            if ( last.getTime() > start + INTERVAL ) {
                printStats();
                break;
            }

        }
        
    }

    public static void main( String[] args ) throws Exception {

        //parse out propeties.
        
        String vendor  = "spinn3r-debug";

        Config<PermalinkItem> config = null;
        Client<PermalinkItem> client = null;
        
        //config = new FeedConfig();
        //client = new FeedClient();

        config = new PermalinkConfig();
        client = new PermalinkClient();

        // set your vendor.  This is required.  Don't use the default.
        
        config.setVendor( vendor );

        // just english for right now
        //config.setLang( "en" );

        // Fetch for the last 5 minutes and then try to get up to date.  In
        // production you'd want to call setFirstRequestURL from the
        // getLastRequestURL returned from fetch() below
        
        long now = System.currentTimeMillis();
        start = now - INTERVAL;
        Date after = new Date( start ) ;
        config.setAfter( after );

        //1994-11-05T13:15:30Z

        //Date after = ISO8601DateParser.parse( "2008-07-04T00:38:00Z" );

        System.out.printf( "Finding records after: %s\n", after );
        config.setAfter( after );
        
        client.setConfig( config );

        new PostRate( client ).exec();
        
    }
    
}