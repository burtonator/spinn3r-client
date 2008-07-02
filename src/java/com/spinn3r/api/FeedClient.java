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
 * <p> Reference API implementation for fetching Feed content form Spinn3r.
 *
 * <p> This class is <b>NOT</b> threadsafe.  Implementations need to ensure
 * thread safety by using <code>synchronized</code> or
 * <code>java.util.concurrent</code> constructs.
 */
public class FeedClient extends BaseClient implements Client {

    public static int MAX_LIMIT = 100;

    public static int OPTIMAL_LIMIT = 100;

    public static int CONSERVATIVE_LIMIT = 10;
    
    /**
     * Base router request URL.
     */
    public static String ROUTER = "http://api.spinn3r.com/rss/feed.getDelta?";

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {
        super.fetch( config );
    }

    /**
     * Check on the status of a weblog within Spinn3r.
     *
     * @throws FileNotFoundException when the weblog was not found in our index.
     */
    public Feed status( String link ) throws Exception {

        try {

            StringBuffer params = new StringBuffer();

            addParam( params, "link", link );
            addParam( params, "vendor",  config.getVendor() );
            addParam( params, "version", config.getVersion() );

            String resource = String.format( "http://%s/rss/feed.status?%s", getHost(), params );

            Document doc = doFetch( resource );

            Element root = (Element)doc.getFirstChild();

            Element channel = getElementByTagName( root, "channel" );

            Feed feed = new Feed( channel );

            return feed;

        } catch ( FileNotFoundException e ) {
            return null;
        }
        
    }

    protected BaseItem parseItem( Element current ) throws Exception {

        FeedItem item = new FeedItem();

        return super.parseItem( current, item );
        
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
        return "http://" + getHost() + "/rss/feed.getDelta?";
    }

    public static void main( String[] args ) throws Exception {

        FeedClient client = new FeedClient();

        Config config = new Config();
        config.setVendor( "XXXX" );

        client.setConfig( config );

        String method     = args[0];
        String resource   = args[1];

        System.out.printf( "%s for %s\n", method, resource );

        if ( method.equals( "status" ) ) {

            Feed feed = client.status( resource );

            if ( feed == null ) {
                System.out.printf( "Not found\n" );
                return;
            }

            feed.dump();

        }
            
    }

}