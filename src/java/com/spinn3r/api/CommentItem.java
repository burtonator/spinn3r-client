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

import static com.spinn3r.api.XMLUtils.NS_COMMENT;
import static com.spinn3r.api.XMLUtils.getElementCDATAByTagName;

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;

/**
 * Represents a comment item returned from the API.
 */
public class CommentItem extends BaseItem {
    
    private String commentRawPublished = null;
    
    private String commentPermalinkHashcode = null;
    
    private String commentPermalinkTitle = null;
    
    private String commentPermalink = null;


    public CommentItem ( ContentApi.Entry entry ) {
        throw new UnimplementedException ("protobuf support not implmented for this client");
    }


    public CommentItem ( Element current ) throws Exception {
        parseItem( current );
    }


    /**
     * 
     * Get the value of <code>commentPermalink</code>.
     *
     */
    public String getCommentPermalink() { 
        return this.commentPermalink;
    }

    /**
     * 
     * Set the value of <code>commentPermalink</code>.
     *
     */
    public void setCommentPermalink( String commentPermalink ) { 
        this.commentPermalink = commentPermalink;
    }

    /**
     * 
     * Get the value of <code>commentPermalinkTitle</code>.
     *
     */
    public String getCommentPermalinkTitle() { 
        return this.commentPermalinkTitle;
    }

    /**
     * 
     * Set the value of <code>commentPermalinkTitle</code>.
     *
     */
    public void setCommentPermalinkTitle( String commentPermalinkTitle ) { 
        this.commentPermalinkTitle = commentPermalinkTitle;
    }

    /**
     * 
     * Get the value of <code>commentPermalinkHashcode</code>.
     *
     */
    public String getCommentPermalinkHashcode() { 
        return this.commentPermalinkHashcode;
    }

    /**
     * 
     * Set the value of <code>commentPermalinkHashcode</code>.
mornin     *
     */
    public void setCommentPermalinkHashcode( String commentPermalinkHashcode ) { 
        this.commentPermalinkHashcode = commentPermalinkHashcode;
    }

    /**
     * 
     * Get the value of <code>commentRawPublished</code>.
     *
     */
    public String getCommentRawPublished() { 
        return this.commentRawPublished;
    }

    /**
     * 
     * Set the value of <code>commentRawPublished</code>.
     *
     */
    public void setCommentRawPublished( String commentRawPublished ) { 
        this.commentRawPublished = commentRawPublished;
    }

    protected void parseItem ( Element current ) throws Exception {
        
        // <comment:permalink>http://www.techcrunch.com/2009/08/24/apple-will-approve-rhapsodys-iphone-app-but-it-will-still-be-a-dud/</comment:permalink>
        // <comment:permalink_title>Apple Will Approve Rhapsody's iPhone App, But It Will Still Be A Dud</comment:permalink_title>
        // <comment:permalink_hashcode>6OQAltp-n-o</comment:permalink_hashcode>

        setCommentPermalink( getElementCDATAByTagName( current, "permalink", NS_COMMENT ) );
        setCommentPermalinkTitle( getElementCDATAByTagName( current, "permalink_title", NS_COMMENT ) );
        setCommentPermalinkHashcode( getElementCDATAByTagName( current, "permalink_hashcode", NS_COMMENT ) );
        setCommentRawPublished( getElementCDATAByTagName( current, "raw_published", NS_COMMENT ) );

        
    }

}