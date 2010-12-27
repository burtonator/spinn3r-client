package com.spinn3r.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Provider;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.Message.Builder;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamDelimiter;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamHeader;

public class ProtoStreamDecoder<T extends AbstractMessageLite> implements Decoder<T> {

    private static final String SUPPORTED_VERSION = "1.0";

    private final ProtoStreamDelimiter.Builder _delimiterBuilder =
        ProtoStreamDelimiter.newBuilder();


    private final InputStream _input;
    private final Provider<? extends Builder> _builderFactory;
    
    private boolean initialized = false;
    
    public static <K extends AbstractMessageLite> ProtoStreamDecoder<K> newProtoStreamDecoder(InputStream input, Provider<? extends Builder> provider) {
        return new ProtoStreamDecoder<K>(input, provider);
    }
    public static <K extends AbstractMessageLite> ProtoStreamDecoder<K> newProtoStreamDecoder(InputStream input, final Builder builder) {
        return newProtoStreamDecoder(input, new Provider<Builder>() {
            public Builder get() {
                return builder.clone().clear();
            }
        });
    }


    protected ProtoStreamDecoder ( InputStream input, Provider<? extends Builder> builderFactory ) {
        _input   = input;
        _builder = builder;
       
    }
    
    
    private void init() throws IOException {
        
        if(initialized)
            return;
        
        String expectedType = _builderFactory.get().getDescriptorForType().getFullName();

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
        _delimiterBuilder.mergeDelimitedFrom( _input );
        
        ProtoStreamDelimiter delimiter = delimiterBuilder.build();

        if ( delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.END )
            res = null;
        else if ( delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.ENTRY ) {
            Builder builder = _builderFactory.get();
            builder.mergeDelimitedFrom( _input );
            
            res = (T)builder.build();
        }
        else {
            String msg = String.format("Expected delimiter type %s\n", delimiter.getDelimiterType() );
            throw new ProtoStreamDecoderExcption ( msg );
        }

        return res;
    }   

	@Override
    public int available() {
        return 0;
    }

	@Override
    public void close() throws IOException {
        _input.close();
    }


    @Override
    public void mark(int readAheadLimit) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public Collection<? extends T> read(int len) throws IOException {
        List<T> retval = new ArrayList<T>(len);
        T obj;
        int ctr = 0;
        
        while(ctr < len && (obj = read()) != null) {
            retval.add(obj);
            ctr++;
        }
        
        return retval;
    }

    @Override
    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) throws IOException {
        long i;
        for(i = 0; i < n; i++) {
            if(read() == null)
                break;
        }
        
        return i;
    }
}
