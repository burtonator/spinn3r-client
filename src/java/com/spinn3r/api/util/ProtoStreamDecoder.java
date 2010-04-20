package com.spinn3r.api.util;

import java.io.IOException;
import java.io.InputStream;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.Message.Builder;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamDelimiter;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamHeader;

public class ProtoStreamDecoder<T extends AbstractMessageLite> implements Decoder<T> {

    private static final String SUPPORTED_VERSION = "1.0";

    private final ProtoStreamDelimiter.Builder _delimiterBuilder =
        ProtoStreamDelimiter.newBuilder();


    private final InputStream _input;
    private final Builder     _builder;
    
    private boolean initialized = false;


    public ProtoStreamDecoder ( InputStream input, Builder builder ) {

        _input   = input;
        _builder = builder;
       
    }
    
    private void init() throws IOException {
        
        if(initialized)
            return;
        
        String expectedType = _builder.getDescriptorForType().getFullName();

        ProtoStreamHeader.Builder headerBuilder =
            ProtoStreamHeader.newBuilder();

       headerBuilder.mergeDelimitedFrom( _input );

       ProtoStreamHeader header = headerBuilder.build();

       String version = header.getVersion();

       if ( ! version.equals( SUPPORTED_VERSION ) ) {
           String msg = String.format("Version mismatch expected '%s' got '%s'\n", SUPPORTED_VERSION, version );
           throw new ProtoStreamDecoderExcption ( msg );
       }

       String type = header.getDefaultEntryType();
       
       if ( ! type.equals( expectedType ) ) {
           String msg = String.format("Type mismatch expected '%s' got '%s'\n", expectedType, type );
           throw new ProtoStreamDecoderExcption ( msg );
       }
       
       initialized = true;
    }

    @SuppressWarnings("unchecked")
    public T read ( )
        throws IOException {
        
        init();

        T res = null;

        ProtoStreamDelimiter.Builder delimiterBuilder = _delimiterBuilder.clone();

        delimiterBuilder.mergeDelimitedFrom( _input );
        
        ProtoStreamDelimiter delimiter = delimiterBuilder.build();

        if ( delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.END )
            res = null;

        else if ( delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.ENTRY ) {
            Builder builder = _builder.clone().clear();

            builder.mergeDelimitedFrom( _input );

            
            res = (T)builder.build();
        }

        else {
            String msg = String.format("Expected delimiter type %s\n", delimiter.getDelimiterType() );
            throw new ProtoStreamDecoderExcption ( msg );
        }

        return res;
    }

}
