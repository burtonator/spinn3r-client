package com.spinn3r.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Interface for writing objects to an output stream. 
 * (Or wherever else you want to send them)
 * 
 * @author jwu
 *
 */
public interface Decoder<E> {


	/**
	 * Write a object to the output stream.
	 * 
	 * @param entry
	 * @throws IOException
	 */
	public E read ( ) throws IOException;
	
}