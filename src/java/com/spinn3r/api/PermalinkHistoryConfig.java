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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;

/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public class PermalinkHistoryConfig extends Config<Source> {


    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;

    private String           source               = null; 

    @Override
    public Source createResultObject ( ContentApi.Entry entry ) throws ParseException {
        return new Source ( entry );
    }

    @Override
    public Source createResultObject ( Element current ) throws ParseException {
        return new Source ( current );
    }


    @Override
    protected int getMaxLimit() {
        return MAX_LIMIT;
    }

    @Override
    protected int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }

    @Override
    protected int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
    }


    /**
     * 
     * Get the value of <code>source</code>.
     *
     */
    public String getSource() { 
        return this.source;
    }

    /**
     * 
     * Set the value of <code>source</code>.
     *
     */
    public void setSource( String source ) { 
        this.source = source;
    }


    @Override
    public String getRouter() {
        return String.format( "http://%s/%s/%s.history?", getHost(), getFormat().getURLEntry(), BaseClient.PERMALINK_HANDLER );
    }

    /**
     * Generate the first request URL based just on configuration directives.
     */
    @Override
    public String generateFirstRequestURL( int request_limit ) {

        StringBuffer params = new StringBuffer( 1024 ) ;

        if ( request_limit > getMaxLimit() )
            request_limit = getMaxLimit();
        
        addParam( params, "limit",   request_limit );
        addParam( params, "vendor",  getVendor() );
        addParam( params, "version", getVersion() );
        
        try {
            addParam( params, "source",  URLEncoder.encode( getSource(), "UTF-8" ) );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String result = getRouter() + params.toString();

        //System.out.printf( "%s\n", result );
        
        return result;
        
    }

}