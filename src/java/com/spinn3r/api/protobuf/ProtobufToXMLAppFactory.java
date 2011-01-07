package com.spinn3r.api.protobuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FilenameUtils;

public class ProtobufToXMLAppFactory
{
	private static final FilenameFilter PROTOBUF_NAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return FilenameUtils.getExtension(name).equals("protobuf");
		}
		
	};
	
	
	static ProtobufToXMLApp getInstance(String protobufFilename, String xmlFilename) throws IOException
	{
		return new ProtobufToXMLAppImpl(new FileInputStream(protobufFilename), new GZIPOutputStream(new FileOutputStream(xmlFilename)));
	}
	
	static ProtobufToXMLApp getInstance(File protobufFile, File xmlFile) throws FileNotFoundException, IOException
	{
		return new ProtobufToXMLAppImpl(new FileInputStream(protobufFile), new GZIPOutputStream(new FileOutputStream(xmlFile)));
	}
	
	static ProtobufToXMLApp getInstance(File protobufFileOrDir) throws FileNotFoundException, IOException
	{
		if(protobufFileOrDir.isDirectory())
			return getDirectoryInstance(protobufFileOrDir);
		else
			return getFileInstance(protobufFileOrDir);
	}
	
	private static ProtobufToXMLApp getDirectoryInstance(File directory) throws FileNotFoundException, IOException
	{
		String[] filesNames = directory.list(ProtobufToXMLAppFactory.PROTOBUF_NAME_FILTER);
		ProtobufToXMLApp[] appArray = new ProtobufToXMLApp[filesNames.length];
		
		for(int i = 0; i < filesNames.length; i++)
			appArray[i] = getFileInstance(new File(directory, filesNames[i]));
		
		return new ProtobufToXMLAppCollection(appArray);
	}

	
	private static ProtobufToXMLApp getFileInstance(File file) throws FileNotFoundException, IOException
	{
		String xmlFilename = FilenameUtils.getBaseName(file.getAbsolutePath()) + ".xml.gz";
		OutputStream os = new GZIPOutputStream(new FileOutputStream(new File(file.getParentFile(), xmlFilename)));
		return new ProtobufToXMLAppImpl(new FileInputStream(file), os);
	}
	
	static ProtobufToXMLApp getInstance(String fileOrDir) throws FileNotFoundException, IOException
	{	
		return getInstance(new File(fileOrDir));
	}
	
	
}
