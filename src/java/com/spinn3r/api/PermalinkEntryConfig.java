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
public class PermalinkEntryConfig extends Config<PermalinkItem> {

    /**
     * When we've ran out of results (because the client is up to date) then we
     * should spin for a few seconds.  If the sleep interval is -1 then we sleep
     * for a random amount of time between 0 and 30 seconds.
     *
     * Default: Zero since it's usual for the permalinks status API to return <
     * 10 results.
     */
    public static final long DEFAULT_SLEEP_INTERVAL = 0L;

    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;

    private String           resource               = null; 
    private String           id                     = null;

    /**
     * How long we should sleep if an API call doesn't return enough values.
     */
    private long sleepInterval = DEFAULT_SLEEP_INTERVAL;


    @Override
    public PermalinkItem createResultObject ( ContentApi.Entry entry ) throws ParseException {
        return new PermalinkItem ( entry );
    }

    @Override
    public PermalinkItem createResultObject ( Element current ) throws ParseException {
        return new PermalinkItem ( current );
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
     * Get the value of <code>id</code>.
     *
     */
    public String getId() { 
        return this.id;
    }

    /**
     * 
     * Set the value of <code>id</code>.
     *
     */
    public void setId( String id ) { 
        this.id = id;
    }

    /**
     * 
     * Get the value of <code>resource</code>.
     *
     */
    public String getResource() { 
        return this.resource;
    }

    /**
     * 
     * Set the value of <code>resource</code>.
     *
     */
    public void setResource( String resource ) { 
        this.resource = resource;
    }

    public long getSleepInterval() {
        return sleepInterval;
    }
    
    @Override
    public String getRouter() {
        return String.format( "http://%s/%s/%s.entry?", getHost(), 
        		getFormat().getURLEntry(), "permalink3" );
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
        addParam( params, "after", toISO8601(getAfter()));

        if ( getResource() != null ) {
            
            try {
                addParam( params, "resource", URLEncoder.encode( getResource(), "UTF-8" ) );
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        if ( getId() != null )
            addParam( params, "id", getId() );

        String result = getRouter() + params.toString();

        return result;
        
    }
}