## Hot content ##

We spend most of our time optimizing for hot data.  Keep your client connected at all times and you'll be served from memory and the content should be served VERY fast.

## Parallel client ##

Update to the parallel client (>=3.2.0).  This solves the problem with slower networks, allows us to optimize parallel hardware on our end, and solves TCP congession control and TCP backoff issues found in the older client.

## Use protostreams ##

Our new protocol, [protostreams](Protostream.md) (>=3.3.0), is 2-4x faster than our current XML protocol.

## Command line and --save ##

Use --save and write XML or protostreams to disk as this is optimized for fetching content as fast as possible.

See CommandLineAccess for more information

## Make sure that you have enough bandwidth ##

Test your bandwidth.  You should ideally have well north of 5Mbit to Spinn3r.

See BandwidthRequirements for more information.

## Remote Filter ##

If there's a large chunk of data you don't need from Spinn3r and you can filter on a basic field, run with a remote filter which won't download the content.  For example, if you're a customer just using our Facebook support use a remote filter of:

> (eq publisher\_type 'SOCIAL\_MEDIA')

See [Filtering](Filtering.md) for more information.

## Monitor your client ##

If your clients falls behind, it should trigger alerts on your end.  Use a system like nagios or a script that sends you an SMS if your processing falls behind so you can fix it before it becomes a problem.