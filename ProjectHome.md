# Spinn3r API #

This projects implements client bindings to access the [Spinn3r](http://spinn3r.com) web service.

All of our drivers will be released under the Apache 2.0 license. The APL is a very liberal license and basically allows customers and researchers using the Spinn3r API to build whatever type of application they want on top of our platform without having to worry about legal and licensing implications.

# Overview #

The Spinn3r API is a firehose API of live weblog, microblog, and social media content and is fetched and updated it in near real time.

Users of the reference client run it from the command line as a daemon and write .protostream files to a directory on their hard drive.

Once the .protostream files are written to your local disk you can use a C#, perl, python, or C (including any language where protocol buffers are supported) client to read the protostream and index the data specific to your application.

## Documentation ##

See the [permalink API](PermalinkAPI.md) and our [wire protocol](Protostream.md) (named protostreams and based on [Protocol Buffers](http://code.google.com/p/protobuf)) and [Javadoc](http://spinn3r-client.googlecode.com/svn/trunk/javadoc/index.html)

## Permalink API ##

Spinn3r fetches the full HTML of every post published in the blogosphere and makes it available via the Permalink API.

## Comment API ##

We are currently beta testing an API for indexing comments published in the blogosphere.