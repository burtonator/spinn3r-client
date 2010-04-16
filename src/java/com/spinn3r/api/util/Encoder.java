package com.spinn3r.api.util;

import java.io.IOException;
import java.util.Collection;

/**
 * Interface for writing objects to an output stream. 
 * (Or wherever else you want to send them)
 * 
 * @author jwu
 *
 */
public interface Encoder<E> {
	/**
	 * Write a object to the output stream.
	 * 
	 * @param entry
	 * @throws IOException
	 */
	public void write ( E entry ) throws IOException;
	
	/**
	 * Write all the objects in a collection
	 * 
	 * @param entries
	 * @throws IOException
	 */
	public void writeAll ( Collection<E> entries ) throws IOException;
	
	/**
	 * Signal that there are to be no more entries. For some implementations 
	 * of this interface, this may cause any queued entries to be flushed out
	 * 
	 * @throws IOException
	 */
	public void end () throws IOException;
	
	/**
	 * Flush the stored entries to the output stream. This function may
	 * have no effect for some implementations as some implementations may
	 * require a call to <code>end()</code> before anything is flushed.
	 * 
	 * @throws IOException
	 */
	public void flush () throws IOException;
}