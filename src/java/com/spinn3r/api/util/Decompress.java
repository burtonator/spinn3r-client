/*
 * Copyright 2009 Tailrank, Inc (Spinn3r).
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import com.spinn3r.api.protobuf.ContentApi;

/**
 * Facade which allows us to compress and decompress text easily and efficiently
 * from a central location with the correctly library and encodign support.
 * 
 * @author Kevin Burton
 * @version $Id: SHA1.java,v 1.9 2005/06/17 18:04:04 burton Exp $
 */
public class Decompress {

	private static int    BUFFER_SIZE         = 4096;
	private static int    DECOMPRESSION_RATIO = 5;
	private static String ENCODING            = "UTF-8";

	/**
     * Given the binary data, determine the external form, correctly parse it
     * and return the value as a String.
     */
    public static String getContentAsString ( ContentApi.Content content ) throws IOException {


        byte[] b = content.getData().toByteArray();

        // we don't have ANYTHING to decompress so don't do any work becuase we
        // would just throw an exception.
        if ( b == null || b.length == 0 )
            return null;
        
        byte data[];


            
        //now tmp should be filled with the correct data without the FORMAT
        //identifier.
        ByteArrayInputStream bis =  new ByteArrayInputStream( b );

        //now use an input stream that can decompress the data.
        InputStream cis = getCompressedInputStream( bis );
        
        //create a buffer to store the data. 
        ByteArrayOutputStream bos =
            new ByteArrayOutputStream( b.length * DECOMPRESSION_RATIO );
        
        byte[] buff = new byte[ BUFFER_SIZE ];
        int count;
        
        while( (count = cis.read( buff ) ) > 0 ) {
            
            //now write it to our temp byte array buffer
            bos.write( buff, 0, count );
            
        }
        
        cis.close();
        bos.close();
        
        data = bos.toByteArray();


        return new String( data, ENCODING );
        
    }

    /**
	 * Get the correct stream from the correctly underlying compression library.
	 */
    private static InputStream getCompressedInputStream( InputStream is ) throws IOException {

		return new InflaterInputStream( is );

   }
}
