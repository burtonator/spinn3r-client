package com.spinn3r.api.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

import com.spinn3r.api.protobuf.ProtoStream;
import com.google.protobuf.AbstractMessageLite;


public class ProtoStreamEncoder implements Encoder<AbstractMessageLite> {
    
    private static final String VERSION = "1.0";
    
    private static final ProtoStream.ProtoStreamDelimiter _entryDelimiter;
    private static final ProtoStream.ProtoStreamDelimiter _endDelimiter;

    private final OutputStream _outputStream;
    

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
    
    public ProtoStreamEncoder ( OutputStream outputStream, String entryType, Map<String,String> applationHeadders  ) 
        throws IOException {

        _outputStream = outputStream;
        
        ProtoStream.ProtoStreamHeader.Builder builder =
            ProtoStream.ProtoStreamHeader.newBuilder();

        builder.setVersion( VERSION );
        builder.setDefaultEntryType( entryType );

        if ( applationHeadders != null ) {

            ProtoStream.ApplacationHeader.Builder subBuilder =
                ProtoStream.ApplacationHeader.newBuilder();

            for ( String name : applationHeadders.keySet() ) {
                subBuilder.clear();

                String value = applationHeadders.get( name );

                subBuilder.setName( name );
            
                if ( value != null )
                    subBuilder.setValue( value );
            
                builder.addApplacationHeader( subBuilder.build() );
            }
        } 

        ProtoStream.ProtoStreamHeader headerProto = builder.build();

        headerProto.writeDelimitedTo( _outputStream );
    }
    

    @Override
    public void write ( AbstractMessageLite entry ) 
        throws IOException {    

        _entryDelimiter.writeDelimitedTo( _outputStream );
        entry          .writeDelimitedTo( _outputStream );
    }
    

    @Override
    public void writeAll ( Collection<AbstractMessageLite> entries )
        throws IOException {
        
        for ( AbstractMessageLite entry : entries )
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
