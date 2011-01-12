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

public class ProtoStreamToXMLFactory {
	
	private static final FilenameFilter PROTOSTREAM_NAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return FilenameUtils.getExtension(name).equals("protostream");
		}
		
	};
	
	
	static ConversionApp getInstance(String protobufFilename, String xmlFilename) throws IOException
	{
		return new ProtoStreamToXMLImpl(new FileInputStream(protobufFilename), new GZIPOutputStream(new FileOutputStream(xmlFilename)));
	}
	
	static ConversionApp getInstance(File protobufFile, File xmlFile) throws FileNotFoundException, IOException
	{
		return new ProtoStreamToXMLImpl(new FileInputStream(protobufFile), new GZIPOutputStream(new FileOutputStream(xmlFile)));
	}
	
	static ConversionApp getInstance(File protobufFileOrDir) throws FileNotFoundException, IOException
	{
		if(protobufFileOrDir.isDirectory())
			return getDirectoryInstance(protobufFileOrDir);
		else
			return getFileInstance(protobufFileOrDir);
	}
	
	private static ConversionApp getDirectoryInstance(File directory) throws FileNotFoundException, IOException
	{
		String[] filesNames = directory.list(ProtoStreamToXMLFactory.PROTOSTREAM_NAME_FILTER);
		ConversionApp[] appArray = new ConversionApp[filesNames.length];
		
		for(int i = 0; i < filesNames.length; i++)
			appArray[i] = getFileInstance(new File(directory, filesNames[i]));
		
		return new ConversionAppCollection(appArray);
	}

	
	private static ConversionApp getFileInstance(File file) throws FileNotFoundException, IOException
	{
		String xmlFilename = FilenameUtils.getBaseName(file.getAbsolutePath()) + ".xml.gz";
		OutputStream os = new GZIPOutputStream(new FileOutputStream(new File(file.getParentFile(), xmlFilename)));
		return new ProtoStreamToXMLImpl(new FileInputStream(file), os);
	}
	
	static ConversionApp getInstance(String fileOrDir) throws FileNotFoundException, IOException
	{	
		return getInstance(new File(fileOrDir));
	}
}
