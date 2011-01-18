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

import java.util.Date;
import java.util.List;

import com.spinn3r.util.ISO8601DateParser;

/**
 * Represents a single item returned from the API.
 */
public abstract class BaseItem extends BaseResult {

    protected List<String> tags                   = null;

    protected String title                = null;
    protected String link                 = null;
    protected String description          = null;
    protected String guid                 = null;
    protected String lang                 = null;

    protected String resourceGUID         = null;

    protected String source               = null;

    protected String weblogTitle          = null;
    protected String weblogDescription    = null;
    protected String weblogPublisherType  = null;
    protected String weblogResource       = null;

    protected int weblogTier              = -1;
    protected int weblogIndegree          = -1;

    protected Date published              = null;
	protected Date dateFound              = null;
    
    protected String authorName           = null;
    protected String authorEmail          = null;
    protected String authorLink           = null;

    protected String contentExtract       = null;
    protected String commentExtract       = null;

    protected String feedURL              = null;
    protected String feedResource         = null;

    private String postBody               = null;
    private String postTitle              = null;

    private String postHashcode           = null;
    private String feedHashcode           = null;
    private String sourceHashcode         = null;

    /**
     * 
     * Get the value of <code>feedResource</code>.
     *
     */
    public String getFeedResource() { 
        return this.feedResource;
    }

    /**
     * 
     * Set the value of <code>feedResource</code>.
     *
     */
    public void setFeedResource( String feedResource ) { 
        this.feedResource = feedResource;
    }

    /**
     * 
     * Get the value of <code>postHashcode</code>.
     *
     */
    public String getPostHashcode() { 
        return this.postHashcode;
    }

    /**
     * 
     * Set the value of <code>postHashcode</code>.
     *
     */
    public void setPostHashcode( String postHashcode ) { 
        this.postHashcode = postHashcode;
    }

    /**
     * 
     * Get the value of <code>feedHashcode</code>.
     *
     */
    public String getFeedHashcode() { 
        return this.feedHashcode;
    }

    /**
     * 
     * Set the value of <code>feedHashcode</code>.
     *
     */
    public void setFeedHashcode( String feedHashcode ) { 
        this.feedHashcode = feedHashcode;
    }

    /**
     * 
     * Get the value of <code>sourceHashcode</code>.
     *
     */
    public String getSourceHashcode() { 
        return this.sourceHashcode;
    }

    /**
     * 
     * Set the value of <code>sourceHashcode</code>.
     *
     */
    public void setSourceHashcode( String sourceHashcode ) { 
        this.sourceHashcode = sourceHashcode;
    }

    /**
     * 
     * Set the value of <code>postBody</code>.
     *
     */
    public void setPostBody( String postBody ) { 
        this.postBody = postBody;
    }

    /**
     * 
     * Get the value of <code>postBody</code>.
     *
     */
    public String getPostBody() { 
        return this.postBody;
    }
    
    /**
     * 
     * Set the value of <code>postTitle</code>.
     *
     */
    public void setPostTitle( String postTitle ) { 
        this.postTitle = postTitle;
    }

    /**
     * 
     * Get the value of <code>postTitle</code>.
     *
     */
    public String getPostTitle() { 
        return this.postTitle;
    }

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
     * Get the value of <code>pubDate</code>.
     *
     */
	public Date getDateFound() { 
        return this.dateFound;
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
     * Set Date found field.
     */
	public void setDateFound( Date dateFound ) {
		this.dateFound = dateFound;
	}

    /**
     * 
     * Set the value of <code>tags</code>.
     *
     */
    public void setTags( List<String> tags ) { 
        this.tags = tags;
    }

    /**
     * 
     * Get the value of <code>tags</code>.
     *
     */
    public List<String> getTags() { 
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
     * Get the value of <code>weblogResource</code>.
     *
     */
    public String getWeblogResource() { 
        return this.weblogResource;
    }

    /**
     * 
     * Set the value of <code>weblogResource</code>.
     *
     */
    public void setWeblogResource( String weblogResource ) { 
        this.weblogResource = weblogResource;
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

    /**
     * 
     * Get the value of <code>feedURL</code>.
     *
     */
    public String getFeedURL() { 
        return this.feedURL;
    }

    /**
     * 
     * Set the value of <code>feedURL</code>.
     *
     */
    public void setFeedURL( String feedURL ) { 
        this.feedURL = feedURL;
    }

    /**
     * 
     * Get the value of <code>resourceGUID</code>.
     *
     */
    public String getResourceGUID() { 
        return this.resourceGUID;
    }

    /**
     * 
     * Set the value of <code>resourceGUID</code>.
     *
     */
    public void setResourceGUID( String resourceGUID ) { 
        this.resourceGUID = resourceGUID;
    }

    public String toString() {
        return link;
    }

    public String dump() {
        return dump( false );
    }

    public String dump( boolean fields ) {

        StringBuilder buff = new StringBuilder();
        
        buff.append( String.format(  "title: %s\n", title ) );
        buff.append( String.format(  "link: %s\n", link ) );
        buff.append( String.format(  "guid: %s\n", guid ) );
        buff.append( String.format(  "lang: %s\n", lang ) );

        buff.append( String.format(  "tags: %s\n", tags ) );

        buff.append( String.format(  "resourceGUID: %s\n", resourceGUID ) );

        buff.append( String.format(  "source: %s\n", source ) );

        buff.append( String.format(  "weblogTitle: %s\n", weblogTitle ) );
        buff.append( String.format(  "weblogDescription: %s\n", weblogDescription ) );
        buff.append( String.format(  "weblogPublisherType: %s\n", weblogPublisherType ) );

        buff.append( String.format(  "weblogTier: %s\n", weblogTier ) );
        buff.append( String.format(  "weblogIndegree: %s\n", weblogIndegree ) );

        buff.append( String.format(  "pubDate: %s\n", ISO8601DateParser.toString( pubDate ) ) );
        buff.append( String.format(  "published: %s\n", ISO8601DateParser.toString( published ) ) );
    
        buff.append( String.format(  "authorName: %s\n", authorName ) );
        buff.append( String.format(  "authorEmail: %s\n", authorEmail ) );
        buff.append( String.format(  "authorLink: %s\n", authorLink ) );

        buff.append( String.format(  "feedURL: %s\n", feedURL ) );

        buff.append( String.format(  "postTitle: %s\n", postTitle ) );

        buff.append( String.format(  "postHashcode: %s\n", postHashcode ) );
        buff.append( String.format(  "feedHashcode: %s\n", feedHashcode ) );
        buff.append( String.format(  "sourceHashcode: %s\n", sourceHashcode ) );

        if ( ! fields ) {
            buff.append( String.format(  "description: %s\n", description ) );
            buff.append( String.format(  "postBody: %s\n", postBody ) );
            buff.append( String.format(  "contentExtract: %s\n", contentExtract ) );
            buff.append( String.format(  "commentExtract: %s\n", commentExtract ) );
        }

        return buff.toString();

    }



}
