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
    
    private int tier = -1;
    
    private int indegree = -1;
    
    private Date dateFound = null;
    
    private String guid = null;
    
    private String description = null;
    
    private String link = null;
    
    private String title = null;

    private String resource = null;

    private int resourceStatus = -1;

    public Source() { }

    public Source( Element element ) {

        setTitle( getElementCDATAByTagName( element, "title" ) );
        setLink( getElementCDATAByTagName( element, "link" ) );

        System.out.printf( "DUMP: %s\n", getLink() );
        
        setDescription( getElementCDATAByTagName( element, "description" ) );
        setGuid( getElementCDATAByTagName( element, "guid", NS_WEBLOG ) );
        setDateFound( ISO8601DateParser.parse( getElementCDATAByTagName( element, "date_found", NS_WEBLOG ) ) );
        setIndegree( parseInt( getElementCDATAByTagName( element, "indegree", NS_WEBLOG ) ) );

        // FIXME: guid, resource_status
        
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

}