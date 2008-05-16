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
public class SourceListClient extends BaseClient implements Client {

    public static int MAX_LIMIT = 250;

    public static int OPTIMAL_LIMIT = 250;

    public static int CONSERVATIVE_LIMIT = 10;

    /**
     * Base router request URL.
     */
    public static String ROUTER = "http://api.spinn3r.com/rss/source.list?";

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {
        
        super.fetch( config );
    }

    /**
     * Generate the first request URL based just on configuration directives.
     */
    protected String generateFirstRequestURL() {

        SourceListConfig config = (SourceListConfig)super.getConfig();
        
        StringBuffer params = new StringBuffer( 1024 ) ;

        addParam( params, "limit",   getOptimalLimit() );
        addParam( params, "vendor",  config.getVendor() );
        addParam( params, "version", config.getVersion() );

        //AFTER param needs to be added which should be ISO8601

        if ( config.getPublishedAfter() != null )
            addParam( params, "published_after",   toISO8601( config.getPublishedAfter() ) );

        if ( config.getFoundAfter() != null )
            addParam( params, "found_after",   toISO8601( config.getFoundAfter() ) );

        return getRouter() + params.toString();
        
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
        return "http://" + getHost() + "/rss/source.list?";
    }

    public static void main( String[] args ) throws Exception {

        SourceListConfig config = new SourceListConfig();
        SourceListClient client = new SourceListClient();

        config.setVendor( "XXXX" );
        config.setVersion( "2.2.1" );
        
        client.setConfig( config );

        client.fetch();

        List results = client.getResults();

    }

}

