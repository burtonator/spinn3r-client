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

/**
 * New and BETA event listener for detecting events in the Spinn3r API core.
 * 
 * This was originally written as a means to obtain state for logging
 * information for command line clients.  Moving forward, it may have other
 * uses.
 * 
 */
public interface EventListener {

    /**
     * Called when the API core requests a set of results from Spinn3r.
     */
    public void onRequest( String resource );

    /**
     * Called when the API core has received a response from Spinn3r.
     */
    public void onResponse( String resource );

    /**
     * Called when a new item is found.
     */
    public void onItem( BaseItem item );

    /**
     * Called when we found results.
     */
    public void onResults( List<BaseItem> items );

    /**
     * Called before we sleep when insuficient results are found.
     */
    public void onSleep();
    
}