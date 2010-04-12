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
	 * Indicate that the system has started downloading the 
	 * given url.
	 * 
	 * @param url
	 */
	public void start(String url);
	
	/**
	 * Indicate that the system has is finished downloading
	 * (and saving, if necessary) the given URL
	 * 
	 * @param url
	 */
	public void end(String url);
	
	/**
	 * Indicate that the system has hit an error in 
	 * downloading the given URL.
	 * 
	 * @param url
	 * @param message
	 */
	public void error(String url, String message);
	
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
