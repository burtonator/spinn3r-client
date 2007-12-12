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

import com.spinn3r.api.*;

import java.util.*;
import java.io.*;

/**
 * Command line debug client for testing the API.  Also shows example usage.
 * 
 * This class will fetch the current API results as of INTERVAL minutes and then
 * keep fetching until it's up to date.
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

    private int total = 0;

    private static long start = -1;
    
    public PostRate( Client client ) {
        this.client = client;
    }
    
    /**
     * Process results, handling them as necessary.
     */
    void process( List<BaseItem> results ) throws Exception {

        for( BaseItem item : results ) {
            ++total;

            //update the state internally so we have a copy of the last item
            //found.
            last = item.getPubDate();
            
        }
        
    }

    public void exec() throws Exception {

        while( true ) {

            //fetch the most recent results.  This will block if necessary.

            long fetch_before = System.currentTimeMillis();

            client.fetch();

            long fetch_after  = System.currentTimeMillis();

            //get the results found from the last fetch.
            results = client.getResults();

            System.out.println( "Found N results: " + results.size() );

            process( results );

            System.out.println( "total: " + total );
            
            if ( last.getTime() > start + INTERVAL )
                break;

        }
        
    }

    public static void main( String[] args ) throws Exception {

        //parse out propeties.
        
        String vendor  = "spinn3r-debug";

        Config config = null;
        Client client = null;
        
        config = new FeedConfig();
        client = new FeedClient();
        
        // set your vendor.  This is required.  Don't use the default.
        
        config.setVendor( vendor );

        // just english for right now
        //config.setLang( "en" );

        // Fetch for the last 5 minutes and then try to get up to date.  In
        // production you'd want to call setFirstRequestURL from the
        // getLastRequestURL returned from fetch() below
        
        long now = System.currentTimeMillis();
        start = now - INTERVAL;
        config.setAfter( new Date( start ) );
        
        client.setConfig( config );

        new PostRate( client ).exec();
        
    }
    
}