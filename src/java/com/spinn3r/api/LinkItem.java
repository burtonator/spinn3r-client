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
    
    private String linkText = null;
    
    private String linkXml = null;
    
    private String linkTitle = null;

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
        // <source:indegree>0</source:indegree>
        // <source:ranking>0.0</source:ranking>
        // <source:tier>385</source:tier>
        // <source:link>http://infopreneurship.wordpress.com</source:link>
        // <post:spam_probability>0.0</post:spam_probability>
        // <post:hashcode>66v4-z8pGOg</post:hashcode>
        // <post:title>Larry Lessig Defends Copyright, Loves Charlie Rose Remixes</post:title>
        // <post:link>http://www.techcrunch.com/2008/11/21/larry-lessig-defends-copyright-loves-charlie-rose-remixes/</post:link>
        // <post:lang>U</post:lang>
        // <post:outdegree>0</post:outdegree>
        // <post:timestamp>1227392877248889348</post:timestamp>
        // </item>

        setLinkXml( BaseClient.getElementCDATAByTagName( current, "xml", NS_LINK ) );

    }

}