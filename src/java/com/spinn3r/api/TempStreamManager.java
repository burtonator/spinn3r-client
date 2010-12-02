package com.spinn3r.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The TempStreamManager returns TempOutputStream. A TempOutputStream
 * is a OutputStream which is linked to a temporary file. When the stream
 * is closed, the file is deleted. 
 * 
 * @author jwu
 *
 */
class TempStreamManager implements Provider<OutputStream>
{
	private final Provider<File> tempFileProvider;
	
	@Inject
	TempStreamManager(Provider<File> tempFileProvider)
	{
		this.tempFileProvider = tempFileProvider;
	}
	
	protected static class TempOutputStream extends OutputStream
	{
		private final File file;
		private final OutputStream outputStream;
		
		protected TempOutputStream(File file) throws FileNotFoundException
		{
			this.file = file;
			this.outputStream = new FileOutputStream(file);
		}
		
		@Override
		public void write(int b) throws IOException {
			outputStream.write(b);
			
		}
		
		@Override
		public void close() throws IOException {
			outputStream.close();
			if(!file.delete())
				throw new IOException("Unable to delete " + file.toString());
		}
	}

	public OutputStream get()
	{
		try {
			return new TempOutputStream(tempFileProvider.get());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
