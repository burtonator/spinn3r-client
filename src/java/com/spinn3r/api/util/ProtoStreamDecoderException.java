package com.spinn3r.api.util;

public class ProtoStreamDecoderException extends RuntimeException {


	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ProtoStreamDecoderException() {
	}

	public ProtoStreamDecoderException(String message) {
		super(message);
	}

	public ProtoStreamDecoderException(Throwable cause) {
		super(cause);
	}

	public ProtoStreamDecoderException(String message, Throwable cause) {
		super(message, cause);
	}

}
