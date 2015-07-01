# source.register #

Endpoint: http://api.spinn3r.com/rss/source.register

Registers a new weblog within Spinn3r.

Returns HTTP 200 on success.  The resource will be available within Spinn3r within the hour (almost certainly sooner).

Returns HTTP 403 on failure.  This can happen if you attempt to register a URL which is not a weblog or an information resource.  For example, registering a URL to a .pdf would generate this response.

By default when a client registers a new weblog it is not available for indexing by other Spinn3r users.  If a second user registers the exact same source then it's made available for use by others.

# Parameters #

## Required ##

| link | The link to the weblog you'd like to register |
|:-----|:----------------------------------------------|

## Optional ##

| feed | The feed to use if one is not specified |
|:-----|:----------------------------------------|
| publisher\_type | For new sources, the publisher type to be used |

See GeneralParameters for additional documentation.

### Specifying feeds ###

It's not always possible for Spinn3r to detect the feed for a source.  Starting with 3.0.57 (released on 3-15-09) we now record the the feed specified in a registration and use this as a hint for our RSS autodiscovery code.  If we are unable to find a feed for the source, and the feed specified by the registrar passes some basic tests (uses the same domain, has content, parses, etc) then we will use the one they specify.

Note that in the future if the source correctly enables RSS/Atom autodiscovery that we will prefer this feed and not the vendor suggested feed.

This will only work for new sources or sources registered by the same vendor within Spinn3r.

Please check that the domain being registered resolves properly and is an actively maintained site. If there isn't an rss feed please let us know. Currently, manual intervention is needed to scrape sites that don't have a feed. If the site is hosted on Wordpress or Typepad, you may have a problem registering the domain. Please let us know if you can't register it, and we'll investigate.

### Publisher Type ###

Spinn3r supports classification of source types into WEBLOG, MAINSTREAM\_NEWS, etc.

New sources can be assigned a publisher type from the vendor.

Currently we support the following types:

WEBLOG, MAINSTREAM\_NEWS, CLASSIFIED, FORUM, REVIEW

See Spinn3rFields for a full list of accepted types.

# Example #

See SourceFields for more documentation on response values.

# From Spinn3r Reference Client #

```
SourceClient client = new SourceClient();

Config config = new Config();
config.setVendor( "acme" );
        
client.setConfig( config );

client.register( "http://example.com/weblog" );

```