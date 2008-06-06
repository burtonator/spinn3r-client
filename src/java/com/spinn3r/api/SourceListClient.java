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
import java.net.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * 
 */
public class SourceListClient extends BaseClient implements Client {

    /**
     * When we've ran out of results (because the client is up to date) then we
     * should spin for a few seconds.  If the sleep interval is -1 then we sleep
     * for a random amount of time between 0 and 30 seconds.
     *
     * Default: Zero since it's usual for the permalinks status API to return <
     * 10 results.
     */
    public static final long DEFAULT_SLEEP_INTERVAL = 0L;

    public static int MAX_LIMIT = 250;

    public static int OPTIMAL_LIMIT = 250;

    public static int CONSERVATIVE_LIMIT = 10;

    /**
     * Base router request URL.
     */
    public static String ROUTER = "http://api.spinn3r.com/rss/source.list?";

    /**
     * How long we should sleep if an API call doesn't return enough values.
     */
    private long sleepInterval = DEFAULT_SLEEP_INTERVAL;

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {
        
        super.fetch( config );
    }

    /**
     * Generate the first request URL based just on configuration directives.
     */
    protected String generateFirstRequestURL() {

        SourceListConfig config = (SourceListConfig)super.getConfig();
        
        StringBuffer params = new StringBuffer( 1024 ) ;

        addParam( params, "limit",   getOptimalLimit() );
        addParam( params, "vendor",  config.getVendor() );
        addParam( params, "version", config.getVersion() );

        //AFTER param needs to be added which should be ISO8601

        if ( config.getPublishedAfter() != null )
            addParam( params, "published_after",   toISO8601( config.getPublishedAfter() ) );

        if ( config.getFoundAfter() != null )
            addParam( params, "found_after",   toISO8601( config.getFoundAfter() ) );

        return getRouter() + params.toString();
        
    }

    protected BaseItem parseItem( Element current ) throws Exception {
        return new Source( current );
    }

    protected int getMaxLimit() {
        return MAX_LIMIT;
    }

    protected int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }

    protected int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
    }

    public String getRouter() {
        return "http://" + getHost() + "/rss/source.list?";
    }

    public long getSleepInterval() {
        return sleepInterval;
    }

    public static void main( String[] args ) throws Exception {

        SourceListConfig config = new SourceListConfig();
        SourceListClient client = new SourceListClient();

        config.setVendor( "XXXX" );

        //start with an arbitrary date
        Date date = new Date( 1210661536159L );

        //We're looking to find new weblogs as we find them.  Alternatively, one
        //could use setPublishedAfter to detect when we find new URLs.
        config.setFoundAfter( date );

        //tell the client to use this specific config.
        client.setConfig( config );

        while( true ) {

            //connect to the network and fetch the next batch of results.
            client.fetch();

            //get the the results in a data structure that we can manipulate
            List<Source> results = client.getResults();

            //iterate over each source.
            for( Source source : results ) {

                System.out.printf( "source title:           %s\n", source.getTitle() );
                System.out.printf( "source description:     %s\n", source.getDescription() );

                System.out.printf( "source guid:            %s\n", source.getGuid() );
                System.out.printf( "source resource:        %s\n", source.getResource() );
                System.out.printf( "source resource status: %s\n", source.getResourceStatus() );
                System.out.printf( "source link:            %s\n", source.getLink() );
                System.out.printf( "source date_found:      %s\n", source.getDateFound() );
                System.out.printf( "source tier:            %s\n", source.getTier() );
                System.out.printf( "source indegree:        %s\n", source.getIndegree() );
                System.out.printf( "source disabled:        %s\n", source.getDisabled() );

                //get feed specific data.
                Feed feed = source.getFeed();

                System.out.printf( "feed guid:              %s\n", feed.getGuid() );
                System.out.printf( "feed resource:          %s\n", feed.getResource() );
                System.out.printf( "feed resource status:   %s\n", feed.getResourceStatus() );
                System.out.printf( "feed link:              %s\n", feed.getLink() );
                System.out.printf( "feed last published:    %s\n", feed.getLastPublished() );
                System.out.printf( "feed date found:        %s\n", feed.getDateFound() );
                System.out.printf( "feed title:             %s\n", feed.getChannelTitle() );
                System.out.printf( "feed link:              %s\n", feed.getChannelLink() );
                System.out.printf( "feed description:       %s\n", feed.getChannelDescription() );
                System.out.printf( "feed etag:              %s\n", feed.getEtag() );

                System.out.printf( "---\n" );

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

