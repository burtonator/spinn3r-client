package com.spinn3r.api.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.inject.internal.ImmutableMap;
import com.google.protobuf.CodedOutputStream;
import com.spinn3r.api.protobuf.ProtoStream;

public class ProtoStreamBytesEncoder implements Encoder<byte[]> {
    
    private final OutputStream outputStream;
    private final String entryType;
    private final Map<String, String> applicationHeaders;
    
    private static final String VERSION = "1.0";
    private static final ProtoStream.ProtoStreamDelimiter _entryDelimiter;
    private static final ProtoStream.ProtoStreamDelimiter _endDelimiter;
    
    private boolean headerWritten = false;
    
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
    
    public static ProtoStreamBytesEncoder newEncoder(OutputStream outputStream, String klass, Map<String, String> applicationHeaders) {
        return new ProtoStreamBytesEncoder(outputStream, klass, applicationHeaders);
    }
    
    public static ProtoStreamBytesEncoder newEncoder(OutputStream outputStream, String klass) {
        return newEncoder(outputStream, klass, Collections.<String,String>emptyMap());
    }
    
    public static ProtoStreamBytesEncoder newEncoder(OutputStream outputStream, Class<?> klass) {
        return newEncoder(outputStream, klass, Collections.<String,String>emptyMap());
    }

    public static ProtoStreamBytesEncoder newEncoder(OutputStream outputStream, Class<?> klass,
            Map<String, String> applicationHeaders) {
        return newEncoder(outputStream, klass.getCanonicalName(), applicationHeaders);
    }
    
    public ProtoStreamBytesEncoder(OutputStream outputStream, String entryType,
            Map<String, String> applicationHeaders)
    {
        this.outputStream = outputStream;
        this.entryType = entryType;
        this.applicationHeaders = ImmutableMap.copyOf(applicationHeaders);
    }
    
    private void writeHeader() throws IOException
    {
        if(headerWritten)
            return;
        
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

        headerProto.writeDelimitedTo( outputStream );
        
        headerWritten = true;
    }

    @Override
    public void end() throws IOException {
        writeHeader();
        _endDelimiter.writeDelimitedTo(outputStream);
        outputStream.flush();
    }

    @Override
    public void flush() throws IOException {
        writeHeader();
        outputStream.flush();

    }

    @Override
    public void write ( byte[] data ) 
        throws IOException {    
        writeHeader();
        _entryDelimiter.writeDelimitedTo( outputStream );
        
        CodedOutputStream codedStream = CodedOutputStream.newInstance(outputStream);
        codedStream.writeRawVarint32(data.length);
        codedStream.flush();
        
        outputStream.write(data);
    }
    

    @Override
    public void writeAll(Collection<? extends byte[]> arg0) throws IOException {
        for(byte[] b : arg0)
            write(b);

    }

}
