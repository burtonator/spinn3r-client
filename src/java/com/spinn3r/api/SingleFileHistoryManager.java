package com.spinn3r.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * This transaction history manager writes log messages to a single file.
 * 
 * @author jwu
 *
 */
class SingleFileHistoryManager implements TransactionHistoryManager {
	
	private final PrintWriter output;
	private int byteCount = 0;
	
	SingleFileHistoryManager(PrintWriter pw)
	{
		this.output = pw;
	}
	
	SingleFileHistoryManager(OutputStream outputStream)
	{
		this(new PrintWriter(outputStream));
	}
	
	SingleFileHistoryManager(File file) throws FileNotFoundException
	{
		this(new FileOutputStream(file));
	}
	
	SingleFileHistoryManager(String filename) throws FileNotFoundException
	{
		this(new File(filename));
	}
	
	@Override
	public void end(String url) {
		write(String.format("end %d %s", url.length(), url));
	}

	@Override
	public void error(String url, String message) {
		write(String.format("error %d %s %d %s", url.length(), url, message.length(), message));
	}

	@Override
	public void start(String url) {
		write(String.format("start %d %s", url.length(), url));
	}

	@Override
	public synchronized void close() {
		output.close();		
	}

	@Override
	public synchronized int bytesWritten() {
		return byteCount;
	}
	
	private synchronized void write(String str)
	{
		byteCount += str.getBytes().length;
		output.println(str);
		output.flush();
	}

}
