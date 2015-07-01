# permalink.status #

Given the full URL to a post, return the Spinn3r representation of it including all metadata (author, tags, etc).

Due to confusion with feed.status this is being replaced in Spinn3r 3.x with [permalink.entry](PermalinkEntryAPI.md)

# Parameters #

| resource | URL to a post for which you need status |
|:---------|:----------------------------------------|

See GeneralParameters for additional documentation.

# Example Response #

```
<item>

<title>Gillmor Gang Digests Comcast/Plaxo Deal</title>

<link>http://www.techcrunch.com/2008/05/14/gillmor-gang-digests-comcastplaxo-deal/</link>
<guid>http://techcrunch.com/2008/05/14/gillmor-gang-digests-comcastplaxo-deal</guid>

<pubDate>Thu, 15 May 2008 05:35:33 GMT</pubDate>

<dc:source>http://www.techcrunch.com</dc:source>

<weblog:title>TechCrunch</weblog:title>
<weblog:description>
TechCrunch profiles the companies, products and events that are 
defining and transforming the new web. TechCrunch is written by 
Michael Arrington.
</weblog:description>

<dc:lang>en</dc:lang>
<weblog:tier>0</weblog:tier>

<atom:author>
  <atom:name>Michael Arrington</atom:name>
  <atom:email></atom:email>
  <atom:link></atom:link>
</atom:author>

<weblog:indegree>10131</weblog:indegree>
<weblog:iranking>606</weblog:iranking>

<category>Company &amp;#038; Product Profiles</category>
<category>comcast</category>
<category>Plaxo</category>

<description>
...
</description>

<post:content_extract>
...
</post:content_extract>

<weblog:publisher_type>WEBLOG</weblog:publisher_type>

<atom:published></atom:published>
<post:date_found>2008-05-15T05:35:33Z</post:date_found>

<post:resource_guid>WhB3f4PRaxs</post:resource_guid>

</item>

```

# From Spinn3r Reference Client #

```
PermalinkStatusConfig config = new PermalinkStatusConfig();
PermalinkStatusClient client = new PermalinkStatusClient();

config.setVendor( "debug" );
config.setVersion( "2.2.1" );
config.setResource( "http://www.techcrunch.com/2008/05/14/gillmor-gang-digests-comcastplaxo-deal/" );
        
client.setConfig( config );

client.fetch();
List<BaseItem> results = client.getResults();
```