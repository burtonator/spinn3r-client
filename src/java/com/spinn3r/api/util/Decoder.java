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
public interface Decoder<E> {


	/**
	 * The number of objects that may be read or skipped without blocking
	 * 
	 * @return
	 */
	public int available() throws IOException;
	
	/** 
	 * Close this decoder.
	 * @throws IOException
	 */
	public void close() throws IOException;
	
	/**
	 * Mark the present position in the stream. Subsequent calls to reset() will attempt 
	 * to reposition the stream to this point
	 * 
	 * @param readAheadLimit Limit on the number of characters that may be read while still preserving the mark. 
	 *     After reading this many objects, attempting to reset the stream may fail.
	 * @throws IOException
	 */
	public void mark(int readAheadLimit) throws IOException;
	
	/**
	 * Return true of the mark operation is supported.
	 * 
	 * @return
	 */
	public boolean markSupported();
	
	/**
	 * Read an object from the input stream.
	 *
	 * @throws IOException
	 */
	public E read ( ) throws IOException;
	
	/**
     * Read a number of objects from the input stream. Read up to
     * len objects
     * 
     * @param len 
     * @throws IOException
     */
	public Collection<? extends E> read( int len ) throws IOException;
	
	/**
	 * Reset the stream position to the point of the previous mark.
	 * 
	 * @throws IOException
	 */
	public void reset() throws IOException;
	
	
	/**
	 * Skips over and discards n objects in the input stream. 
	 * 
	 * The stream, for a variety of reasons, may skip over fewer than
	 * n objects. This method returns the actual number of objects skipped
	 * 
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public long skip(long n) throws IOException;
	
	/**
	 * Return true if calls to <code>read()</code> will
	 * never return another value.
	 * 
	 * This function may return true if and only if there are no more 
	 * items to be read. However, it may also return false if 
	 * the decoder is uncertain if there are any more items to be
	 * read. Therefore, one may receive false even though 
	 * there are no more items (at least until the decoder can determine
	 * that there are no more items).
	 * 
	 * @return
	 */
	public boolean atEnd();
	
}