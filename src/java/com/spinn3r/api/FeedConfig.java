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
public class FeedConfig extends Config {

    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 100;
    public static int CONSERVATIVE_LIMIT   = 10;


    protected int getMaxLimit() {
        return MAX_LIMIT;
    }

    protected int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }

    protected int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
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
        return String.format( "http://%s/rss/%s.getDelta?", getHost(), BaseClient.FEED_HANDLER );
    }

}