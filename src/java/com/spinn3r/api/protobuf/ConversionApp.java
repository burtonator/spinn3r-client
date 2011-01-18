package com.spinn3r.api.protobuf;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.spinn3r.io.utils.EncodingException;

interface ConversionApp 
{
	public void go() throws IOException, ParserConfigurationException, FactoryConfigurationError, ParseException, EncodingException, TransformerException;
}
