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
public class SourceClient extends BaseClient implements Client {

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {

        super.fetch( config );

    }

    public Source status( String link ) throws Exception {

        StringBuffer params = new StringBuffer();

        addParam( params, "link", link );
        addParam( params, "vendor",  config.getVendor() );
        addParam( params, "version", config.getVersion() );
        
        Document doc = doFetch( getRouter() + params );

        Source source = new Source();

        Element root = (Element)doc.getFirstChild();

        Element channel = getElementByTagName( root, "channel" );

        //determine the next_request_url so that we can fetch the second page of
        //results.
        source.setTitle( getElementCDATAByTagName( channel, "title" ) );
        source.setLink( getElementCDATAByTagName( channel, "link" ) );
        source.setDescription( getElementCDATAByTagName( channel, "description" ) );
        source.setGuid( getElementCDATAByTagName( channel, "guid", NS_WEBLOG ) );

        return source;
        
    }

    public String getRouter() {
        return "http://" + getHost() + "/rss/source.status?";
    }

    public static void main( String[] args ) throws Exception {

        SourceClient client = new SourceClient();

        Config config = new Config();
        config.setVendor( "test" );
        
        client.setConfig( config );
        client.setHost( "dev.api.spinn3r.com" );

        String resource = args[0];

        System.out.printf( "Fetching status for: %s\n", resource );
        
        Source source = client.status( resource );

        System.out.printf( "         title: %s\n", source.getTitle() );
        System.out.printf( "          link: %s\n", source.getLink() );
        System.out.printf( "   description: %s\n", source.getDescription() );
        System.out.printf( "          guid: %s\n", source.getGuid() );
        
    }
    
}