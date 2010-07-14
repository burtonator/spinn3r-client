package com.spinn3r.api.util;

public class EncodingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EncodingException() {
	}

	public EncodingException(String message) {
		super(message);
	}

	public EncodingException(Throwable cause) {
		super(cause);
	}

	public EncodingException(String message, Throwable cause) {
		super(message, cause);
	}

}
