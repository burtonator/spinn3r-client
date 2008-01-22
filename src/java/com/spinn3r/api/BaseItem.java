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

/**
 * Represents a single item returned from the API.
 */
public abstract class BaseItem {

    private List tags                   = null;

    private String title                = null;
    private String link                 = null;
    private String description          = null;
    private String guid                 = null;
    private String lang                 = null;

    private String source               = null;

    private String weblogTitle          = null;
    private String weblogDescription    = null;
    private String weblogPublisherType  = null;

    private int weblogTier              = -1;
    private int weblogIndegree          = -1;

    private Date pubDate                = null;
    private Date published              = null;
    
    private String authorName           = null;
    private String authorEmail          = null;
    private String authorLink           = null;

    private String contentExtract       = null;
    private String commentExtract       = null;

    /**
     * 
     * Set the value of <code>weblogIndegree</code>.
     *
     */
    public void setWeblogIndegree( int weblogIndegree ) { 
        this.weblogIndegree = weblogIndegree;
    }

    /**
     * 
     * Get the value of <code>weblogIndegree</code>.
     *
     */
    public int getWeblogIndegree() { 
        return this.weblogIndegree;
    }

    /**
     * 
     * Get the value of <code>weblogPublisherType</code>.
     *
     */
    public String getWeblogPublisherType() { 
        return this.weblogPublisherType;
    }

    /**
     * 
     * Set the value of <code>weblogPublisherType</code>.
     *
     */
    public void setWeblogPublisherType( String weblogPublisherType ) { 
        this.weblogPublisherType = weblogPublisherType;
    }

    /**
     * 
     * Get the value of <code>commentExtract</code>.
     *
     */
    public String getCommentExtract() { 
        return this.commentExtract;
    }

    /**
     * 
     * Set the value of <code>commentExtract</code>.
     *
     */
    public void setCommentExtract( String commentExtract ) { 
        this.commentExtract = commentExtract;
    }

    /**
     * 
     * Get the value of <code>contentExtract</code>.
     *
     */
    public String getContentExtract() { 
        return this.contentExtract;
    }

    /**
     * 
     * Set the value of <code>contentExtract</code>.
     *
     */
    public void setContentExtract( String contentExtract ) { 
        this.contentExtract = contentExtract;
    }

    /**
     * 
     * Get the value of <code>authorLink</code>.
     *
     */
    public String getAuthorLink() { 
        return this.authorLink;
    }

    /**
     * 
     * Set the value of <code>authorLink</code>.
     *
     */
    public void setAuthorLink( String authorLink ) { 
        this.authorLink = authorLink;
    }

    /**
     * 
     * Get the value of <code>authorEmail</code>.
     *
     */
    public String getAuthorEmail() { 
        return this.authorEmail;
    }

    /**
     * 
     * Set the value of <code>authorEmail</code>.
     *
     */
    public void setAuthorEmail( String authorEmail ) { 
        this.authorEmail = authorEmail;
    }

    /**
     * 
     * Get the value of <code>authorName</code>.
     *
     */
    public String getAuthorName() { 
        return this.authorName;
    }

    /**
     * 
     * Set the value of <code>authorName</code>.
     *
     */
    public void setAuthorName( String authorName ) { 
        this.authorName = authorName;
    }

    /**
     * 
     * Get the value of <code>pubDate</code>.
     *
     */
    public Date getPubDate() { 
        return this.pubDate;
    }

    /**
     * 
     * Set the value of <code>pubDate</code>.
     *
     */
    public void setPubDate( Date pubDate ) { 
        this.pubDate = pubDate;
    }

    /**
     * 
     * Set the value of <code>tags</code>.
     *
     */
    public void setTags( List tags ) { 
        this.tags = tags;
    }

    /**
     * 
     * Get the value of <code>tags</code>.
     *
     */
    public List getTags() { 
        return this.tags;
    }

    /**
     * 
     * Get the value of <code>weblogTier</code>.
     *
     */
    public int getWeblogTier() { 
        return this.weblogTier;
    }

    /**
     * 
     * Set the value of <code>weblogTier</code>.
     *
     */
    public void setWeblogTier( int weblogTier ) { 
        this.weblogTier = weblogTier;
    }

    /**
     * 
     * Get the value of <code>weblogDescription</code>.
     *
     */
    public String getWeblogDescription() { 
        return this.weblogDescription;
    }

    /**
     * 
     * Set the value of <code>weblogDescription</code>.
     *
     */
    public void setWeblogDescription( String weblogDescription ) { 
        this.weblogDescription = weblogDescription;
    }

    /**
     * 
     * Get the value of <code>weblogTitle</code>.
     *
     */
    public String getWeblogTitle() { 
        return this.weblogTitle;
    }

    /**
     * 
     * Set the value of <code>weblogTitle</code>.
     *
     */
    public void setWeblogTitle( String weblogTitle ) { 
        this.weblogTitle = weblogTitle;
    }

    /**
     * 
     * Get the value of <code>source</code>.
     *
     */
    public String getSource() { 
        return this.source;
    }

    /**
     * 
     * Set the value of <code>source</code>.
     *
     */
    public void setSource( String source ) { 
        this.source = source;
    }

    /**
     * 
     * Get the value of <code>lang</code>.
     *
     */
    public String getLang() { 
        return this.lang;
    }

    /**
     * 
     * Set the value of <code>lang</code>.
     *
     */
    public void setLang( String lang ) { 
        this.lang = lang;
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
     * Set the value of <code>title</code>.
     *
     */
    public void setTitle( String title ) { 
        this.title = title;
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
     * Set the value of <code>description</code>.
     *
     */
    public void setDescription( String description ) { 
        this.description = description;
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
     * Get the value of <code>published</code>.
     *
     */
    public Date getPublished() { 
        return this.published;
    }

    /**
     * 
     * Set the value of <code>published</code>.
     *
     */
    public void setPublished( Date published ) { 
        this.published = published;
    }

    public String toString() {
        return link;
    }

}