
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

import java.security.*;

/**
 *
 * @author <a href="mailto:burton@tailrank.com">Kevin Burton</a>
 * @version $Id: MD5.java,v 1.9 2005/06/17 18:04:04 burton Exp $
 */
public class MD5 {

    private static ThreadLocal local = new ThreadLocalMessageDigest( "MD5" );

    public static byte[] encode( final String content ) {

        // WARN: I think I need to call getBytes( "UTF-8" ) so this doesn't
        // change per platform.

        // getBytes(): Encodes this {@code String} into a sequence of bytes
        // using the platform's default charset, storing the result into a new
        // byte array.

        return encode( content.getBytes() );

    }
    
	/**
         *  
	 * @param bytes
	 * @return byte[]
	 */
    public static byte[] encode( final byte[] bytes ) {

        MessageDigest md = (MessageDigest)local.get();
        md.reset();
        return md.digest( bytes );

    }

    public static MessageDigest getMessageDigest() {
        return (MessageDigest)local.get();
    }
        
}

class ThreadLocalMessageDigest extends ThreadLocal {

    private String name = null;
    
    public ThreadLocalMessageDigest( String name ) {
        this.name = name;
    }

    protected Object initialValue() {

        try {
            
            return MessageDigest.getInstance( name );
            
        } catch ( Exception e ) {

            e.printStackTrace();
            return null;

        }

    }
            
}
