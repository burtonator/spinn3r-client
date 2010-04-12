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
	public void end(String url) {

	}

	@Override
	public void error(String url, String message) {

	}

	@Override
	public void start(String url) {

	}

}
