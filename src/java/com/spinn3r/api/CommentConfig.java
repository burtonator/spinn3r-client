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

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi.Entry;


/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public class CommentConfig extends Config<CommentItem> {
    
    private static final int MAX_LIMIT            = 5000;
    
    private static final int OPTIMAL_LIMIT        = 1500;

    private static final int CONSERVATIVE_LIMIT   = 100;

    @Override
    public String getRouter() {
        
        String router = String.format( "http://%s/rss/%s.getDelta?", getHost(), BaseClient.COMMENT_HANDLER );

        return router;

    }

    @Override
    public CommentItem createResultObject(Element current)
            throws ParseException {
        return new CommentItem(current);
    }

    @Override
    int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
    }

    @Override
    int getMaxLimit() {
        return MAX_LIMIT;
    }

    @Override
    int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }



    @Override
    public CommentItem createResultObject(Entry entry) throws ParseException {
        // TODO Auto-generated method stub
        return null;
    }


}