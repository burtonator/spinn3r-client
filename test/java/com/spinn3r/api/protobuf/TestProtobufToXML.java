package com.spinn3r.api.protobuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.spinn3r.api.protobuf.ContentApi.Response;
import com.spinn3r.api.util.EncodingException;

@RunWith(Parameterized.class)
public class TestProtobufToXML
{
	private final Response protobuf;
	private final Document xml;
	
	
	public TestProtobufToXML(Response entry, Document xml)
	{
		this.protobuf = entry;
		this.xml = xml;
	}
	
	@Before
	public void setUp()
	{
		XMLUnit.setIgnoreWhitespace(true);
	}
	
	@Test
	public void test() throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, ParseException, EncodingException, TransformerException
	{
		ProtobufToXML converter = new ProtobufToXML(true);
	
		String protobufXML = converter.convert(protobuf);
		Document protobufDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(protobufXML)));
		
		removeIgnoredTags(protobufDoc);
		removeIgnoredTags(xml);
		
		Diff diff = new Diff(xml, protobufDoc);
		
		diff.overrideElementQualifier(new ElementNameQualifier());	
		XMLAssert.assertXMLEqual(diff, true);
	}
	
	private void removeIgnoredTags(Document document)
	{
		removeAll(document.getDocumentElement(), Node.ELEMENT_NODE, "dc:lang");
		removeAll(document.getDocumentElement(), Node.ELEMENT_NODE, "post:body");
		removeAll(document.getDocumentElement(), Node.ELEMENT_NODE, "pubDate");
		removeAll(document.getDocumentElement(), Node.ELEMENT_NODE, "description");
		removeAll(document.getDocumentElement(), Node.ELEMENT_NODE, "weblog:iranking");
		removeAll(document.getDocumentElement(), Node.ELEMENT_NODE, "source:iranking");
	}
	
	
	// This method walks the document and removes all nodes
	// of the specified type and specified name.
	// If name is null, then the node is removed if the type matches.
	private static void removeAll(Node node, short nodeType, String name) {
	    if (node.getNodeType() == nodeType &&
	            (name == null || node.getNodeName().equals(name))) {
	        node.getParentNode().removeChild(node);
	    } else {
	        // Visit the children
	        NodeList list = node.getChildNodes();
	        for (int i=0; i<list.getLength(); i++) {
	            removeAll(list.item(i), nodeType, name);
	        }
	    }
	}
	
	@Parameters
	public static Collection<Object[]> getParamters() throws FileNotFoundException, IOException, SAXException, ParserConfigurationException
	{
		Collection<Object[]> parameters = new ArrayList<Object[]>(1);
		
		for(int i = 1; i <= 4; i++)
		{
			Response protobuf = Response.parseFrom(new FileInputStream("test/data/test" + i + ".protobuf"));
			
			DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
			builder.setIgnoringElementContentWhitespace(true);
			
			Document document = builder.newDocumentBuilder().parse(new GZIPInputStream(new FileInputStream("test/data/test" + i + ".xml.gz")));
			
			parameters.add(new Object[] { protobuf, document });
		}
		
		return parameters; 
	}
	
}
