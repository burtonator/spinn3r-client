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
public class CommentHistoryConfig extends Config<Source> {
    
    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;

    private String permalinkHashcode = null;
    
    private String feedHashcode = null;
    
    private String sourceHashcode = null;
    
    private String feed = null;
    
    private String permalink = null;

    private String source = null; 


    protected int getMaxLimit() {
        return MAX_LIMIT;
    }

    protected int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }

    protected int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
    }


    public Source createResultObject ( ContentApi.Entry entry ) {
        return new Source ( entry );
    }

    public Source createResultObject ( Element current ) {
        return new Source ( current );
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

    /**
     * 
     * Get the value of <code>permalink</code>.
     *
     */
    public String getPermalink() { 
        return this.permalink;
    }

    /**
     * 
     * Set the value of <code>permalink</code>.
     *
     */
    public void setPermalink( String permalink ) { 
        this.permalink = permalink;
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
     * Get the value of <code>permalinkHashcode</code>.
     *
     */
    public String getPermalinkHashcode() { 
        return this.permalinkHashcode;
    }

    /**
     * 
     * Set the value of <code>permalinkHashcode</code>.
     *
     */
    public void setPermalinkHashcode( String permalinkHashcode ) { 
        this.permalinkHashcode = permalinkHashcode;
    }

    @Override
    public String getRouter() {
        return String.format( "http://%s/rss/%s.history?", getHost(), BaseClient.COMMENT_HANDLER );
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

        if ( getSource() != null )
            addParam( params, "source",  URLEncoder.encode( getSource() ) );

        if ( getPermalink() != null )
            addParam( params, "permalink",  URLEncoder.encode( getPermalink() ) );

        if ( getFeed() != null )
            addParam( params, "feed",  URLEncoder.encode( getFeed() ) );

        //hashcodes
        if ( getSourceHashcode() != null )
            addParam( params, "source_hashcode",  URLEncoder.encode( getSourceHashcode() ) );

        if ( getPermalinkHashcode() != null )
            addParam( params, "permalink_hashcode",  URLEncoder.encode( getPermalinkHashcode() ) );

        if ( getFeedHashcode() != null )
            addParam( params, "feed_hashcode",  URLEncoder.encode( getFeedHashcode() ) );

        String result = getRouter() + params.toString();

        return result;
        
    }
}