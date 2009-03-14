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
import java.io.*;
import java.net.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.spinn3r.api.protobuf.*;

/**
 * <p> Reference API implementation for fetching Permalink content form Spinn3r.
 *
 * <p> This class is <b>NOT</b> threadsafe.  Implementations need to ensure
 * thread safety by using <code>synchronized</code> or
 * <code>java.util.concurrent</code> constructs.
 */
public class PermalinkClient extends BaseClient implements Client {
    
    /**
     * The permalink API can only handle 10 items at once since each piece of
     * content is SO huge.
     */
    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;

    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {
        super.fetch( config );
    }

    public List<BaseItem> getResults() { 
        return (List<BaseItem>)super.results;
    }

    
    protected BaseResult parseItem( ContentApi.Entry current ) throws Exception {
    
        PermalinkItem item = new PermalinkItem();

        return (BaseItem)super.parseItem( current, item );
    }


    protected BaseItem parseItem( Element current ) throws Exception {

        PermalinkItem item = new PermalinkItem();

        return (BaseItem)super.parseItem( current, item );
        
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

    public String getRouter() {
        String format_string = "http://%s/rss/%s.getDelta?"; 
 
        if ( config.getUseProtobuf() ) 
            format_string = "http://%s/protobuf/%s.getDelta?";

        return String.format( format_string, getHost(), BaseClient.PERMALINK_HANDLER );
    }

}