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

import org.w3c.dom.*;

import com.spinn3r.api.protobuf.*;

/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public class LinkConfig extends Config<LinkItem> {
    
    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;


    private Date after = null;


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
    public LinkItem createResultObject ( ContentApi.Entry entry ) throws ParseException {
        return new LinkItem ( entry );
    }

    @Override
    public LinkItem createResultObject ( Element current ) throws ParseException {
        return new LinkItem ( current );
    }



    /**
     * 
     * Set the value of <code>after</code>.
     *
     */
    public void setAfter( Date after ) { 
        this.after = after;
    }

    /**
     * 
     * Get the value of <code>after</code>.
     *
     */
    public Date getAfter() { 
        return this.after;
    }

    @Override
    public String getRouter() {

        String router = String.format( "http://%s/rss/%s.getDelta?", getHost(), BaseClient.LINK_HANDLER );

        return router;

    }
}