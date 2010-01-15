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

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.spinn3r.api.protobuf.*;

/**
 * <p> This class is <b>NOT</b> threadsafe.  Implementations need to ensure
 * thread safety by using <code>synchronized</code> or
 * <code>java.util.concurrent</code> constructs.
 */
public class CommentClient extends LegacyWrapperClient<CommentItem> {
    
    public static int MAX_LIMIT            = 100;
    public static int OPTIMAL_LIMIT        = 50;
    public static int CONSERVATIVE_LIMIT   = 10;

    protected int getMaxLimit() {
        return MAX_LIMIT;
    }

     protected int getOptimalLimit() {
        return OPTIMAL_LIMIT;
    }

    protected int getConservativeLimit() {
        return CONSERVATIVE_LIMIT;
    }


}