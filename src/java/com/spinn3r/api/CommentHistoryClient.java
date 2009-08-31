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

import com.spinn3r.api.protobuf.*;

/**
 * 
 */
public class CommentHistoryClient extends BaseClient implements Client {

    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
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

        if ( config.getSource() != null )
            addParam( params, "source",  URLEncoder.encode( config.getSource() ) );

        if ( config.getPermalink() != null )
            addParam( params, "permalink",  URLEncoder.encode( config.getPermalink() ) );

        if ( config.getFeed() != null )
            addParam( params, "feed",  URLEncoder.encode( config.getFeed() ) );

        //hashcodes
        if ( config.getSourceHashcode() != null )
            addParam( params, "source_hashcode",  URLEncoder.encode( config.getSourceHashcode() ) );

        if ( config.getPermalinkHashcode() != null )
            addParam( params, "permalink_hashcode",  URLEncoder.encode( config.getPermalinkHashcode() ) );

        if ( config.getFeedHashcode() != null )
            addParam( params, "feed_hashcode",  URLEncoder.encode( config.getFeedHashcode() ) );

        String result = getRouter() + params.toString();

        return result;
        
    }

    public List<BaseItem> getResults() { 
        return (List<BaseItem>)super.results;
    }

    protected BaseResult parseItem( ContentApi.Entry current ) throws Exception {
        throw new UnimplementedException ("protobuf support not implmented for this client");
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

        Map<String,String> opts = getopt( args );

        if ( opts.containsKey( "vendor" ) )
            config.setVendor( opts.get( "vendor" ) );
        else
            throw new RuntimeException( "Must specify vendor" );

        if ( opts.containsKey( "permalink_hashcode" ) )
            config.setPermalinkHashcode( opts.get( "permalink_hashcode" ) );

        if ( opts.containsKey( "permalink" ) )
            config.setPermalink( opts.get( "permalink" ) );

        client.setConfig( config );

        List results;

        while( client.hasMoreResults() ) {

            client.fetch();
            
            results = client.getResults();

            System.out.printf( "Found %s results: \n", results.size() );
            System.out.printf( "Last request URL: %s\n", client.getLastRequestURL() );

            dump( results );

        }

    }

}

