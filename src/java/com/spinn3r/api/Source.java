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
public class Source extends BaseItem {
    
    private float spamProbability = -0.0f;

    private boolean disabled = false;
    
    private int tier = -1;
    
    private int indegree = -1;
    
    private Date dateFound = null;
    
    private String guid = null;
    
    private String description = null;
    
    private String link = null;
    
    private String title = null;

    private String resource = null;

    private int resourceStatus = -1;

    private Feed feed = new Feed();

    public Source() { }

    public Source( Element e ) {

        title                     = getElementCDATAByTagName( e, "title" );
        link                      = getElementCDATAByTagName( e, "link" );
        description               = getElementCDATAByTagName( e, "description" );

        guid                      = getElementCDATAByTagName( e, "guid",                                      NS_SOURCE );
        resource                  = getElementCDATAByTagName( e, "resource",                                  NS_SOURCE );

        indegree                  = parseInt( getElementCDATAByTagName( e, "indegree",                        NS_SOURCE ) );
        tier                      = parseInt( getElementCDATAByTagName( e, "tier",                            NS_SOURCE ) );
        resourceStatus            = parseInt( getElementCDATAByTagName( e, "resource_status",                 NS_SOURCE ) );

        dateFound                 = ISO8601DateParser.parse( getElementCDATAByTagName( e, "date_found",       NS_SOURCE ) );

        disabled                  = "true".equals( getElementCDATAByTagName( e, "disabled",                   NS_SOURCE ) );

        spamProbability           = parseFloat( getElementCDATAByTagName( e, "spam_probability",              NS_SOURCE ) ,
                                                -0.0f );

        feed.guid                 = getElementCDATAByTagName( e, "guid",                                      NS_FEED );
        feed.resource             = getElementCDATAByTagName( e, "resource",                                  NS_FEED );
        feed.link                 = getElementCDATAByTagName( e, "link",                                      NS_FEED );
        feed.channelLink          = getElementCDATAByTagName( e, "channel_link",                              NS_FEED );
        feed.channelTitle         = getElementCDATAByTagName( e, "channel_title",                             NS_FEED );
        feed.channelDescription   = getElementCDATAByTagName( e, "channel_desc",                              NS_FEED );
        feed.etag                 = getElementCDATAByTagName( e, "etag",                                      NS_FEED );

        feed.resourceStatus       = parseInt( getElementCDATAByTagName( e, "resource_status",                 NS_FEED ) );

        feed.dateFound            = ISO8601DateParser.parse( getElementCDATAByTagName( e, "date_found",       NS_FEED ) );
        feed.lastPublished        = ISO8601DateParser.parse( getElementCDATAByTagName( e, "last_published",   NS_FEED ) );

    }
    
    /**
     * 
     * Set the value of <code>title</code>.
     *
     */
    public void setTitle( String title ) { 
        this.title = title;
    }

    /**
     * 
     * Get the value of <code>description</code>.
     *
     */
    public String getDescription() { 
        return this.description;
    }

    /**
     * 
     * Set the value of <code>description</code>.
     *
     */
    public void setDescription( String description ) { 
        this.description = description;
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
     * Get the value of <code>title</code>.
     *
     */
    public String getTitle() { 
        return this.title;
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
     * Get the value of <code>indegree</code>.
     *
     */
    public int getIndegree() { 
        return this.indegree;
    }

    /**
     * 
     * Set the value of <code>indegree</code>.
     *
     */
    public void setIndegree( int indegree ) { 
        this.indegree = indegree;
    }

    /**
     * 
     * Get the value of <code>tier</code>.
     *
     */
    public int getTier() { 
        return this.tier;
    }

    /**
     * 
     * Set the value of <code>tier</code>.
     *
     */
    public void setTier( int tier ) { 
        this.tier = tier;
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
     * Get the value of <code>disabled</code>.
     *
     */
    public boolean getDisabled() { 
        return this.disabled;
    }

    /**
     * 
     * Set the value of <code>disabled</code>.
     *
     */
    public void setDisabled( boolean disabled ) { 
        this.disabled = disabled;
    }

    /**
     * 
     * Get the value of <code>spamProbability</code>.
     *
     */
    public float getSpamProbability() { 
        return this.spamProbability;
    }

    /**
     * 
     * Set the value of <code>spamProbability</code>.
     *
     */
    public void setSpamProbability( float spamProbability ) { 
        this.spamProbability = spamProbability;
    }

    /**
     * Get the feed for this source.
     */
    public Feed getFeed() {
        return feed;
    }
    
}