# How can I block MICROBLOG, SOCIAL\_MEDIA or other publisher\_type specific data #

You can use the following remote filters:

```
(not (contains link 'example.com'))
```

```
(not (eq publisher_type 'MICROBLOG'))
```

# Does the Spinn3r API provide ranking data ? #

Spinn3r includes both tier and indegree values in the API responses from our
API.

The indegree value is the number of unique inbound links from other sources in
our index.

In a spam free index indegree will approach the value of a spam free trust
metric.  The difficult of course is determining the spam free index which
Spinn3r is very good at accomplishing since our index is very low in spam.

This value is also used to compute a 'tier' for sources which is the rank
position divided by 1000.

We're currently working on a new ranking algorithm and the current tier/indegree
values are frozen.  This is actually advantageous for a number of our customers
who don't want the rank positions to change.

We're going to incorporate this into future versions of the API so we can serve
rank vectors for a given timestamp.

# Is it possible to convert protocol buffers to XML? #

Right now the Spinn3r API servers support both XML and protocol buffer output.  Eventually the legacy XML output will be deprecated and removed in favor of an updated schema called protobuff-xml which should only be used by customers who MUST use XML.

All other customers should use protocol buffers directly as they are MUCH easier to support and maintain.

If you have an existing protocol buffer file that you want to convert to XML you can look at the `com.spinn3r.api.ProtoDump` file in the Spinn3r distribution.

This will show you how to parse the protocol buffer and you can then print the fields directly and convert to your own (or our) XML format.

# How accurate is your chrome removal / content extract / template reduction ? #

Our current template reduction algorithm is 80-90% accurate.

It was designed for search engines and larger stories which have at least 5k worth of text in the body of the story.

It is possible that the algorithm can drop the first or last lines of text in a story and potentially include large boilerplate text if this is included in a sidebar.

We're also working on a next generation algorithm that has a bit more accuracy with the trade off being additional hardware resources on our end.

# Are publication times for stories reliable? #

The publication time for an article within Spinn3r is obtained from the RSS and Atom feed's dc:created and atom:published RSS elements.

Publication times can be skewed due to a number of variables including clock time on the origin server, incorrect clock time on the machine publishing the story into their CMS, incorrect timezone, etc.

We include the robot timestamp for this purpose.  If we're visiting the site on a regular basis, we can provide a timestamp with much higher resolution and accuracy for algorithms that can accept a smaller 1 hour offset from the actual publication time.

# At what granularity is the tiering applied? per domain? per blog? (e.g. can the local news feed versus national news feed from a newspaper have different tierings?) #

Per source.  A source could be a weblog, mainstream news site, forum site, etc.  A source is a unique URL that publishes frequently, generally has an RSS feed, and would be used by a human to visit.  For example, example.com could be a source but so could example.com/weblog.

# How can I measure the performance of Spinn3r? #

You can run the command line manually and after 1 minute it will print a performance metric.

For example:

```
API performance:          22.0445883834666403    24.717169034656752    -1.0
```

This prints performance number in 1, 5, and 15 minute intervals.  -1 means that there is insufficient information to compute the current performance value.

```
wget http://api.spinn3r.com/bandwidth.test
```

You should verify that you're seeing at least 10Mbit/s to Spinn3r.