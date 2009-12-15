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
public class LinkHistoryClient extends LegacyWrapperClient<LinkItem> {


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

        LinkHistoryConfig config = new LinkHistoryConfig();
        LinkHistoryClient client = new LinkHistoryClient();

        //config.setVersion( "2.);
        config.setVendor( args[0] );
        config.setSource( args[1] );
        config.setHost( "dev.api.spinn3r.com" );

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

