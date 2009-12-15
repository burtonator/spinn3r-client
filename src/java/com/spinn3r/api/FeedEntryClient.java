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
public class FeedEntryClient extends LegacyWrapperClient<FeedItem> {


    public static void main( String[] args ) throws Exception {

        FeedEntryConfig config = new FeedEntryConfig();
        FeedEntryClient client = new FeedEntryClient();
        
        config.setVendor( "debug" );
        config.setVersion( "2.2.1" );

        config.setLimit( 1 );
        
        if ( args[0].startsWith( "http:" ) ) {
            config.setResource( args[0] );
        } else {
            config.setId( args[0] );
        }

        client.setConfig( config );

        client.fetch();
        List<FeedItem> results = client.getResults();

        System.out.printf( "%s\n", client.getLastRequestURL() );
        System.out.printf( "DUMP: Found %d items\n" , results.size() );

        for( FeedItem item : results ) {

            System.out.println( "----" );
            System.out.println( "hashcode:               " + item.getPostHashcode() );
            System.out.println( "link:                   " + item.getLink() );
            System.out.println( "guid:                   " + item.getGuid() );
            System.out.println( "feed URL:               " + item.getFeedURL() );

            System.out.println( "title:                  " + item.getTitle() );
            System.out.println( "source:                 " + item.getSource() );
            System.out.println( "pubDate:                " + item.getPubDate() );
            System.out.println( "published:              " + item.getPublished() );
            
            System.out.println( "weblog title:           " + item.getWeblogTitle() );
            System.out.println( "weblog tier:            " + item.getWeblogTier() );
            System.out.println( "weblog publisher type:  " + item.getWeblogPublisherType() );
            System.out.println( "weblog indegree:        " + item.getWeblogIndegree() );
            
            System.out.println( "author name:            " + item.getAuthorName() );
            System.out.println( "author email:           " + item.getAuthorEmail() );
            System.out.println( "author link:            " + item.getAuthorLink() );

            System.out.println( "lang:                   " + item.getLang() );
            System.out.println( "tags:                   " + item.getTags() );
            
            System.out.println( "description: " );
            System.out.println( "-" );
            System.out.println( item.getDescription() );
            System.out.println( "-" );

            System.out.println( "content extract: " );
            System.out.println( "-" );
            System.out.println( item.getContentExtract() );
            System.out.println( "-" );

        }
        
    }

}

