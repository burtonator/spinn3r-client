# history() #

Given a weblog URL, return recently published articles.

## permalink.history() ##

Given a weblog URL, return recently published HTML permalinks.  This can be used to find the most recent results from techcrunch.com, gigaom.com, etc.

Results in recent posts sorted by reverse chronological order.

As of Oct 1, 2008, all content is returned with the full RSS metadata.

## feed.history() ##

Includes full history for RSS items.

## comment.history ##

Includes history for weblog comments (beta).

### Additional comment.history parameters ###

| feed | Find comments for a specific feed |
|:-----|:----------------------------------|
| permalink | Find comments posted to a specific permalink |

# Parameters #

| source | URL to the source weblog |
|:-------|:-------------------------|

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
TechCrunch profiles the companies, products and events that 
are defining and transforming the new web. TechCrunch is 
written by Michael Arrington.
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
PermalinkHistoryConfig config = new PermalinkHistoryConfig();
PermalinkHistoryClient client = new PermalinkHistoryClient();

config.setVendor( "XXXXXX" );
config.setSource( "http://techcrunch.com" );

client.setConfig( config );

client.fetch();
List<BaseItem> results = client.getResults();
```