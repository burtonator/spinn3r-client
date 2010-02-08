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
public class PermalinkConfig extends Config<PermalinkItem> {
    
    private static int MAX_LIMIT            = 5000;
    
    private static int OPTIMAL_LIMIT        = 1500;

    private static int CONSERVATIVE_LIMIT   = 100;

    private Date after = null;

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
    public PermalinkItem createResultObject ( ContentApi.Entry entry ) throws ParseException {
        return new PermalinkItem ( entry );
    }

    @Override
    public PermalinkItem createResultObject ( Element current ) throws ParseException {
        return new PermalinkItem ( current );
    }


    @Override
    public String getRouter() {
 
       
       String result = String.format( "http://%s/%s/%s.getDelta?", getHost(), getFormat().getURLEntry(),
    		   BaseClient.PERMALINK_HANDLER );

       return result;
       
    }


    public int getMaxLimit() {
        return MAX_LIMIT;
    }

    public int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }

    public int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
    }

}