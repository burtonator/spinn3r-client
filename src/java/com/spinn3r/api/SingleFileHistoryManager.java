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
	private final UniversalCounter counter;
	private int byteCount = 0;
	
	SingleFileHistoryManager(PrintWriter pw, UniversalCounter counter)
	{
		this.output = pw;
		this.counter = counter;
	}
	
	SingleFileHistoryManager(OutputStream outputStream, UniversalCounter counter)
	{
		this(new PrintWriter(outputStream), counter);
	}
	
	SingleFileHistoryManager(File file, UniversalCounter counter) throws FileNotFoundException
	{
		this(new FileOutputStream(file), counter);
	}
	
	SingleFileHistoryManager(String filename, UniversalCounter counter) throws FileNotFoundException
	{
		this(new File(filename), counter);
	}
	
	@Override
	public void log(String url) {
		write(String.format("%d %d %s", counter.next(), url.length(), url));
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
