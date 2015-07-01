# Introduction #

Spinn3r is a bandwidth intensive application.  In order to use the firehose/delta API you should have excess bandwidth which will allow you to catch up quickly in case you ever fall behind.

This can happen in the normal course of business due to database upgrades, datacenter migrations, network outages, etc.

# Throughput Testing #

We've setup a 50MB document on the API servers that host Spinn3r.

Run the following:

```
wget  http://api.spinn3r.com/bandwidth.test?vendor=XXXXXX
```

which will yield:

```
> wget  http://api.spinn3r.com/bandwidth.test
--2012-03-20 15:51:52--  http://api.spinn3r.com/bandwidth.test
Resolving api.spinn3r.com... 173.193.206.252, 208.43.104.2
Connecting to api.spinn3r.com|173.193.206.252|:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 52428800 (50M) [text/plain]
Saving to: `bandwidth.test'
```

Wget measures in MB/s so just multiply by eight(8) to determine your effective bandwidth.

# Breakdown #

At 150k posts per hour, and an average of 50k per post (including raw HTML, content extract, etc, ) the firehose/delta API would require about 5Mbit to keep up with real time.

|**bandwidth**|**throughput**||
|:------------|:-------------|:|
|5Mbit        |1x            |_unsupported_.  Raw spinn3r throughput.|
|10Mbit       |2x            |minimum supported bandwidth|
|20Mbit       |4x            ||
|40Mbit       |8x            |recommended|
|60Mbit       |16x           |ideal|