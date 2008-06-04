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

/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public class SourceListConfig extends Config {

    private Date           publishedAfter               = null; 
    private Date           foundAfter                   = null; 

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

}