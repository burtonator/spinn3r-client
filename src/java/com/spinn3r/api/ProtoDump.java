
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

import java.io.File;
import java.io.FileInputStream;

import com.google.protobuf.CodedInputStream;
import com.spinn3r.api.protobuf.ContentApi;

/**
 * Code to read a protobuff file off disk and print it out.
 * @author Kevin Burton
 * 
 */
public class ProtoDump {

    public static void main( String[] args ) throws Exception {

        String path = args[0];

        File file = new File( path );
        FileInputStream fis = new FileInputStream( file );

        CodedInputStream cis = CodedInputStream.newInstance( fis );
        cis.setSizeLimit( 256 * 1024 * 1024 );
        ContentApi.Response res = ContentApi.Response.parseFrom( cis );

        System.out.printf( "%s\n", res.toString() );

    }
    
}