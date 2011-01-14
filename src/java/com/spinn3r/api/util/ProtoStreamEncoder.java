package com.spinn3r.api.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import com.google.protobuf.AbstractMessageLite;
import com.spinn3r.api.protobuf.ProtoStream;


public class ProtoStreamEncoder<T extends AbstractMessageLite> implements Encoder<T> {
    
    private static final String VERSION = "1.0";
    
    private static final ProtoStream.ProtoStreamDelimiter _entryDelimiter;
    private static final ProtoStream.ProtoStreamDelimiter _endDelimiter;

    private final OutputStream _outputStream;
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder<T> newStreamEncoder(
            OutputStream outputStream, Class<T> klass, Map<String,String> applicationHeaders) throws IOException {
        
        return new ProtoStreamEncoder<T>(outputStream, klass.getCanonicalName(), applicationHeaders);
    }

    public static <T extends AbstractMessageLite> ProtoStreamEncoder<T> newStreamEncoder(
            OutputStream outputStream, String entry, Map<String,String> applicationHeaders) throws IOException {
        
        return new ProtoStreamEncoder<T>(outputStream, entry, applicationHeaders);
    }
    static {

        ProtoStream.ProtoStreamDelimiter.Builder builder =
            ProtoStream.ProtoStreamDelimiter.newBuilder();
        
        builder.setDelimiterType( ProtoStream.ProtoStreamDelimiter.DelimiterType.ENTRY );

        _entryDelimiter = builder.build();

        builder =
            ProtoStream.ProtoStreamDelimiter.newBuilder();
        

        builder.setDelimiterType( ProtoStream.ProtoStreamDelimiter.DelimiterType.END );

        _endDelimiter = builder.build();
    }
    
    private ProtoStreamEncoder ( OutputStream outputStream, String entryType, Map<String,String> applicationHeaders  ) 
        throws IOException {

        _outputStream = outputStream;
        
        ProtoStream.ProtoStreamHeader.Builder builder =
            ProtoStream.ProtoStreamHeader.newBuilder();

        builder.setVersion( VERSION );
        builder.setDefaultEntryType( entryType );

        if ( applicationHeaders != null ) {

            ProtoStream.ApplicationHeader.Builder subBuilder =
                ProtoStream.ApplicationHeader.newBuilder();

            for ( String name : applicationHeaders.keySet() ) {
                subBuilder.clear();

                String value = applicationHeaders.get( name );

                subBuilder.setName( name );
            
                if ( value != null )
                    subBuilder.setValue( value );
            
                builder.addApplicationHeader( subBuilder.build() );
            }
        } 

        ProtoStream.ProtoStreamHeader headerProto = builder.build();

        headerProto.writeDelimitedTo( _outputStream );
    }
    

    @Override
    public void write ( T entry ) 
        throws IOException {    

        _entryDelimiter.writeDelimitedTo( _outputStream );
        entry          .writeDelimitedTo( _outputStream );
    }
    

    @Override
    public void writeAll ( Collection<? extends T> entries )
        throws IOException {
        
        for ( T entry : entries )
            write( entry );
    }


    public void flush () 
        throws IOException {        

        _outputStream.flush();
    }

    
    public void end ()
        throws IOException{

        _endDelimiter.writeDelimitedTo( _outputStream );
    }

    public void closeStream () 
        throws IOException {
        _outputStream.close();
    }
}
