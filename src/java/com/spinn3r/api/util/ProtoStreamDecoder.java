package com.spinn3r.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.inject.Provider;
import com.google.inject.internal.ImmutableSet;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Message.Builder;
import com.spinn3r.api.protobuf.ProtoStream;
import com.spinn3r.api.protobuf.ProtoStream.ApplicationHeader;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamDelimiter;
import com.spinn3r.api.protobuf.ProtoStream.ProtoStreamHeader;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ProtoStreamDecoder<T extends AbstractMessageLite> implements Decoder<T> {

	private static final Set<String> SUPPORTED_VERSION = ImmutableSet.<String>builder()
		.add("1.0").add("2.0")
		.build();
	private static final String CHECKSUM_ALGORITHM = "MD5";

	private final InputStream _originalStream;
	private final DigestInputStream _digestStream;
	private final Provider<? extends Builder> _builderFactory;

	private ProtoStreamHeader header = null;

	private boolean initialized = false;
	private boolean done = false;

	public static <K extends AbstractMessageLite> ProtoStreamDecoder<K> 
		newDecoder(InputStream input, Provider<? extends Builder> provider) {

		return new ProtoStreamDecoder<K>(input, provider);
	}

	public static <K extends AbstractMessageLite> ProtoStreamDecoder<K> 
	newDecoder(InputStream input, final Builder builder) {

		return newProtoStreamDecoder(input, new Provider<Builder>() {
			public Builder get() {
				return builder.clone().clear();
			}
		});

	}

	public static <K extends AbstractMessageLite> ProtoStreamDecoder<K> 
	newProtoStreamDecoder(InputStream input, Provider<? extends Builder> provider) {

		return new ProtoStreamDecoder<K>(input, provider);
	}

	public static <K extends AbstractMessageLite> ProtoStreamDecoder<K> 
	newProtoStreamDecoder(InputStream input, final Builder builder) {

		return newProtoStreamDecoder(input, new Provider<Builder>() {
			public Builder get() {
				return builder.clone().clear();
			}
		});

	}


	protected ProtoStreamDecoder ( InputStream input, Provider<? extends Builder> builderFactory ) {
		_originalStream = input;
		try {
			_digestStream = new DigestInputStream(input, MessageDigest.getInstance(CHECKSUM_ALGORITHM));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		_builderFactory = builderFactory;


	}

	public String getApplicationHeader(String key) throws IOException {
		init();

		for(ApplicationHeader applicationHeader : header.getApplicationHeaderList()) {
			if(applicationHeader.getName().equals(key)) {
				return applicationHeader.getValue();
			}
		}

		return null;
	}

	private void init() throws IOException {

		if(initialized)
			return;

		String expectedType = _builderFactory.get().getDescriptorForType().getFullName();

		
		header = ProtoStreamHeader.parseDelimitedFrom(_digestStream);

		String version = header.getVersion();

		if ( ! SUPPORTED_VERSION.contains(version) ) {
			String msg = String.format("Version mismatch expected '%s' got '%s'\n", SUPPORTED_VERSION, version );
			throw new ProtoStreamDecoderException ( msg );
		}

		String type = header.getDefaultEntryType();

		if ( ! type.equals( expectedType ) ) {
			String msg = String.format("Type mismatch expected '%s' got '%s'\n", expectedType, type );
			throw new ProtoStreamDecoderException ( msg );
		}

		initialized = true;
	}

	@SuppressWarnings("unchecked")
	public T read ( )
	throws IOException {

		init();

		T res = null;

		ProtoStreamDelimiter delimiter = ProtoStreamDelimiter.parseDelimitedFrom(_digestStream);
		
		if ( delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.END )
			res = null;

		else if ( delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.ENTRY ) {
			Builder builder = _builderFactory.get();

			builder.mergeDelimitedFrom( _digestStream );


			res = (T)builder.build();
		}
		
		else if (delimiter.getDelimiterType() == ProtoStreamDelimiter.DelimiterType.CHECKSUM) {
			MessageDigest digest = _digestStream.getMessageDigest();
			String encodedDigset = Base64.encode(digest.digest());
			digest.reset();
			
			ProtoStream.ProtoStreamChecksum checksumMsg = 
				ProtoStream.ProtoStreamChecksum.parseDelimitedFrom(_digestStream);
			
			if(!checksumMsg.getAlgorithm().equals(CHECKSUM_ALGORITHM)) {
				throw new IOException(checksumMsg.getAlgorithm() + " not supported checksum algorithm");
			}
			
			if(!checksumMsg.getDigest().equals(encodedDigset)) {
				throw new IOException("Checksum failed");
			}
		}

		else {
			CodedInputStream codedStream = CodedInputStream.newInstance(_digestStream);
			int size = codedStream.readRawVarint32();
			while(size > 0) {
				size -= _digestStream.skip(size);
			}
		}

		if(res == null) {
			done = true;
		}
		
		return res;
	}

	@Override
	public int available() {
		return 0;
	}

	@Override
	public void close() throws IOException {
		_originalStream.close();
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

	@Override
	public boolean atEnd() {
		return done;
	}

}