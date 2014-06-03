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

import com.spinn3r.api.util.Getopt;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

        Config.addParam( params, "link",             URLEncoder.encode( link ) );
        Config.addParam( params, "vendor",           config.getVendor() );
        Config.addParam( params, "version",          config.getVersion() );
        Config.addParam( params, "force",            config.getForce() );
        Config.addParam( params, "feed",             config.getFeed() );
        Config.addParam( params, "publisher_type",   config.getPublisherType() );

        String resource = String.format( "http://%s/rss/source.register?%s", config.getHost(), params );

        // This is kind of a hack but mostly acceptable.  We should refactor at
        // some point though.
        SourceClient client = new SourceClient();
		client.setConfig(config);
        client.doXmlFetch( resource, config );

    }

    public void setConfig( SourceRegisterConfig config ) {
        this.config = config;
    }

    public static void syntax() {

        System.out.printf( "  --vendor=VENDOR   Specify a vendor code (required)." );
        System.out.printf( "  --file=FILE       File of URLs to read and bulk register." );
        System.out.printf( "URL...              URLs to register." );
    }

    public static void main( String[] args ) throws Exception {

        Getopt getopt = new Getopt( args );

        SourceRegisterClient client = new SourceRegisterClient();
        SourceRegisterConfig config = new SourceRegisterConfig();

        String vendor = getopt.getString( "vendor" );

        if ( vendor == null ) {
            syntax();
            throw new RuntimeException( "No vendor specified." );
        }

        if ( getopt.getString( "publisher_type" ) != null ) {
            config.setPublisherType( getopt.getString( "publisher_type" ) );
        }

        config.setVendor( vendor );

        client.setConfig( config );

        String file = getopt.getString( "file" );

        List<String> sources = null;

        if ( file != null ) {

            System.out.printf( "Registering sources from file: %s\n", file );

            File f = new File( file );
            FileInputStream fis = new FileInputStream( f );
            byte[] data = new byte[ (int)f.length() ];
            fis.read( data );
            fis.close();

            sources = new ArrayList<String>();

            for (String line : new String( data ).split( "\n" ) ) {
                sources.add( line );
            }

        } else {
            sources = getopt.getValues();
        }

        for (String source : sources) {

            System.out.printf( "register for %s\n", source );

            try {
                client.register( source );
            } catch ( java.io.IOException e ) {
                System.out.printf( "Failed to register %s\n%s\n", source, e );
            }

        }

    }
    
}
