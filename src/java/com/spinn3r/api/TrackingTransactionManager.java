package com.spinn3r.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;

/**
 * A tracking history manager keeps track of which messages have been written to this transaction history.
 * This Transaction History Manager keeps track of which urls have been started but have not been
 * ended (or errored). 
 * 
 * @author jwu
 *
 */
class TrackingTransactionManager implements TransactionHistoryManager {

	private final TransactionHistoryManager logManager;
	private final Set<String> activeUrls = Collections.synchronizedSet(new HashSet<String>());
	
	@Inject
	TrackingTransactionManager(TransactionHistoryManager logManager)
	{
		this.logManager = logManager;
	}
	
	@Override
	public void end(String url) {
		
		logManager.end(url);
		activeUrls.remove(url);
	}

	@Override
	public void error(String url, String message) {
		logManager.error(url, message);
		activeUrls.remove(url);
	}

	@Override
	public void start(String url) {
		logManager.start(url);
		activeUrls.add(url);
	}

	/**
	 * Return true if the url has there has been a start(url) call 
	 * and no matching end(url) or error(url) call. 
	 * 
	 * @param url
	 * @return
	 */
	public boolean isActive(String url)
	{
		return activeUrls.contains(url);
	}
	
	/**
	 * 
	 * @return Number of urls which are "active"
	 */
	public int numActive()
	{
		return activeUrls.size();
	}

	@Override
	public void close() {
		logManager.close();		
	}

	@Override
	public int bytesWritten() {
		return logManager.bytesWritten();
	}
}
