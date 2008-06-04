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
public class SourceRegisterClient {

    private SourceRegisterConfig config = null;
    
    /**
     * Register a new weblog within Spinn3r.
     */
    public void register( String link ) throws Exception {

        StringBuffer params = new StringBuffer();

        BaseClient.addParam( params, "link",       link );
        BaseClient.addParam( params, "vendor",     config.getVendor() );
        BaseClient.addParam( params, "version",    config.getVersion() );
        BaseClient.addParam( params, "force",      config.getForce() );
        
        String resource = String.format( "http://%s/rss/source.register?%s", config.getHost(), params );

        //this is kind of a hack but mostly acceptable.  We should refactor at
        //some point though.
        SourceClient client = new SourceClient();
        Document doc = client.doFetch( resource );

    }

    private void setConfig( SourceRegisterConfig config ) {
        this.config = config;
    }
    
    public static void main( String[] args ) throws Exception {

        SourceRegisterClient client = new SourceRegisterClient();
        SourceRegisterConfig config = new SourceRegisterConfig();

        config.setVendor( "debug" );
        config.setHost( "dev.api.spinn3r.com" );

        client.setConfig( config );

        String resource   = args[0];

        System.out.printf( "register for %s\n", resource );

        client.register( resource );
            
    }
    
}