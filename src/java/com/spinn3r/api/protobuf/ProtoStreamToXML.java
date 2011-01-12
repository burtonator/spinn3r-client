package com.spinn3r.api.protobuf;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.spinn3r.api.util.EncodingException;

public class ProtoStreamToXML {

	public static void main(String[] args) throws IOException, ParserConfigurationException, FactoryConfigurationError, ParseException, EncodingException, TransformerException
	{
		if(args.length == 2)
			ProtoStreamToXMLFactory.getInstance(args[0], args[1]).go();
		else if(args.length == 1)
			ProtoStreamToXMLFactory.getInstance(args[0]).go();
		else
		{
			System.err.println("Usage: java " + ProtoStreamToXML.class.getCanonicalName() +
					" args\n" +
					" Arguments may be in one of three forms:\n" +
					" protostreamFileName\n" +
					" protostreamFileName xmlFileName\n" +
					" directory\n\n");
		}
		
	}
}
