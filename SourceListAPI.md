This API is obsolete and should not be used by new customers.

# source.list #

Endpoint: http://api.spinn3r.com/rss/source.list

API designed to help 3rd party crawlers tie into Spinn3r's ping stream and realtime polling and prioritization backend.

Returns an RSS feed with lists of weblogs that have either been found or discovered by Spinn3r or published after a given timestamp.

# Parameters: #

Optionally include only one of the following:

| published\_after | Unix timestamp or ISO 8601 timestamp for weblogs published after a specific time. |
|:-----------------|:----------------------------------------------------------------------------------|
| found\_after     | Unix timestamp or ISO 8601 timestamp for weblogs discovered by Spinn3r after a specific time. |

If neither of these are specified the default is `found_after=-1` (or all weblogs sorted by Spinn3r discovery time).

See GeneralParameters for additional documentation.

# Example Response #

```
<item>

<title>Cat and Girl</title>
<link>http://catandgirl.com</link>
<description>Signifying Nothing Tuesday and Friday</description>

<source:guid>cIqkKMlWG3Q</source:guid>
<source:resource>http://catandgirl.com</source:resource>
<source:link>http://catandgirl.com</source:link>
<source:date_found>2005-09-14T21:48:00Z</source:date_found>
<source:tier>2</source:tier>
<source:indegree>213</source:indegree>
<source:spam_probability>0.0</source:spam_probability>

<feed:guid>XuqVG9erE80</feed:guid>
<feed:resource>http://catandgirl.com/rss.php</feed:resource>
<feed:link>http://www.catandgirl.com/rss.php</feed:link>
<feed:resource_status>200</feed:resource_status>
<feed:channel_link>http://www.catandgirl.com/</feed:channel_link>
<feed:channel_guid>cIqkKMlWG3Q</feed:channel_guid>
<feed:channel_title>Cat and Girl</feed:channel_title>
<feed:channel_desc>Signifying Nothing Tuesday and Friday</feed:channel_desc>
<feed:date_found>2005-10-19T00:00:00Z</feed:date_found>
<feed:etag>&quot;121efdf-a3-f55a2dc0&quot;</feed:etag>

</item>
```

See SourceFields for more documentation on response values.

# From Spinn3r Reference Client #

```

        SourceListConfig config = new SourceListConfig();
        SourceListClient client = new SourceListClient();

        config.setVendor( "XXXX" );

        //start with an arbitrary date
        Date date = new Date( 1210661536159L );

        //We're looking to find new weblogs as we find them.  Alternatively, one
        //could use setPublishedAfter to detect when we find new URLs.
        config.setFoundAfter( date );

        //tell the client to use this specific config.
        client.setConfig( config );

        while( true ) {

            //connect to the network and fetch the next batch of results.
            client.fetch();

            //get the the results in a data structure that we can manipulate
            List<Source> results = client.getResults();

            //iterate over each source.
            for( Source source : results ) {

                System.out.printf( "source title:           %s\n", source.getTitle() );
                System.out.printf( "source description:     %s\n", source.getDescription() );

                System.out.printf( "source guid:            %s\n", source.getGuid() );
                System.out.printf( "source resource:        %s\n", source.getResource() );
                System.out.printf( "source resource status: %s\n", source.getResourceStatus() );
                System.out.printf( "source link:            %s\n", source.getLink() );
                System.out.printf( "source date_found:      %s\n", source.getDateFound() );
                System.out.printf( "source tier:            %s\n", source.getTier() );
                System.out.printf( "source indegree:        %s\n", source.getIndegree() );
                System.out.printf( "source disabled:        %s\n", source.getDisabled() );

                //get feed specific data.
                Feed feed = source.getFeed();

                System.out.printf( "feed guid:              %s\n", feed.getGuid() );
                System.out.printf( "feed resource:          %s\n", feed.getResource() );
                System.out.printf( "feed resource status:   %s\n", feed.getResourceStatus() );
                System.out.printf( "feed link:              %s\n", feed.getLink() );
                System.out.printf( "feed last published:    %s\n", feed.getLastPublished() );
                System.out.printf( "feed date found:        %s\n", feed.getDateFound() );
                System.out.printf( "feed title:             %s\n", feed.getChannelTitle() );
                System.out.printf( "feed link:              %s\n", feed.getChannelLink() );
                System.out.printf( "feed description:       %s\n", feed.getChannelDescription() );
                System.out.printf( "feed etag:              %s\n", feed.getEtag() );

                System.out.printf( "---\n" );

            }

            System.out.printf( "Last request URL: %s\n", client.getLastRequestURL() );
            System.out.printf( "Next request URL: %s\n", client.getNextRequestURL() );
            System.out.printf( "---\n" );

            //optionally we dan determine if there are no more resources by
            //detecting if we didn't find a full page.
            if ( config.getLimit() != results.size() )
                break;
            
        }

```