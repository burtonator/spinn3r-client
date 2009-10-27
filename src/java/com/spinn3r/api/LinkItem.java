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
 */
public class LinkItem extends BaseResult {
    
    private int sourceTier = -1;
    
    private float sourceRanking = -0.0f;
    
    private int sourceIndegree = -1;
    
    private long postTimestamp = -1;
    
    private int postOutdegree = -1;
    
    private String postLang = null;
    
    private String postLink = null;
    
    private String postTitle = null;
    
    private String postHashcode = null;
    
    private String sourceLink = null;
    
    private String sourcePublisherType = null;
    
    private String sourceTitle = null;
    
    private String sourceHashcode = null;
    
    private String targetLink = null;
    
    private String targetHashcode = null;

    private String feedHashcode = null;
    
    private String linkText = null;
    
    private String linkXml = null;
    
    private String linkTitle = null;

    /**
     * 
     * Get the value of <code>linkXml</code>.
     *
     */
    public String getLinkXml() { 
        return this.linkXml;
    }

    /**
     * 
     * Set the value of <code>linkXml</code>.
     *
     */
    public void setLinkXml( String linkXml ) { 
        this.linkXml = linkXml;
    }

    /**
     * 
     * Get the value of <code>linkTitle</code>.
     *
     */
    public String getLinkTitle() { 
        return this.linkTitle;
    }

    /**
     * 
     * Set the value of <code>linkTitle</code>.
     *
     */
    public void setLinkTitle( String linkTitle ) { 
        this.linkTitle = linkTitle;
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
     * Get the value of <code>linkText</code>.
     *
     */
    public String getLinkText() { 
        return this.linkText;
    }

    /**
     * 
     * Set the value of <code>linkText</code>.
     *
     */
    public void setLinkText( String linkText ) { 
        this.linkText = linkText;
    }

    /**
     * 
     * Get the value of <code>targetHashcode</code>.
     *
     */
    public String getTargetHashcode() { 
        return this.targetHashcode;
    }

    /**
     * 
     * Set the value of <code>targetHashcode</code>.
     *
     */
    public void setTargetHashcode( String targetHashcode ) { 
        this.targetHashcode = targetHashcode;
    }

    /**
     * 
     * Get the value of <code>targetLink</code>.
     *
     */
    public String getTargetLink() { 
        return this.targetLink;
    }

    /**
     * 
     * Set the value of <code>targetLink</code>.
     *
     */
    public void setTargetLink( String targetLink ) { 
        this.targetLink = targetLink;
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
     * Get the value of <code>sourceTitle</code>.
     *
     */
    public String getSourceTitle() { 
        return this.sourceTitle;
    }

    /**
     * 
     * Set the value of <code>sourceTitle</code>.
     *
     */
    public void setSourceTitle( String sourceTitle ) { 
        this.sourceTitle = sourceTitle;
    }

    /**
     * 
     * Get the value of <code>sourcePublisherType</code>.
     *
     */
    public String getSourcePublisherType() { 
        return this.sourcePublisherType;
    }

    /**
     * 
     * Set the value of <code>sourcePublisherType</code>.
     *
     */
    public void setSourcePublisherType( String sourcePublisherType ) { 
        this.sourcePublisherType = sourcePublisherType;
    }

    /**
     * 
     * Get the value of <code>sourceLink</code>.
     *
     */
    public String getSourceLink() { 
        return this.sourceLink;
    }

    /**
     * 
     * Set the value of <code>sourceLink</code>.
     *
     */
    public void setSourceLink( String sourceLink ) { 
        this.sourceLink = sourceLink;
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
     * Get the value of <code>postTitle</code>.
     *
     */
    public String getPostTitle() { 
        return this.postTitle;
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
     * Get the value of <code>postLink</code>.
     *
     */
    public String getPostLink() { 
        return this.postLink;
    }

    /**
     * 
     * Set the value of <code>postLink</code>.
     *
     */
    public void setPostLink( String postLink ) { 
        this.postLink = postLink;
    }

    /**
     * 
     * Get the value of <code>postLang</code>.
     *
     */
    public String getPostLang() { 
        return this.postLang;
    }

    /**
     * 
     * Set the value of <code>postLang</code>.
     *
     */
    public void setPostLang( String postLang ) { 
        this.postLang = postLang;
    }

    /**
     * 
     * Set the value of <code>postOutdegree</code>.
     *
     */
    public void setPostOutdegree( int postOutdegree ) { 
        this.postOutdegree = postOutdegree;
    }

    /**
     * 
     * Get the value of <code>postOutdegree</code>.
     *
     */
    public int getPostOutdegree() { 
        return this.postOutdegree;
    }

    /**
     * 
     * Get the value of <code>postTimestamp</code>.
     *
     */
    public long getPostTimestamp() { 
        return this.postTimestamp;
    }

    /**
     * 
     * Set the value of <code>postTimestamp</code>.
     *
     */
    public void setPostTimestamp( long postTimestamp ) { 
        this.postTimestamp = postTimestamp;
    }

    /**
     * 
     * Set the value of <code>sourceIndegree</code>.
     *
     */
    public void setSourceIndegree( int sourceIndegree ) { 
        this.sourceIndegree = sourceIndegree;
    }

    /**
     * 
     * Get the value of <code>sourceIndegree</code>.
     *
     */
    public int getSourceIndegree() { 
        return this.sourceIndegree;
    }

    /**
     * 
     * Get the value of <code>sourceRanking</code>.
     *
     */
    public float getSourceRanking() { 
        return this.sourceRanking;
    }

    /**
     * 
     * Set the value of <code>sourceRanking</code>.
     *
     */
    public void setSourceRanking( float sourceRanking ) { 
        this.sourceRanking = sourceRanking;
    }

    /**
     * 
     * Get the value of <code>sourceTier</code>.
     *
     */
    public int getSourceTier() { 
        return this.sourceTier;
    }

    /**
     * 
     * Set the value of <code>sourceTier</code>.
     *
     */
    public void setSourceTier( int sourceTier ) { 
        this.sourceTier = sourceTier;
    }

    public void parse( Element current ) throws Exception {

        // <item>
        // <title>Larry Lessig Defends Copyright, Loves Charlie Rose Remixes</title>
        // <link>http://www.crunchgear.com/</link>
        // <link:date_found>2008-11-22T22:27:57Z</link:date_found>
        // <feed:hashcode>7fx8J0fWwAE</feed:hashcode>
        // <link:title>Gadgets</link:title>
        // <link:xml>&lt;a href="http://www.crunchgear.com/" title="Gadgets"&gt;Gadgets&lt;/a&gt;</link:xml>
        // <link:text>Gadgets</link:text>
        // <target:hashcode>LYy-1XjIOTM</target:hashcode>
        // <target:link>http://www.crunchgear.com/</target:link>
        // <source:hashcode>KCW9eB2fNuU</source:hashcode>
        // <source:title>TechCrunch</source:title>
        // <source:publisher_type>WEBLOG</source:publisher_type>
        // <source:link>http://infopreneurship.wordpress.com</source:link>
        // <post:hashcode>66v4-z8pGOg</post:hashcode>
        // <post:title>Larry Lessig Defends Copyright, Loves Charlie Rose Remixes</post:title>
        // <post:link>http://www.techcrunch.com/2008/11/21/larry-lessig-defends-copyright-loves-charlie-rose-remixes/</post:link>
        // <post:lang>U</post:lang>
        // <post:outdegree>0</post:outdegree>
        // <post:timestamp>1227392877248889348</post:timestamp>
        // <source:indegree>0</source:indegree>
        // <source:ranking>0.0</source:ranking>
        // <source:tier>385</source:tier>
        // </item>

        setLinkXml( getElementCDATAByTagName( current, "xml", NS_LINK ) );
        setLinkTitle( getElementCDATAByTagName( current, "title", NS_LINK ) );
        setLinkText( getElementCDATAByTagName( current, "text", NS_LINK ) );
        setFeedHashcode( getElementCDATAByTagName( current, "hashcode", NS_FEED ) );

        setTargetHashcode( getElementCDATAByTagName( current, "hashcode", NS_TARGET ) );
        setTargetLink( getElementCDATAByTagName( current, "link", NS_TARGET ) );

        setSourceHashcode( getElementCDATAByTagName( current, "hashcode", NS_SOURCE ) );
        setSourceTitle( getElementCDATAByTagName( current, "title", NS_SOURCE ) );
        setSourcePublisherType( getElementCDATAByTagName( current, "publisher_type", NS_SOURCE ) );
        setSourceLink( getElementCDATAByTagName( current, "link", NS_SOURCE ) );

        setPostHashcode( getElementCDATAByTagName( current, "hashcode", NS_POST ) );
        setPostTitle( getElementCDATAByTagName( current, "title", NS_POST ) );
        setPostLink( getElementCDATAByTagName( current, "link", NS_POST ) );
        setPostLang( getElementCDATAByTagName( current, "lang", NS_POST ) );

        setPostOutdegree( parseInt( getElementCDATAByTagName( current, "outdegree", NS_POST ) ) );
        setPostTimestamp( parseLong( getElementCDATAByTagName( current, "timestamp", NS_POST ) ) );

        setSourceIndegree( parseInt( getElementCDATAByTagName( current, "indegree", NS_SOURCE ) ) );
        setSourceRanking( parseFloat( getElementCDATAByTagName( current, "ranking", NS_SOURCE ), -1.0f ) );
        setSourceTier( parseInt( getElementCDATAByTagName( current, "tier", NS_SOURCE ) ) );

        
    }

}