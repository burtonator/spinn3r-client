package com.spinn3r.api;

public class NullLogManager implements TransactionHistoryManager {

	@Override
	public int bytesWritten() {
		return 0;
	}

	@Override
	public void close() {

	}

	@Override
	public void log(String url) {

	}

}
