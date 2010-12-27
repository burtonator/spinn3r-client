package com.spinn3r.api.util;

public class ProtoStreamDecoderExcption extends RuntimeException {


	public ProtoStreamDecoderExcption() {
	}

	public ProtoStreamDecoderExcption(String message) {
		super(message);
	}

	public ProtoStreamDecoderExcption(Throwable cause) {
		super(cause);
	}

	public ProtoStreamDecoderExcption(String message, Throwable cause) {
		super(message, cause);
	}

}
