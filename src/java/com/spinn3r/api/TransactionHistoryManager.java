package com.spinn3r.api;

/**
 * A TransactionHistoryManager is an interface to a transaction log system,
 * which this client uses to track the program state and restore
 * after a failure.
 * 
 * @author jwu
 *
 */
interface TransactionHistoryManager 
{	
	/**
	 * Indicate that we're going to write next follwoing URL
	 * 
	 * @param url
	 */
	public void log(String url);
	
	/**
	 * Close ther log.
	 */
	public void close();
	
	/**
	 * Get the number of bytes written into the log.
	 * @return
	 */
	public int bytesWritten();
}
