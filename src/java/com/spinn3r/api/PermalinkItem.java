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


import static com.spinn3r.api.XMLUtils.NS_ATOM;
import static com.spinn3r.api.XMLUtils.NS_DC;
import static com.spinn3r.api.XMLUtils.NS_FEED;
import static com.spinn3r.api.XMLUtils.NS_POST;
import static com.spinn3r.api.XMLUtils.NS_SOURCE;
import static com.spinn3r.api.XMLUtils.NS_WEBLOG;
import static com.spinn3r.api.XMLUtils.empty;
import static com.spinn3r.api.XMLUtils.getElementByTagName;
import static com.spinn3r.api.XMLUtils.getElementCDATAByTagName;
import static com.spinn3r.api.XMLUtils.parseTags;

import java.io.IOException;

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;
import com.spinn3r.io.utils.CompressedBLOB;
import com.spinn3r.io.utils.CompressedBlob2;
import com.spinn3r.io.utils.EncodingException;
import com.spinn3r.util.ISO8601DateParser;

/**
 * Represents a single item returned from the API.
 */
public class PermalinkItem extends BaseItem {


    public PermalinkItem ( ContentApi.Entry entry ) throws ParseException {
        parseItem( entry );
    }


    public PermalinkItem ( Element current ) throws ParseException {
        parseItem( current );
    }


    protected void parseItem ( Element current ) throws ParseException {
                

        try {
            //base elements.
            setTitle( getElementCDATAByTagName( current, "title" ) );

            setDescription( getElementCDATAByTagName( current, "description" ) );

            setLink( getElementCDATAByTagName( current, "link" )  );
            setGuid( getElementCDATAByTagName( current, "guid" )  );

            // dc:lang
            setLang( getElementCDATAByTagName( current, "lang", NS_DC ) );

            // dc:source
            setSource( getElementCDATAByTagName( current, "source", NS_DC ) );
        
            // weblog:title
            // weblog:description
            setWeblogTitle( getElementCDATAByTagName( current, "title", NS_WEBLOG ) );

            setWeblogResource( getElementCDATAByTagName( current, "resource", NS_SOURCE ) );

            setWeblogDescription( getElementCDATAByTagName( current, "description", NS_WEBLOG ) );

            String str_tier = getElementCDATAByTagName( current, "tier", NS_WEBLOG );
        
            if ( str_tier != null )
                setWeblogTier( Integer.parseInt( str_tier ) );

            String pubDate = getElementCDATAByTagName( current, "pubDate" );
            setPubDate( RFC822DateParser.parse( pubDate ) );

            String atom_published = getElementCDATAByTagName( current, "published", NS_ATOM );
            if ( atom_published != null && ! atom_published.equals( "" ) )
                setPublished( ISO8601DateParser.parse( atom_published ) );

            //FIXME: this stuff should NOT use the weblog namespace anymore as it is
            //deprecated.
        
            //FIXME: weblog:iranking

            String weblog_indegree = getElementCDATAByTagName( current, "indegree", NS_WEBLOG );
            if ( weblog_indegree != null )
                setWeblogIndegree( Integer.parseInt( weblog_indegree ) );
        
            String publisher_type = getElementCDATAByTagName( current, "publisher_type", NS_WEBLOG );
            if ( publisher_type != null )
                publisher_type = publisher_type.trim();
        
            setWeblogPublisherType( publisher_type );

            setTags( parseTags( current ) );

            Element author = getElementByTagName( current, "author", NS_ATOM );
        
            if ( author != null ) {

                setAuthorName(  getElementCDATAByTagName( author, "name",  NS_ATOM ) );
                setAuthorEmail( getElementCDATAByTagName( author, "email", NS_ATOM ) );
                setAuthorLink(  getElementCDATAByTagName( author, "link",  NS_ATOM ) );

            }

            // Spinn3r 2.1 post content.

            setContentExtract( getElementCDATAByTagName( current,
                                                         "content_extract",
                                                         NS_POST ) );

            setCommentExtract( getElementCDATAByTagName( current,
                                                         "comment_extract",
                                                         NS_POST ) );

            setFeedURL( getElementCDATAByTagName( current, "url", NS_FEED ) );
            setFeedResource( getElementCDATAByTagName( current, "resource", NS_FEED ) );

            setResourceGUID( getElementCDATAByTagName( current, "resource_guid", NS_POST ) );

            //spinn3r 3.x values

            //FIXME: post:timestamp, feed:link
        
            setPostTitle( getElementCDATAByTagName( current, "title", NS_POST ) );
            setPostBody( getElementCDATAByTagName( current, "body", NS_POST ) );

            setPostHashcode( getElementCDATAByTagName( current,   "hashcode", NS_POST ) );
            setSourceHashcode( getElementCDATAByTagName( current, "hashcode", NS_SOURCE ) );
            setFeedHashcode( getElementCDATAByTagName( current,   "hashcode", NS_FEED ) );
        }

        catch ( java.text.ParseException e ) {
            throw new ParseException ( e );
        }

    }


    protected void parseItem( ContentApi.Entry entry ) throws ParseException {
        
        try {
            //System.out.printf( "FIXME: entry is:\n%s\n", entry.toString() );

            if ( ! entry.hasSource() )
                throw new MissingRequiredFieldException ( "missing source" );

            ContentApi.Source source = entry.getSource();

            if ( ! entry.hasFeed() )
                throw new MissingRequiredFieldException ( "missing feed" );

            ContentApi.Feed feed = entry.getFeed();

            if ( ! entry.hasPermalinkEntry() )
                throw new MissingRequiredFieldException ( "missing PermalinkEntry" );

            ContentApi.PermalinkEntry permalink_entry = entry.getPermalinkEntry();

            if ( ! entry.hasFeedEntry() )
                throw new MissingRequiredFieldException ( "missing feedEntry" );

            ContentApi.FeedEntry feed_entry  = entry.getFeedEntry();


            //FIXME FIXME FIXME
            //FIXME: when using the feed API we need to use feed_entry for this data.

            //base elements.
            if ( ! empty( permalink_entry.getTitle() ) )
                setTitle( permalink_entry.getTitle() );

            String content; 
            if(permalink_entry.getContent().hasEncoding())
            {
            	CompressedBlob2 content_blob =
            		new CompressedBlob2 ( permalink_entry.getContent().getData().toByteArray(),
            				permalink_entry.getContent().getEncoding());
            	content = content_blob.decompress();
            }
            else
            {
            	CompressedBLOB content_blob =
            		new CompressedBLOB ( permalink_entry.getContent().getData().toByteArray());
            	content = content_blob.decompress();
            }

        
            if ( ! empty(  content ) )
                setDescription( content );
        
            setLink( permalink_entry.getCanonicalLink().getHref() );
            setGuid( permalink_entry.getCanonicalLink().getResource() );

            // dc:lang

            if ( empty( source.getLang(0).getCode() ) ) {
                setLang( "U" );
            } else {
                setLang( source.getLang(0).getCode() );
            }

            // dc:source
            setSource( source.getLink(0).getHref() );
        
            // weblog:title
            // weblog:description

            if ( ! empty( source.getTitle() ) )
                setWeblogTitle( source.getTitle() );

            if ( ! empty( source.getDescription() ) )
                setWeblogDescription( source.getDescription() );

            setWeblogTier( source.getTier() );

            if( permalink_entry.getLastPublished() != null && permalink_entry.getLastPublished().length() != 0)
                setPubDate( ISO8601DateParser.parse( permalink_entry.getLastPublished() ) );
            else
                setPubDate( ISO8601DateParser.parse( permalink_entry.getDateFound() ) );

            //FIXME: this is wrog.
            String last_published = permalink_entry.getLastPublished();
            if ( ! empty( last_published ) )
                setPublished( ISO8601DateParser.parse( last_published ) );

            if ( source.hasIndegree() )
                setWeblogIndegree( source.getIndegree() );        
        
            setWeblogPublisherType( source.getPublisherType().trim() );

            setTags( feed_entry.getCategoryList() );

            //TODO: support more than one author in a future version.
            if ( permalink_entry.getAuthorCount() > 0 ) {
            
                ContentApi.Author author = permalink_entry.getAuthor( 0 );

                if ( ! empty( author.getName() ) )
                    setAuthorName( author.getName() );
            
                if ( ! empty( author.getEmail() ) )
                    setAuthorEmail( author.getEmail() );

                if ( ! empty( author.getLink(0).getHref() ) )
                    setAuthorLink ( author.getLink(0).getHref() );

            }

            // Spinn3r 2.1 post content.

            String content_extract; 
            ContentApi.Content extract = permalink_entry.getContentExtract();
            if(extract.hasEncoding())
            {
            	CompressedBlob2 content_blob =
            		new CompressedBlob2 (extract.getData().toByteArray(),
            				extract.getEncoding());
            	content_extract = content_blob.decompress();
            }
            else
            {
            	CompressedBLOB content_blob =
            		new CompressedBLOB ( extract.getData().toByteArray());
            	content_extract = content_blob.decompress();
            }

            if ( ! empty( content_extract ) )
                setContentExtract( content_extract );

            if ( ! empty( feed.getCanonicalLink().getHref() ) )
                setFeedURL( feed.getCanonicalLink().getHref() );

            if ( ! empty( feed.getCanonicalLink().getResource() ) )
                setFeedResource( feed.getCanonicalLink().getResource() );

            setResourceGUID( permalink_entry.getHashcode() );

            //spinn3r 3.x values

            //FIXME: post:timestamp, feed:link

            if ( ! empty( feed_entry.getTitle() ) )
                setPostTitle( feed_entry.getTitle() );
            
            
            String feed_entry_content = ""; 
            ContentApi.Content feed_content = feed_entry.getContent();
            if(feed_content.hasEncoding())
            {
            	CompressedBlob2 content_blob =
            		new CompressedBlob2 (feed_content.getData().toByteArray(),
            				feed_content.getEncoding());
            	content_extract = content_blob.decompress();
            }
            else
            {
            	CompressedBLOB content_blob =
            		new CompressedBLOB ( feed_content.getData().toByteArray());
            	content_extract = content_blob.decompress();
            }
        
            if ( ! empty( feed_entry_content ) ) {
                setPostBody ( feed_entry_content );
            }

            setPostHashcode  ( feed_entry.getHashcode() );
            setSourceHashcode( source.getHashcode()     );

            if ( ! empty( feed.getHashcode() ) )
                setFeedHashcode ( feed.getHashcode() );
        }

        catch ( EncodingException e ) {
            throw new ParseException ( e );
        }
        catch( IOException e) {
        	throw new ParseException ( e );
        }
    }



}