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
public class Config {

    /**
     * When we've ran out of results (because the client is up to date) then we
     * should spin for a few seconds.  If the sleep interval is -1 then we sleep
     * for a random amount of time between 0 and 30 seconds.
     */
    public static final long DEFAULT_SLEEP_INTERVAL = 30L * 1000L;

    /**
     * Default number of results to fetch.
     *
     */
    public static int      DEFAULT_LIMIT       = -1;

    /**
     * When fetching the API this specifies the default version to return.
     */
    public static String   DEFAULT_VERSION     = "3.0.54";

    /**
     * Default value for useProtobuf.
     *
     */
    public static boolean DEFAULT_USE_PROTOBUF = false;
    
    private int            limit               = DEFAULT_LIMIT;
    private String         lang                = null;
    private String         version             = DEFAULT_VERSION;
    private String         vendor              = null;
    private Date           after               = new Date(); /* use epoch as default */
    private String         firstRequestURL     = null;
    private boolean        skipDescription     = false;
    private String         api                 = null;
    private boolean        useProtobuf         = DEFAULT_USE_PROTOBUF;

    /**
     * 
     * Get the value of <code>useProtobuf</code>.
     *
     */
    public boolean getUseProtobuf() { 
        return this.useProtobuf;
    }

    /**
     * 
     * Set the value of <code>useProtobuf</code>.
     *
     */
    public void setUseProtobuf( boolean useProtobuf ) { 
        this.useProtobuf = useProtobuf;
    }

    /**
     * How long we should sleep if an API call doesn't return enough values.
     */
    private long sleepInterval = DEFAULT_SLEEP_INTERVAL;

    /**
     * 
     * Set the value of <code>firstRequestURL</code>.
     *
     */
    public void setFirstRequestURL( String firstRequestURL ) { 
        this.firstRequestURL = firstRequestURL;
    }

    /**
     * 
     * Get the value of <code>firstRequestURL</code>.
     *
     */
    public String getFirstRequestURL() { 
        return this.firstRequestURL;
    }

    /**
     * 
     * Get the value of <code>after</code>.
     *
     */
    public Date getAfter() { 
        return this.after;
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
     * Set the value of <code>limit</code>.
     *
     * @deprecated This method will be changing to protected in the future.
     * We're going to remove the option for users to set the limit on the number
     * of items per response as we have found this to cause problems in
     * production applications.  Having Spinn3r select the optimal values has
     * turned out to be better for everyone involved and yielded much higher
     * performance.
     */
    public void setLimit( int limit ) {

        // NOTE: there are now LEGIT reasons to use a limit of 1 especially when
        // using the .entry() API
        
        //if ( limit < 10 )
        //    throw new IllegalArgumentException( "Minimum limit is 10." );
        
        this.limit = limit;
    }

    /**
     * 
     * Get the value of <code>limit</code>.
     *
     */
    public int getLimit() { 
        return this.limit;
    }

    public long getSleepInterval() {

        long result = sleepInterval;
        
        if ( result == -1 ) {
            //use a random number generator to compute the
            float f = new Random().nextFloat();
            
            result = (long)(f * 30L);

        }
        
        return result;
    }

    /**
     * 
     * Get the value of <code>skipDescription</code>.
     *
     */
    public boolean getSkipDescription() { 
        return this.skipDescription;
    }

    /**
     * 
     * Set the value of <code>skipDescription</code>.
     *
     */
    public void setSkipDescription( boolean skipDescription ) { 
        this.skipDescription = skipDescription;
    }

    /**
     * 
     * Get the value of <code>api</code>.
     *
     */
    public String getApi() { 
        return this.api;
    }

    /**
     * 
     * Set the value of <code>api</code>.
     *
     */
    public void setApi( String api ) { 
        this.api = api;
    }

}