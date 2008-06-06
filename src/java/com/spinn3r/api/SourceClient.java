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
 * API client that implements Spinn3r's source registration API.
 */
public class SourceClient extends BaseClient implements Client {

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {

        super.fetch( config );

    }

    /**
     * Register a new weblog within Spinn3r.
     * 
     * @throws Exception when registration fails.
     */
    public void register( String link ) throws Exception {

        StringBuffer params = new StringBuffer();

        addParam( params, "link",       link );
        addParam( params, "vendor",     config.getVendor() );
        addParam( params, "version",    config.getVersion() );
        //addParam( params, "force",      "true" );
        
        String resource = String.format( "http://%s/rss/source.register?%s", getHost(), params );
        
        Document doc = doFetch( resource );

    }

    /**
     * Check on the status of a weblog within Spinn3r.
     *
     * @throws FileNotFoundException when the weblog was not found in our index.
     */
    public Source status( String link ) throws Exception {

        try {

            StringBuffer params = new StringBuffer();

            addParam( params, "link", link );
            addParam( params, "vendor",  config.getVendor() );
            addParam( params, "version", config.getVersion() );

            String resource = String.format( "http://%s/rss/source.status?%s", getHost(), params );

            Document doc = doFetch( resource );

            Source source = new Source();

            Element root = (Element)doc.getFirstChild();

            Element channel = getElementByTagName( root, "channel" );

            //determine the next_request_url so that we can fetch the second page of
            //results.
            source.setTitle( getElementCDATAByTagName( channel, "title" ) );
            source.setLink( getElementCDATAByTagName( channel, "link" ) );
            source.setDescription( getElementCDATAByTagName( channel, "description" ) );
            source.setGuid( getElementCDATAByTagName( channel, "guid", NS_WEBLOG ) );
            source.setDateFound( ISO8601DateParser.parse( getElementCDATAByTagName( channel, "date_found", NS_WEBLOG ) ) );
            source.setIndegree( Integer.parseInt( getElementCDATAByTagName( channel, "indegree", NS_WEBLOG ) ) );
            
            //FIXME: feed info
            
            return source;

        } catch ( FileNotFoundException e ) {
            return null;
        }
        
    }

    public String getRouter() {
        return "http://" + getHost() + "/rss/source.status?";
    }

    public static void main( String[] args ) throws Exception {

        SourceClient client = new SourceClient();

        Config config = new Config();
        config.setVendor( "biz360" );
        
        client.setConfig( config );

        client.setHost( "dev.api.spinn3r.com" );

        String method     = args[0];
        String resource   = args[1];

        System.out.printf( "%s for %s\n", method, resource );

        if ( method.equals( "register" ) ) {

            client.register( resource );

        } else if ( method.equals( "status" ) ) {

            Source source = client.status( resource );

            if ( source == null ) {
                System.out.printf( "Not found\n" );
                return;
            }

            System.out.printf( "         title: %s\n", source.getTitle() );
            System.out.printf( "          link: %s\n", source.getLink() );
            System.out.printf( "   description: %s\n", source.getDescription() );
            System.out.printf( "    date_found: %s\n", source.getDateFound() );
            System.out.printf( "      indegree: %s\n", source.getIndegree() );

        }
            
    }
    
}