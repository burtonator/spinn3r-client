
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

package com.spinn3r.api;

import com.spinn3r.api.protobuf.ContentApi;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import com.google.protobuf.CodedInputStream;

import com.spinn3r.api.util.Decompress;
import com.spinn3r.api.util.ProtoStreamDecoder;

import com.ice.tar.TarInputStream;
import com.ice.tar.TarEntry;

/**
 * Code to read a protobuff file off disk and print it out.
 * @author Kevin Burton
 * 
 */
public class ProtoStreamContentListing {

    public static void processProtoFile ( InputStream  inputStream ) throws java.io.IOException {

        ContentApi.Entry.Builder builder = ContentApi.Entry.newBuilder();

        ProtoStreamDecoder<ContentApi.Entry> decoder =
            ProtoStreamDecoder.newProtoStreamDecoder( inputStream, builder );

        for ( ContentApi.Entry entry = decoder.read() ; entry != null ; entry = decoder.read() ) {
            String hashcode              = entry.getSource().getHashcode();
            String sourceLink            = entry.getSource().getLink(0).getHref();
            String permalink             = entry.getPermalinkEntry().getCanonicalLink().getHref();
            int    permalinkContentSize  = entry.getPermalinkEntry().getContent().getData().size();
            int    feedContentSize       = entry.getFeedEntry().getContent().getData().size();
                
            System.out.printf( "%s\t%s\t%s\t%s\t%s\n", hashcode, sourceLink, permalink, permalinkContentSize, feedContentSize );
        }
    }


    public static void processTarFile ( InputStream inputStream ) 
        throws java.io.IOException, java.io.FileNotFoundException {

            TarInputStream tarInputStream = new TarInputStream ( inputStream );

            for ( TarEntry tarEntry = tarInputStream.getNextEntry() ; 
                  tarEntry != null                                  ;  
                  tarEntry = tarInputStream.getNextEntry()          ) {
                
                processProtoTarEntry( tarInputStream, tarEntry );
            }

    }


    public static void processProtoTarEntry ( TarInputStream tarInputStream, TarEntry tarEntry ) 
        throws java.io.IOException, java.io.FileNotFoundException {

        if ( tarEntry.isDirectory() )
            for ( TarEntry subTarEntry : tarEntry.getDirectoryEntries() )
                processProtoTarEntry( tarInputStream, subTarEntry );

        else if ( tarEntry.getName().endsWith( ".protobuf" ) )
            processProtoFile( tarInputStream );

        else
            System.err.printf( "Unknown file extentino on file: %s\n", tarEntry.getName() );

    }


    public static void main( String[] args ) throws Exception {

        for ( String path : args ) {
            
            File        file        = new File ( path );
            InputStream inputStream = new FileInputStream( file );

            if ( path.endsWith( ".protostream" ) )
                processProtoFile( inputStream );

            else if ( path.endsWith( ".tar" ) )
                processTarFile( inputStream );

            else
                System.err.printf( "Unknown file extentino on file: %s\n", path );

            inputStream.close();

        }

    }
    
}
