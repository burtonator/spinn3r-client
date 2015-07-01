# feed.status #

Endpoint: http://api.spinn3r.com/rss/feed.status

Determines the status for a given weblog.

Returns a RSS feed with no items.  Metadata for the blog (title, link, description, etc) is includes in the root channel element.

# Parameters #

| link | Link to a feed to which you'd like to obtain the status |
|:-----|:--------------------------------------------------------|

See GeneralParameters for additional documentation.

# Example Response #

```
<channel>        

<title>Kevin Burton&#039;s NEW FeedBlog</title>
<link></link>
<guid>http://feedblog.org/feed</guid>

<description></description>

<feed:guid>j4sD7MLVfmQ</feed:guid>
<feed:resource>http://feedblog.org/feed</feed:resource>
<feed:link></feed:link>
<feed:resource_status>0</feed:resource_status>
<feed:channel_link></feed:channel_link>
<feed:channel_guid>CMpyEOwlFlg</feed:channel_guid>
<feed:channel_title>Kevin Burton&#039;s NEW FeedBlog</feed:channel_title>
<feed:channel_desc>You may say I&#039;m a dreamer, but I&#039;m not the only one.</feed:channel_desc>
<feed:date_found>2007-06-01T12:34:09Z</feed:date_found>
<feed:etag>&quot;a73b45994111ef70a6999cb846dd9abd&quot;</feed:etag>
<feed:last_published>2008-07-03T05:00:34Z</feed:last_published>

</channel>
```

Returns HTTP 404 if the given weblog is not indexed by Spinn3r.

See SourceFields for more documentation on response values.

# From Spinn3r Reference Client #

```
FeedClient client = new FeedClient();

Config config = new Config();
config.setVendor( "acme" );
        
client.setConfig( config );

Feed feed = client.status( "http://feedblog.org/feed" );
```