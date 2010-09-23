/*
 * Copyright 2009 Tailrank, Inc.
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
 *
 * For more information see:
 * 
 * <a href="http://spinn3r.com">http://spinn3r.com</a>
 */

package com.spinn3r.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.spinn3r.api.protobuf.ContentApi;
import com.spinn3r.api.util.CompressedBLOB;
import com.spinn3r.api.util.CompressedBlob2;



public class DiskReader {

    private static int SLEEP_TIME = 1000 * 5; // five seconds

    private final String watchDir;


    public DiskReader ( String watchDir_value ) {
        watchDir = watchDir_value;
    }



    public ContentApi.Response getNext () throws ParseException, FileNotFoundException, IOException  {
        
        ContentApi.Response res = null;

        File dir = new File ( watchDir );

        if ( ! dir.isDirectory() )
            throw new ParseException 
                ( String.format( "Watch directory path is not a directory: %s", watchDir ) );

        
        File proto_file = null;

        for ( String name : dir.list() ) {
            File current = new File ( watchDir, name );

            if ( proto_file == null || current.lastModified() < proto_file.lastModified() )
                proto_file = current;
        }


        if ( proto_file != null ) {
            res = ContentApi.Response.parseFrom( new FileInputStream ( proto_file.getAbsoluteFile() ) );
            if(!proto_file.delete())
            	throw new IOException("Unable to delete " + proto_file.delete());
        }

        return res;
    }



    public static String readContent ( ContentApi.Content content ) throws Exception {
    	
    	// If the content has an encoding field, assume the byte array is encoded using 
    	// the scheme specified in that field. Otherwise, assume that the first two bytes
    	// of the stream is a magic number that specified the encoding scheme
    	if(content.hasEncoding()) 
    	{
    		return new CompressedBlob2(content.getData().toByteArray(), content.getEncoding()).decompress();
    	}
    	else 
    	{
    		CompressedBLOB content_blob = new CompressedBLOB ( content.getData().toByteArray());

    		return content_blob.decompress();
    	}
    }



    public static void main( String[] args ) throws Exception {

        if ( args.length != 1 ) {
            System.out.printf( "Usage: DiskReader watch-dir/\n" );
            System.exit( 1 );
        }

        DiskReader reader = new DiskReader ( args[0] );

        while ( true ) {
            ContentApi.Response result = reader.getNext();

            if ( result == null ) {
                System.out.printf( "nothing files found sleeping...\n" );
                Thread.sleep( SLEEP_TIME );
            }

            else {
                System.out.printf( "" );

                for ( ContentApi.Entry entry : result.getEntryList() ) {

                    String content = readContent( entry.getPermalinkEntry().getContent() );

                    if ( content == null )
                        content = "";

                    System.out.printf( "Found entry for item length %s: %s\n",
                                       content.length(),
                                       entry.getPermalinkEntry().getCanonicalLink().getHref() );
                }
            }


            
        }
        
    }
}
