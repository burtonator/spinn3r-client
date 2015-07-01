# source.status #

Endpoint: http://api.spinn3r.com/rss/source.status

Determines the status for a given weblog.

Returns a RSS feed with no items.  Metadata for the blog (title, link, description, etc) is includes in the root channel element.

# Parameters #

| link | Link to a weblog to which you'd like to obtain the status |
|:-----|:----------------------------------------------------------|

See GeneralParameters for additional documentation.

# Example Response #

```
<title>Kevin Burton&#039;s NEW FeedBlog</title>
<link>Kevin Burton&#039;s NEW FeedBlog</link>
<description>You may say I&#039;m a dreamer, but I&#039;m not the only one.</description>

<feed:guid>j4sD7MLVfmQ</feed:guid>
<feed:resource>http://feedblog.org/feed</feed:resource>
<feed:resource_status>304</feed:resource_status>
<feed:channel_link></feed:channel_link>
<feed:channel_guid>Dag8hMBOVDY</feed:channel_guid>
<feed:channel_title>Kevin Burton&#039;s NEW FeedBlog</feed:channel_title>
<feed:channel_desc>You may say I&#039;m a dreamer, but I&#039;m not the only one.</feed:channel_desc>
<feed:date_found>2007-06-01T12:34:09Z</feed:date_found>
<feed:etag>&quot;31af9c398cbb80e9900aed382d81c943&quot;</feed:etag>

<source:guid>Dag8hMBOVDY</source:guid>
<source:date_found>2005-10-13T04:50:08Z</source:date_found>
<source:tier>286</source:tier>
<source:indegree>10</source:indegree>
```

Returns HTTP 404 if the given weblog is not indexed by Spinn3r.

See SourceFields for more documentation on response values.

# From Spinn3r Reference Client #

```
SourceClient client = new SourceClient();

Config config = new Config();
config.setVendor( "acme" );
        
client.setConfig( config );

Source source = client.status( "http://feedblog.org" );
```