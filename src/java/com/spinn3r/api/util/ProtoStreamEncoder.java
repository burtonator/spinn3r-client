package com.spinn3r.api.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.protobuf.AbstractMessageLite;


public class ProtoStreamEncoder<T extends AbstractMessageLite> implements Encoder<T> {
    
    private final ProtoStreamBytesEncoder wrappedEncoder;
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder<T> newEncoder(
            OutputStream outputStream, Class<T> klass) throws IOException {
        
        return newEncoder(outputStream, klass, Collections.<String,String>emptyMap());
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder<T> newEncoder(
            OutputStream outputStream, Class<T> klass, Map<String,String> applicationHeaders) throws IOException {
        
        return newStreamEncoder(outputStream, klass, applicationHeaders);
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder<T> newStreamEncoder(
            OutputStream outputStream, Class<T> klass, Map<String,String> applicationHeaders) throws IOException {
        
        return new ProtoStreamEncoder<T>(outputStream, klass, applicationHeaders);
    }

    private ProtoStreamEncoder ( OutputStream outputStream, Class<?> klass, Map<String,String> applicationHeaders  ) 
    {
        wrappedEncoder = ProtoStreamBytesEncoder.newEncoder(outputStream, klass, applicationHeaders);
    }
    

    @Override
    public void write ( T entry ) 
        throws IOException {    

        wrappedEncoder.write(entry.toByteArray());
    }
    

    @Override
    public void writeAll ( Collection<? extends T> entries )
        throws IOException {
        
        for ( T entry : entries )
            write( entry );
    }


    public void flush () 
        throws IOException {        

        wrappedEncoder.flush();
    }

    
    public void end ()
        throws IOException{

        wrappedEncoder.end();
    }
}
