package com.spinn3r.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.google.protobuf.CodedInputStream;
import com.spinn3r.api.protobuf.ProtoStream;

public class ProtoStreamCheckSum {
	
	private final String algorithm;
	
	public ProtoStreamCheckSum getChecker() {
		return new ProtoStreamCheckSum(ProtoStreamBytesEncoder2.CHECKSUM_ALGORITHM);
	}
	
	private ProtoStreamCheckSum(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public void read(InputStream stream) throws IOException, EncodingException {
		DigestInputStream digestStream;
		try {
			digestStream = new DigestInputStream(stream, MessageDigest.getInstance(algorithm));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		
		//Skip the header
		consumeNext(digestStream);
		
		ProtoStream.ProtoStreamDelimiter.DelimiterType type = nextType(stream);
		
		while(type != ProtoStream.ProtoStreamDelimiter.DelimiterType.END) {
			if(type == ProtoStream.ProtoStreamDelimiter.DelimiterType.CHECKSUM) {
				MessageDigest digest = digestStream.getMessageDigest();
				byte[] checksumBytes = digest.digest();
				digest.reset();
				
				ProtoStream.ProtoStreamChecksum checkSumMsg =
					ProtoStream.ProtoStreamChecksum.parseDelimitedFrom(digestStream);
				byte[] markBytes = Base64.decode(checkSumMsg.getDigest());
				if(!Arrays.equals(checksumBytes, markBytes))
					throw new IOException("Checksum failed");
			}
			else {
				consumeNext(digestStream);
			}
		}
		
	}
	
	private ProtoStream.ProtoStreamDelimiter.DelimiterType nextType(InputStream stream) throws IOException {
		ProtoStream.ProtoStreamDelimiter delimiter = 
			ProtoStream.ProtoStreamDelimiter.parseDelimitedFrom(stream);
		
		return delimiter.getDelimiterType();
	}
	
	private void consumeNext(InputStream stream) throws IOException {
		CodedInputStream coded = CodedInputStream.newInstance(stream);
		
		int size = coded.readRawVarint32();
		while(size > 0) 
			size -= stream.skip(size);
	}
	
	
}
