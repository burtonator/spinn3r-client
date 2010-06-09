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

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;

/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public class SourceListConfig extends Config<Source> {
    
    public static int  MAX_LIMIT              = 250;
    public static int  OPTIMAL_LIMIT          = 250;
    public static int  CONSERVATIVE_LIMIT     = 10;
    public static long DEFAULT_SLEEP_INTERVAL = 0L;

    private Date           postedAfter                  = null;
    private Date           publishedAfter               = null; 
    private Date           foundAfter                   = null; 


    @Override
    public Source createResultObject ( ContentApi.Entry entry ) throws ParseException {
        return new Source ( entry );
    }

    @Override
    public Source createResultObject ( Element current ) throws ParseException {
        return new Source ( current );
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


    public long getSleepInterval() {
        return DEFAULT_SLEEP_INTERVAL;
    }

    /**
     * 
     * Get the value of <code>publishedAfter</code>.
     *
     */
    public Date getPublishedAfter() { 
        return this.publishedAfter;
    }

    /**
     * 
     * Set the value of <code>publishedAfter</code>.
     *
     */
    public void setPublishedAfter( Date publishedAfter ) { 
        this.publishedAfter = publishedAfter;
    }

    /**
     * 
     * Get the value of <code>foundAfter</code>.
     *
     */
    public Date getFoundAfter() { 
        return this.foundAfter;
    }

    /**
     * 
     * Set the value of <code>foundAfter</code>.
     *
     */
    public void setFoundAfter( Date foundAfter ) { 
        this.foundAfter = foundAfter;
    }

    /**
     * 
     * Get the value of <code>postedAfter</code>.
     *
     */
    public Date getPostedAfter() { 
        return this.postedAfter;
    }

    /**
     * 
     * Set the value of <code>postedAfter</code>.
     *
     */
    public void setPostedAfter( Date postedAfter ) { 
        this.postedAfter = postedAfter;
    }

    @Override
    public String getRouter() {
        return "http://" + getHost() + "/rss/source.list?";
    }


    /**
     * Generate the first request URL based just on configuration directives.
     */
    public String generateFirstRequestURL() {
        
        StringBuffer params = new StringBuffer( 1024 ) ;

        addParam( params, "limit",   getOptimalLimit() );
        addParam( params, "vendor",  getVendor() );
        addParam( params, "version", getVersion() );

        //AFTER param needs to be added which should be ISO8601

        if ( getPublishedAfter() != null )
            addParam( params, "published_after",   toISO8601( getPublishedAfter() ) );

        if ( getPostedAfter() != null )
            addParam( params, "posted_after",   toISO8601( getPostedAfter() ) );

        if ( getFoundAfter() != null )
            addParam( params, "found_after",   toISO8601( getFoundAfter() ) );

        return getRouter() + params.toString();
        
    }

}