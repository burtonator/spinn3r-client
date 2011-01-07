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

import java.util.List;
import java.util.Map;

import com.spinn3r.util.ISO8601DateParser;
/**
 * 
 */
public class SourceListClient extends LegacyWrapperClient<Source> {


    /**
     * Print a source to stdout.
     *
     */
    public static void printf( Source source ) {

        source.dump();
        
        //get feed specific data.
        Feed feed = source.getFeed();

        feed.dump();
        
        System.out.printf( "---\n" );

    }
    
    public static void main( String[] args ) throws Exception {

        SourceListConfig config = new SourceListConfig();
        SourceListClient client = new SourceListClient();

        Map<String,String> opts = getopt( args );

        if ( opts.containsKey( "vendor" ) )
            config.setVendor( opts.get( "vendor" ) );
        else
            throw new RuntimeException( "Must specify vendor" );

        if ( opts.containsKey( "published_after" ) ) {
            String date = opts.get( "published_after" );
            config.setPublishedAfter( ISO8601DateParser.parse( date ) );
        } else if ( opts.containsKey( "posted_after" ) ) {
            String date = opts.get( "posted_after" );
            config.setPostedAfter( ISO8601DateParser.parse( date ) );
        } else if ( opts.containsKey( "found_after" ) ) {
            String date = opts.get( "found_after" );
            config.setFoundAfter( ISO8601DateParser.parse( date ) );
        } else {
            throw new RuntimeException( "Specify published_after, posted_after, or found_after." );
        }
            
        //tell the client to use this specific config.
        client.setConfig( config );

        while( true ) {

            //connect to the network and fetch the next batch of results.
            client.fetch();

            //get the the results in a data structure that we can manipulate
            List<Source> results = client.getResults();

            //iterate over each source.
            for( Source source : results ) {
                printf( source );
            }

            System.out.printf( "Last request URL: %s\n", client.getLastRequestURL() );
            System.out.printf( "Next request URL: %s\n", client.getNextRequestURL() );
            System.out.printf( "---\n" );

            //optionally we dan determine if there are no more resources by
            //detecting if we didn't find a full page.
            if ( config.getLimit() != results.size() )
                break;
            
        }

    }

}

