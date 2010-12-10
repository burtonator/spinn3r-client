package com.spinn3r.api.util;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.AbstractMessageLite;
import com.spinn3r.api.protobuf.ProtoStream;
import com.spinn3r.api.protobuf.ProtoStream.ApplicationHeader;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamChecksum;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamDelimiter;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamHeader;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamDelimiter.Builder;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamDelimiter.DelimiterType;


public class ProtoStreamEncoder2<T extends AbstractMessageLite> implements Encoder<T> {
	
	public static class Factory<T extends AbstractMessageLite> {
		private Map<String, String> applicationHeader = new HashMap<String, String>();
		private String defaultType;
		private OutputStream outputStream;
		
		public void addHeader(String key, String value) {
			applicationHeader.put(key, value);
		}
		
		public void addHeaders(Map<String, String> headers) {
			applicationHeader.putAll(headers);
		}
		
		public void setDefaultType(String type) {
			this.defaultType = type;
		}
		
		public void setOutputStream(OutputStream outputStream) {
			this.outputStream = outputStream;
		}
		
		public ProtoStreamEncoder2<T> build() throws IOException {
			return newEncoder(outputStream, defaultType, applicationHeader);
		}
	}
    
    private static final String VERSION = "2.0";
    private static final String CHECKSUM_ALGORITHM = "MD5";
    
    private static final ProtoStream.ProtoStreamDelimiter _entryDelimiter;
    private static final ProtoStream.ProtoStreamDelimiter _endDelimiter;
    private static final ProtoStream.ProtoStreamDelimiter _checksumDelimiter;

    private final DigestOutputStream _outputStream;
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newEncoder(
            OutputStream outputStream, String entrytype) throws IOException {
    	return newStreamEncoder(outputStream, entrytype);
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newEncoder(
            OutputStream outputStream, String entrytype, Map<String,String> applicationHeaders) throws IOException {
        
        return newStreamEncoder(outputStream, entrytype, applicationHeaders);
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newEncoder(
            OutputStream outputStream, Class<T> klass) throws IOException {
    	return newStreamEncoder(outputStream, klass);
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newEncoder(
            OutputStream outputStream, Class<T> klass, Map<String,String> applicationHeaders) throws IOException {
        
        return newStreamEncoder(outputStream, klass.getCanonicalName(), applicationHeaders);
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newStreamEncoder(
            OutputStream outputStream, String entrytype) throws IOException {
    	return newStreamEncoder(outputStream, entrytype, Collections.<String,String>emptyMap());
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newStreamEncoder(
            OutputStream outputStream, String entrytype, Map<String,String> applicationHeaders) throws IOException {
        
        return new ProtoStreamEncoder2<T>(outputStream, entrytype, applicationHeaders);
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newStreamEncoder(
            OutputStream outputStream, Class<T> klass) throws IOException {
    	return newStreamEncoder(outputStream, klass, Collections.<String,String>emptyMap());
    }
    
    public static <T extends AbstractMessageLite> ProtoStreamEncoder2<T> newStreamEncoder(
            OutputStream outputStream, Class<T> klass, Map<String,String> applicationHeaders) throws IOException {
        
        return new ProtoStreamEncoder2<T>(outputStream, klass.getCanonicalName(), applicationHeaders);
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
        
        builder = 
        	ProtoStream.ProtoStreamDelimiter.newBuilder();
        builder.setDelimiterType(ProtoStream.ProtoStreamDelimiter.DelimiterType.CHECKSUM);
        
        _checksumDelimiter = builder.build();
    }
    
    private ProtoStreamEncoder2 ( OutputStream outputStream, String entryType, Map<String,String> applicationHeaders  ) 
        throws IOException {

        try {
			_outputStream = new DigestOutputStream(outputStream, MessageDigest.getInstance(CHECKSUM_ALGORITHM));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
        
        ProtoStream.ProtoStreamHeader.Builder builder =
            ProtoStream.ProtoStreamHeader.newBuilder();

        builder.setVersion( VERSION );
        builder.setDefaultEntryType( entryType );

        if ( applicationHeaders != null ) {

            ProtoStream.ApplicationHeader.Builder subBuilder =
                ProtoStream.ApplicationHeader.newBuilder();

            for ( Map.Entry<String, String> entry : applicationHeaders.entrySet() ) {
                subBuilder.clear();
                subBuilder.setName( entry.getKey() );
            
                if ( entry.getValue() != null )
                    subBuilder.setValue( entry.getValue() );
            
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
    
    private void writeChecksum() throws IOException {
    	_checksumDelimiter.writeDelimitedTo(_outputStream);
    	MessageDigest digest = _outputStream.getMessageDigest();
    	byte[] digestBytes = digest.digest();
    	digest.reset();
    	
    	ProtoStream.ProtoStreamChecksum.Builder checksumMsgBuilder = 
    		ProtoStream.ProtoStreamChecksum.newBuilder();
    	
    	checksumMsgBuilder.setAlgorithm(CHECKSUM_ALGORITHM);
    	checksumMsgBuilder.setDigest(Base64.encode(digestBytes));
    	
    	checksumMsgBuilder.build().writeDelimitedTo(_outputStream);
    }



    public void flush () 
        throws IOException {        

        _outputStream.flush();
    }

    
    public void end ()
        throws IOException{
    	writeChecksum();
        _endDelimiter.writeDelimitedTo( _outputStream );
        closeStream();
    }

    private void closeStream () 
        throws IOException {
        _outputStream.close();
    }
}
