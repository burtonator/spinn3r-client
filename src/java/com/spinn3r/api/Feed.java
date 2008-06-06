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

import org.w3c.dom.*;

import static com.spinn3r.api.BaseClient.*;

/**
 * Represents a source within Spinn3r.
 */
public class Feed extends BaseItem {
    
    int resourceStatus = -1;
    
    String link = null;
    
    String channelDescription = null;
    
    String channelTitle = null;
    
    String channelLink = null;
    
    String etag = null;
    
    Date lastPublished = null;
    
    Date dateFound = null;
    
    String resource = null;
    
    String guid = null;

    /**
     * 
     * Get the value of <code>resourceStatus</code>.
     *
     */
    public int getResourceStatus() { 
        return this.resourceStatus;
    }

    /**
     * 
     * Set the value of <code>resourceStatus</code>.
     *
     */
    public void setResourceStatus( int resourceStatus ) { 
        this.resourceStatus = resourceStatus;
    }

    /**
     * 
     * Get the value of <code>link</code>.
     *
     */
    public String getLink() { 
        return this.link;
    }

    /**
     * 
     * Set the value of <code>link</code>.
     *
     */
    public void setLink( String link ) { 
        this.link = link;
    }

    /**
     * 
     * Get the value of <code>channelDescription</code>.
     *
     */
    public String getChannelDescription() { 
        return this.channelDescription;
    }

    /**
     * 
     * Set the value of <code>channelDescription</code>.
     *
     */
    public void setChannelDescription( String channelDescription ) { 
        this.channelDescription = channelDescription;
    }

    /**
     * 
     * Get the value of <code>channelTitle</code>.
     *
     */
    public String getChannelTitle() { 
        return this.channelTitle;
    }

    /**
     * 
     * Set the value of <code>channelTitle</code>.
     *
     */
    public void setChannelTitle( String channelTitle ) { 
        this.channelTitle = channelTitle;
    }

    /**
     * 
     * Get the value of <code>channelLink</code>.
     *
     */
    public String getChannelLink() { 
        return this.channelLink;
    }

    /**
     * 
     * Set the value of <code>channelLink</code>.
     *
     */
    public void setChannelLink( String channelLink ) { 
        this.channelLink = channelLink;
    }

    /**
     * 
     * Get the value of <code>etag</code>.
     *
     */
    public String getEtag() { 
        return this.etag;
    }

    /**
     * 
     * Set the value of <code>etag</code>.
     *
     */
    public void setEtag( String etag ) { 
        this.etag = etag;
    }

    /**
     * 
     * Get the value of <code>lastPublished</code>.
     *
     */
    public Date getLastPublished() { 
        return this.lastPublished;
    }

    /**
     * 
     * Set the value of <code>lastPublished</code>.
     *
     */
    public void setLastPublished( Date lastPublished ) { 
        this.lastPublished = lastPublished;
    }

    /**
     * 
     * Get the value of <code>dateFound</code>.
     *
     */
    public Date getDateFound() { 
        return this.dateFound;
    }

    /**
     * 
     * Set the value of <code>dateFound</code>.
     *
     */
    public void setDateFound( Date dateFound ) { 
        this.dateFound = dateFound;
    }

    /**
     * 
     * Get the value of <code>resource</code>.
     *
     */
    public String getResource() { 
        return this.resource;
    }

    /**
     * 
     * Set the value of <code>resource</code>.
     *
     */
    public void setResource( String resource ) { 
        this.resource = resource;
    }

    /**
     * 
     * Get the value of <code>guid</code>.
     *
     */
    public String getGuid() { 
        return this.guid;
    }

    /**
     * 
     * Set the value of <code>guid</code>.
     *
     */
    public void setGuid( String guid ) { 
        this.guid = guid;
    }

}