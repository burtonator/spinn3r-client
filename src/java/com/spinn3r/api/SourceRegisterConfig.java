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
public class SourceRegisterConfig extends Config {

    private String         vendor           = null;

    private String         version          = Config.DEFAULT_VERSION;

    private String         host             = Config.DEFAULT_HOST;

    private boolean        force            = false;

    private String         feed             = null;

    private String         publisherType    = null;

    /**
     * 
     * Set the value of <code>publisherType</code>.
     *
     */
    public void setPublisherType( String publisherType ) { 
        this.publisherType = publisherType;
    }

    /**
     * 
     * Get the value of <code>publisherType</code>.
     *
     */
    public String getPublisherType() { 
        return this.publisherType;
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
     * Specify the vendor for this call.  This MUST be specified or the client
     * will not work.
     *
     */
    public String getVendor() { 
        return this.vendor;
    }

    /**
     * 
     * Set the value of <code>vendor</code>.
     *
     */
    public void setVendor( String vendor ) { 
        this.vendor = vendor;
    }

    /**
     * 
     * Get the value of <code>version</code>.
     *
     */
    public String getVersion() { 
        return this.version;
    }

    /**
     * 
     * Set the value of <code>version</code>.
     *
     */
    public void setVersion( String version ) { 
        this.version = version;
    }

    /**
     * 
     * Get the value of <code>host</code>.
     *
     */
    public String getHost() { 
        return this.host;
    }

    /**
     * 
     * Set the value of <code>host</code>.
     *
     */
    public void setHost( String host ) { 
        this.host = host;
    }

    /**
     * 
     * Get the value of <code>force</code>.
     *
     */
    public boolean getForce() { 
        return this.force;
    }

    /**
     * 
     * Set the value of <code>force</code>.
     *
     */
    public void setForce( boolean force ) { 
        this.force = force;
    }

    @Override
    public String getRouter () {
        throw new RuntimeException ( "error not supporter" );
    }
}