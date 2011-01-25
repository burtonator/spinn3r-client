package com.spinn3r.api.protobuf;

import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.jamesmurty.utils.XMLBuilder;
import com.spinn3r.api.ISO8601DateParser;
import com.spinn3r.api.RFC822DateParser;
import com.spinn3r.api.protobuf.ContentApi.Author;
import com.spinn3r.api.protobuf.ContentApi.Content;
import com.spinn3r.api.protobuf.ContentApi.Entry;
import com.spinn3r.api.protobuf.ContentApi.Feed;
import com.spinn3r.api.protobuf.ContentApi.FeedEntry;
import com.spinn3r.api.protobuf.ContentApi.Lang;
import com.spinn3r.api.protobuf.ContentApi.Link;
import com.spinn3r.api.protobuf.ContentApi.PermalinkEntry;
import com.spinn3r.api.protobuf.ContentApi.Response;
import com.spinn3r.api.protobuf.ContentApi.Source;
import com.spinn3r.api.util.CompressedBLOB;
import com.spinn3r.api.util.CompressedBlob2;
import com.spinn3r.api.util.EncodingException;
import com.spinn3r.api.util.ProtoStreamDecoder;

class ProtobufToXMLHelper 
{
	private XMLBuilder builder;
	private final boolean ignoreFormattingErrors;
	
	public ProtobufToXMLHelper(boolean ignoreFormattingErrors) throws ParserConfigurationException, FactoryConfigurationError
	{
		
		builder = XMLBuilder.create("rss");
		this.ignoreFormattingErrors = ignoreFormattingErrors;
	}
	
	public String asString() throws TransformerException
	{
		Properties outputProperties = new Properties();
		
		outputProperties.put(javax.xml.transform.OutputKeys.METHOD, "xml");
		
		return builder.root().asString(outputProperties);
	}
	
	private void init()
	{
		builder = builder.attribute("version", "2.0").
			attribute("xmlns:dc",
				"http://purl.org/dc/elements/1.1/").attribute("xmlns:atom",
				"http://www.w3.org/2005/Atom").attribute("xmlns:post",
				"http://tailrank.com/ns/#post").attribute("xmlns:link",
				"http://spinn3r.com/ns/link").attribute("xmlns:cluster-log",
				"http://tailrank.com/ns/#cluster-log").attribute(
				"xmlns:source", "http://tailrank.com/ns/#source").attribute(
				"xmlns:source_register_history",
				"http://spinn3r.com/ns/#source_register_history").attribute(
				"xmlns:source_discovery",
				"http://spinn3r.com/ns/#source_discovery").attribute(
				"xmlns:target", "http://tailrank.com/ns/#target").attribute(
				"xmlns:api", "http://tailrank.com/ns/#api").attribute(
				"xmlns:weblog", "http://tailrank.com/ns/#weblog").attribute(
				"xmlns:feed", "http://tailrank.com/ns/#feed").attribute(
				"xmlns:comment", "http://tailrank.com/ns/#comment");
		
		builder = builder.element("channel")
			.element("link").text("http://spinn3r.com").up()
			.element("generator").text("Powered by Spinn3r").up()
			.element("title").text("Spinn3r permalink3.getDelta results").up();	
		
	}
	
	public void convert(Response response) throws ParseException, EncodingException, IOException
	{
		init();
		
		if(response.hasNextRequestUrl())
		{
			String next_url = response.getNextRequestUrl().replace("protobuf", "rss");
			builder = builder.e("api:next_request_url").text(next_url).up();
			builder = builder.e("atom:link").a("rel", "next").a("href", next_url).up();
		}
		if(response.hasRequestUrl())
			builder = builder.e("dc:source").text(response.getRequestUrl().replace("protobuf", "rss")).up();
		
		
		for(Entry entry : response.getEntryList())
			convert(entry);
	}
	
	public void convert(ProtoStreamDecoder<ContentApi.Entry> decoder) throws ParseException, EncodingException, IOException {
		init();
		
		ContentApi.Entry entry;
		while((entry = decoder.read()) != null) {
			convert(entry);
		}
	}
	
	private void convert(Entry entry) throws ParseException, EncodingException, IOException
	{
		builder = builder.element("item");
		if(entry.hasPermalinkEntry())
			convert(entry.getPermalinkEntry());
		
		if(entry.hasSource())
			convert(entry.getSource());
		
		if(entry.hasFeedEntry())
			convert(entry.getFeedEntry());
		
		if(entry.hasFeed())
			convert(entry.getFeed());
		
		builder = builder.up();
	}
	
	private void convert(PermalinkEntry permalink) throws ParseException, EncodingException, IOException
	{	
		builder = builder.element("title");
		if(permalink.hasTitle())
			builder = builder.text(permalink.getTitle());
		builder = builder.up();
		
		for(Link link : permalink.getLinkList())
			builder = builder.element("link").text(link.getHref()).up();
		
		/* Since old formats of permalinkEntry did not have a guid,
		 * use a guid from the cannoncial link if there is one
		 */
		if(permalink.hasCanonicalLink())
			builder = builder.element("guid").text(permalink.getCanonicalLink().getResource()).up();
		
		builder = builder.element("pubDate");
		if(permalink.hasLastPublished() && permalink.getLastPublished().length() > 0)
		{
			String date = RFC822DateParser.toString(ISO8601DateParser.parse(permalink.getLastPublished()));
			builder = builder.text(date);
		}
		builder = builder.up();
		
		for(Lang lang : permalink.getLangList())
			builder = builder.element("dc:lang").text(lang.getCode()).up();
		
		if(permalink.hasLastPublished())
			builder = builder.element("atom:published").text(permalink.getLastPublished()).up();
		
		if(permalink.hasDateFound())
		{
			builder = builder.element("post:date_found").text(permalink.getDateFound()).up();			
		}
		
		if(permalink.hasIdentifier())
		{
			builder = builder.element("post:timestamp").text(Long.toString(permalink.getIdentifier())).up();
		}
		
		if(permalink.hasSpamProbability())
			builder = builder.element("post:spam_probability").text(String.format("%.1f", permalink.getSpamProbability())).up();
		
		if(permalink.hasHashcode())
		{
			builder = builder.element("post:resource_guid").text(permalink.getHashcode()).up();
			builder = builder.element("post:hashcode").text(permalink.getHashcode()).up();
		}
		
		
		
		if(permalink.hasContentExtract())
		{
			try {
				String content_extract = extractContent(permalink.getContentExtract());
				if(content_extract != null && content_extract.length() != 0)
					builder = builder.element("post:content_extract").text(content_extract).up();
				
			} catch (EncodingException e) {
				if(!this.ignoreFormattingErrors)
					throw e;
			} 
			
		}
	}
	
	private String extractContent(Content content) throws EncodingException, IOException
	{
		if(content.hasEncoding())
			return extractContent(content.getData().toByteArray(), content.getEncoding());
		else
			return extractContent(content.getData().toByteArray());
	}
	
	private String extractContent(byte[] data, String encoding) throws EncodingException, IOException
	{
		return new CompressedBlob2(data, encoding).decompress();
	}
	
	private String extractContent(byte[] data) throws EncodingException, IOException
	{
		return new CompressedBLOB(data).decompress();
	}
	
	private void convert(Source source)
	{
		if(source.hasTitle())
		{
			builder = builder.element("weblog:title").text(source.getTitle()).up();
			builder = builder.element("source:title").text(source.getTitle()).up();
		}
		
		if(source.hasDescription()) 
		{
			builder = builder.element("weblog:description").text(source.getDescription()).up();
			
		}
		
		builder = builder.element("source:description");
		if(source.hasDescription())
			builder = builder.text(source.getDescription());
		builder = builder.up();
		
		if(source.hasIndegree())
		{
			builder = builder.element("weblog:indegree").text(Integer.toString(source.getIndegree())).up();
			builder = builder.element("source:indegree").text(Integer.toString(source.getIndegree())).up();
		}
		

		for(Link link : source.getLinkList())
		{
			builder = builder.element("dc:source").text(link.getHref()).up();
		}
		
		for(Link link : source.getLinkList())
		{
			builder = builder.element("source:resource").text(link.getHref()).up();
		}
		
		if(source.hasPublisherType())
		{
			builder = builder.element("weblog:publisher_type").text(source.getPublisherType()).up();
			builder = builder.element("source:publisher_type").text(source.getPublisherType()).up();
		}
		
		if(source.hasTier())
		{
			builder = builder.element("weblog:tier").text(Integer.toString(source.getTier())).up();
			builder = builder.element("source:tier").text(Integer.toString(source.getTier())).up();
		}
		
		if(source.hasHashcode())
			builder = builder.element("source:hashcode").text(source.getHashcode()).up();
		
		
		// TODO:Where did IRanking go?
		builder = builder.element("source:iranking").text("0").up();
		builder = builder.element("weblog:iranking").text("0").up();
	}
	
	private void convert(FeedEntry feedEntry) throws IOException, EncodingException {
		for(String category : feedEntry.getCategoryList())
			builder = builder.element("category").text(category).up();
		
		if(feedEntry.hasTitle() && feedEntry.getTitle().length() != 0)
			builder = builder.element("post:title").text(feedEntry.getTitle()).up();
		
		
		for(Author author : feedEntry.getAuthorList())
		{
			if(!author.hasName() || author.getName().length() == 0)
				continue;
			builder = builder.element("atom:author");
			builder = builder.element("atom:name").text(author.getName()).up();
			
			builder = builder.element("atom:email");
			if(author.hasEmail())
				builder = builder.text(author.getEmail());
			builder = builder.up();
			
			if(author.getLinkCount() == 0)
			{
				builder = builder.element("atom:link").up();
			}
			else 
			{
				for(Link link : author.getLinkList())
				{
					builder = builder.element("atom:link").text(link.getHref()).up();
				}
			}
			
			builder = builder.up();
		}
		
		if(feedEntry.hasContent())
		{
			try {
				String content = extractContent(feedEntry.getContent());
				
				if(content != null && content.length() != 0)
					builder = builder.element("post:body").text(content).up();
				
			} catch (EncodingException e) {
				if(!this.ignoreFormattingErrors)
					throw e;
			}
			
		}
	}
	
	private void convert(Feed feed)
	{
		if(feed.hasCanonicalLink())
		{
			if(feed.getCanonicalLink().getHref().length() != 0)
			{
				builder = builder.element("feed:url").text(feed.getCanonicalLink().getHref()).up();
				builder = builder.element("feed:link").text(feed.getCanonicalLink().getHref()).up();
			}
			if(feed.getCanonicalLink().getResource().length() != 0)
			{
				builder = builder.element("feed:resource").text(feed.getCanonicalLink().getResource()).up();
			}
		}
		
		if(feed.hasHashcode() && feed.getHashcode().length() != 0)
			builder = builder.element("feed:hashcode").text(feed.getHashcode()).up();
	}
}
