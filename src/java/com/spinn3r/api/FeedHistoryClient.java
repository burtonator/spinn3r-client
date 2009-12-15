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
public class FeedHistoryClient extends LegacyWrapperClient<Source> {


    public static void dump( List<Source> results ) {

        for( Source item : results ) {
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

        config.setHost( "api.spinn3r.com" );
        client.setConfig( config );

        List<Source> results;
        
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

