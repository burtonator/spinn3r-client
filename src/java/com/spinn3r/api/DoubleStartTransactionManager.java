package com.spinn3r.api;

import java.util.Set;
import java.util.HashSet;

class DoubleStartTransactionManager implements TransactionHistoryManager
{
	private final TransactionHistoryManager logManager;
	private final Set<String> activeSet = new HashSet<String>();

	DoubleStartTransactionManager(TransactionHistoryManager logManager)
	{
		this.logManager = logManager;
	}

	public synchronized void start(String url)
	{
		if(activeSet.contains(url))
			return;

		logManager.start(url);
		activeSet.add(url);
	}

	public synchronized void end(String url)
	{
		logManager.end(url);
		activeSet.remove(url);
	}

	public synchronized void error(String url, String message)
	{
		logManager.error(url, message);
		activeSet.remove(url);
	}

	public synchronized int bytesWritten()
	{
		return logManager.bytesWritten();
	}

	public synchronized void close()
	{
		logManager.close();
	}
}
