package com.spinn3r.api.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.spinn3r.api.protobuf.ContentApi.Response;
import com.spinn3r.io.utils.EncodingException;

class ProtobufToXMLAppImpl implements ProtobufToXMLApp {

	private final InputStream protobufFile;
	private final OutputStream xmlfile;
	
	ProtobufToXMLAppImpl(InputStream protobuf, OutputStream xml)
	{
		this.protobufFile = protobuf;
		this.xmlfile  = xml;
	}
	
	@Override
	public void go() throws IOException, ParserConfigurationException, FactoryConfigurationError, ParseException, EncodingException, TransformerException
	{
		Response response = Response.parseFrom(protobufFile);
		PrintWriter outputWriter = new PrintWriter(xmlfile);
		
		ProtobufToXML protobufToXML = new ProtobufToXML(false);
		outputWriter.print(protobufToXML.convert(response));
	}
	
}
