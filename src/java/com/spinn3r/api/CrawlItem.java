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

import java.io.IOException;

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;
import com.spinn3r.api.util.CompressedBLOB;

import static com.spinn3r.api.XMLUtils.*;

/**
 * Represents a single item returned from the API.
 */
public class CrawlItem extends BaseItem {

    public CrawlItem ( ContentApi.Entry entry ) throws ParseException {
        parseItem( entry );
    }

    public CrawlItem ( Element current ) throws ParseException {
        parseItem( current );
    }

    protected void parseItem ( Element current ) throws ParseException {

    }

    protected void parseItem( ContentApi.Entry entry ) throws ParseException {
    }

}