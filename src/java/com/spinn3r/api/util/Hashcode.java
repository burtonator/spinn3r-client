
/*
 * Copyright 2008 Tailrank, Inc.
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

package com.spinn3r.api.util;

public class Hashcode {

    public static final int HASH_WIDTH = 8;

    /**
     * Compute a hashcode for the given resource URL.  This is truncated MD5
     * with Base64 encoding.
     */
    public static String getHashcode( String resource ) {

        String key = resource;

        return Base64.encode( MD5.encode( key ), HASH_WIDTH );
    }

}