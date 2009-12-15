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
public class CommentHistoryClient extends LegacyWrapperClient<Source> {



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

