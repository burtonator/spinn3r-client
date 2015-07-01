# Introduction #

Protostream is the new format for downloading data from the Spinn3r service. Protostreams allow you to download Spinn3r data with less latency and to spend less time parsing the payload.

# Reading a protostream #

The spinn3r-client provides a library for reading protostream data. `Decoder` is the class for reading payload objects from a protostream file.

For Spinn3r data, each payload object is of type `ContentApi.Entry`. You can get the next payload object from the protostream by using the `Decoder.read()` call. That call returns `null` when there are no more payload objects left in the stream.

Code example:

```

import com.spinn3r.api.EntryDecoderFactory;
import com.spinn3r.api.util.Decoder;
import com.spinn3r.api.protobuf.ContentApi;

EntryDecoderFactory factory = EntryDecoderFactory.newFactory();
Decoder<ContentApi.Entry> decoder = null;

try {

    decoder = factory.get("/path/to/file.protostream");

    ContentApi.Entry obj;

    while((obj = decoder.read()) != null) {
        /* Do something with object */ 
    }

} finally {
    decoder.close();
}

```

# The payload object #

The Spinn3r payload object is  a protocol buffer object. You can find the object specification in [spinn3rApi.proto](http://code.google.com/p/spinn3r-client/source/browse/src/proto/spinn3rApi.proto). The file also contains descriptions of the fields in the payload object. If you need to see more information about how the object specification translate into a java object, see the [protocol buffer documentation](http://code.google.com/apis/protocolbuffers/docs/overview.html).

# Protostream format #

A protostream is a stream of protocol buffer messages, encoded on the wire as length prefixed varints according to the Google [protocol buffer specification](http://code.google.com/apis/protocolbuffers/docs/encoding.html). The stream has three parts: a header, the payload, and a tail marker.

A `ProtoStreamHeader` message sits at the beginning of the stream. It contains a protostream version string and any application specific stream headers that the application wanted to include.

The header is followed by the payload. There may be zero or more payload messages in the payload. Each payload message is proceeded by a `ProtoStreamDelimiter` message with a `delimiter_type` set to `ENTRY`.

After the payload, a protostream end is marked with a  `ProtoStreamDelimiter` message with the `delimiter_type` set to `END`.

# Migrating from Legacy XML API #

See [APIToContentAPI](APIToContentAPI.md) and [XMLToProtostream](XMLToProtostream.md) for a field mapping the legacy API and encoding to protostreams.