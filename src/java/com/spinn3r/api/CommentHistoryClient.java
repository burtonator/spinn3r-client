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
public class CommentHistoryClient extends BaseClient implements Client {

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

        CommentHistoryConfig config = (CommentHistoryConfig)super.getConfig();
        
        StringBuffer params = new StringBuffer( 1024 ) ;

        int limit = config.getLimit();
        
        if ( limit > getMaxLimit() )
            limit = getMaxLimit();
        
        addParam( params, "limit",   limit );
        addParam( params, "vendor",  config.getVendor() );
        addParam( params, "version", config.getVersion() );
        addParam( params, "source",  URLEncoder.encode( config.getSource() ) );

        String result = getRouter() + params.toString();

        System.out.printf( "%s\n", result );
        
        return result;
        
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
        return String.format( "http://%s/rss/%s.history?", getHost(), BaseClient.COMMENT_HANDLER );
    }

    public static void dump( List<BaseItem> results ) {


        for( BaseItem item : results ) {
            System.out.println( "----" );
            System.out.println( "link:                   " + item.getLink() );
            System.out.println( "title:                  " + item.getTitle() );
            System.out.println( "pubDate:                " + item.getPubDate() );
            System.out.println( "published:              " + item.getPublished() );
            System.out.println( "-" );
            System.out.printf( "%s\n", item.getDescription() );
        }

    }
    
    public static void main( String[] args ) throws Exception {

        //example comment history usage.
        
        CommentHistoryConfig config = new CommentHistoryConfig();
        CommentHistoryClient client = new CommentHistoryClient();

        config.setVendor( args[0] );
        config.setSource( args[1] );

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

