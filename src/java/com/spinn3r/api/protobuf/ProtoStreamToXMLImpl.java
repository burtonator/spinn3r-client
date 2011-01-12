package com.spinn3r.api.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.spinn3r.api.EntryDecoderFactory;
import com.spinn3r.api.util.EncodingException;
import com.spinn3r.api.util.ProtoStreamDecoder;

public class ProtoStreamToXMLImpl implements ConversionApp {
	
	private final InputStream input;
	private final OutputStream xmlfile;
	
	ProtoStreamToXMLImpl(InputStream input, OutputStream xml)
	{
		this.input = input;
		this.xmlfile  = xml;
	}

	@Override
	public void go() throws IOException, ParserConfigurationException,
			FactoryConfigurationError, ParseException, EncodingException,
			TransformerException {
		
		ProtoStreamDecoder<ContentApi.Entry> decoder = EntryDecoderFactory.newFactory().get(input);
		ProtobufToXMLHelper helper = new ProtobufToXMLHelper(false);
		PrintWriter outputWriter = new PrintWriter(xmlfile);
		
		helper.convert(decoder);
		
		outputWriter.print(helper.asString());
		outputWriter.close();		
	}

}
