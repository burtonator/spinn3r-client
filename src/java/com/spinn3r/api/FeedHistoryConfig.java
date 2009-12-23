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
import java.net.URLEncoder;

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;

/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public class FeedHistoryConfig extends Config {

    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;
    
    private String sourceHashcode = null;
    private String feedHashcode = null;
    private String feed = null;
    private String source = null; 

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


    @Override
    public Source createResultObject ( ContentApi.Entry entry ) {
        return new Source ( entry );
    }

    @Override
    public Source createResultObject ( Element current ) {
        return new Source ( current );
    }

    /**
     * 
     * Get the value of <code>sourceHashcode</code>.
     *
     */
    public String getSourceHashcode() { 
        return this.sourceHashcode;
    }

    /**
     * 
     * Set the value of <code>sourceHashcode</code>.
     *
     */
    public void setSourceHashcode( String sourceHashcode ) { 
        this.sourceHashcode = sourceHashcode;
    }

    /**
     * 
     * Get the value of <code>feedHashcode</code>.
     *
     */
    public String getFeedHashcode() { 
        return this.feedHashcode;
    }

    /**
     * 
     * Set the value of <code>feedHashcode</code>.
     *
     */
    public void setFeedHashcode( String feedHashcode ) { 
        this.feedHashcode = feedHashcode;
    }

    /**
     * 
     * Get the value of <code>feed</code>.
     *
     */
    public String getFeed() { 
        return this.feed;
    }

    /**
     * 
     * Set the value of <code>feed</code>.
     *
     */
    public void setFeed( String feed ) { 
        this.feed = feed;
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
        return String.format( "http://%s/rss/%s.history?", getHost(), BaseClient.FEED_HANDLER );
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

        addParam( params, "source",           URLEncoder.encode( getSource() ) , true );
        addParam( params, "feed",             URLEncoder.encode( getFeed() ) , true );
        addParam( params, "feed_hashcode",    getFeedHashcode() , true );
        addParam( params, "source_hashcode",  getSourceHashcode() , true );

        String result = getRouter() + params.toString();

        System.out.printf( "%s\n", result );
        
        return result;
        
    }

}