# source.validate #

Endpoint: http://api.spinn3r.com/rss/source.validate

Validates a given website or feed against the currently published RSS feed.  We
then test the conformance of Spinn3r to verify that source was indexed
correctly.

# Parameters #

| link | Link to a feed to which you'd like to obtain the status |
|:-----|:--------------------------------------------------------|

See GeneralParameters for additional documentation.

# Example Response #

```
<channel>        

<title>Spinn3r validate.</title>
<link>http://api.spinn3r.com</link>

<generator>Powered by Spinn3r</generator>

<source:accuracy>100.00</source:accuracy>

<item>
    
<title>Dem ticket: Smarmy and Smirky ‘08?</title>
<link>http://michellemalkin.com/2008/08/19/dem-ticket-smarmy-and-smirky-08/</link>
<atom:published>2008-08-19T16:35:49Z</atom:published>
<post:status>indexed</post:status>post:status>

</item>

<!-- ... more items here -->

</channel>
```

Returns HTTP 404 if the given weblog is not indexed by Spinn3r.

See SourceFields for more documentation on response values.

# From Spinn3r Reference Client #

```
```