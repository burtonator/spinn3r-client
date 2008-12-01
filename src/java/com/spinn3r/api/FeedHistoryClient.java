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
public class FeedHistoryClient extends BaseClient implements Client {

    public static int MAX_LIMIT            = 10;
    public static int OPTIMAL_LIMIT        = 10;
    public static int CONSERVATIVE_LIMIT   = 10;

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {
        
        super.fetch( config );
    }

    /**
     * Generate the first request URL based just on configuration directives.
     */
    protected String generateFirstRequestURL() {

        FeedHistoryConfig config = (FeedHistoryConfig)super.getConfig();
        
        StringBuffer params = new StringBuffer( 1024 ) ;

        int limit = config.getLimit();
        
        if ( limit > getMaxLimit() )
            limit = getMaxLimit();

        addParam( params, "limit",   limit );
        addParam( params, "vendor",  config.getVendor() );
        addParam( params, "version", config.getVersion() );

        addParam( params, "source",           URLEncoder.encode( config.getSource() ) , true );
        addParam( params, "feed",             URLEncoder.encode( config.getFeed() ) , true );
        addParam( params, "feed_hashcode",    config.getFeedHashcode() , true );
        addParam( params, "source_hashcode",  config.getSourceHashcode() , true );

        String result = getRouter() + params.toString();

        System.out.printf( "%s\n", result );
        
        return result;
        
    }

    public List<BaseItem> getResults() { 
        return (List<BaseItem>)super.results;
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
        return String.format( "http://%s/rss/%s.history?", getHost(), BaseClient.FEED_HANDLER );
    }

    public static void dump( List<BaseItem> results ) {

        for( BaseItem item : results ) {
            System.out.println( "link:                   " + item.getLink() );
            System.out.println( "title:                  " + item.getTitle() );
            System.out.println( "pubDate:                " + item.getPubDate() );
            System.out.println( "published:              " + item.getPublished() );
            System.out.println( "-" );
        }

    }
    
    public static void main( String[] args ) throws Exception {

        BaseClient.FEED_HANDLER = "feed3";
        
        FeedHistoryConfig config = new FeedHistoryConfig();
        FeedHistoryClient client = new FeedHistoryClient();

        //config.setVersion( "2.);
        config.setVendor( args[0] );
        config.setFeedHashcode( args[1] );

        client.setHost( "dev.api.spinn3r.com" );
        client.setConfig( config );

        List results;
        
        client.fetch();
        results = client.getResults();
        dump( results );
        System.out.printf( "DUMP: Found %d items\n" , results.size() );

        client.fetch();
        results = client.getResults();
        dump( results );
        System.out.printf( "DUMP: Found %d items\n" , results.size() );

    }

}

