package com.spinn3r.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Provider;
import com.spinn3r.api.protobuf.ContentApi.Link;
import com.spinn3r.api.protobuf.ContentApi.Source;

public class TestProtoStreamByteArrayEncoder {
    
    private final ByteArrayOutputStream writeToStream = new ByteArrayOutputStream();
    
    private final Link testObj = Link.newBuilder()
        .setHref("http://blahblah/doh.html")
        .setRel("doh.html")
        .setMimeType("text/html")
        .build();

    private final Source testSource = Source.newBuilder()
        .addLink(testObj)
        .build();
    
    private final Source tripleSource = Source.newBuilder()
        .addLink(testObj)
        .addLink(testObj)
        .addLink(testObj)
        .build();
     
    
    @Test
    public void oneObject() throws IOException {
        ProtoStreamBytesEncoder encoder1 = ProtoStreamBytesEncoder.newEncoder(writeToStream, Link.class);
        encoder1.write(testObj.toByteArray());
        ProtoStreamDecoder<Link> decoder1 = ProtoStreamDecoder.newDecoder(new ByteArrayInputStream(writeToStream.toByteArray()),
                new Provider<Link.Builder> () {

                    @Override
                    public Link.Builder get() {
                        return Link.newBuilder();
                    }
            
        });

        Assert.assertEquals(testObj, decoder1.read());
        Assert.assertNull(decoder1.read());
    }
    
    @Test
    public void nestedObj() throws IOException {
        ProtoStreamBytesEncoder encoder1 = ProtoStreamBytesEncoder.newEncoder(writeToStream, Source.class);
        encoder1.write(testSource.toByteArray());
        ProtoStreamDecoder<Source> decoder1 = ProtoStreamDecoder.newDecoder(new ByteArrayInputStream(writeToStream.toByteArray()),
                new Provider<Source.Builder> () {

                    @Override
                    public Source.Builder get() {
                        return Source.newBuilder();
                    }
            
        });
        Assert.assertEquals(testSource, decoder1.read());
        Assert.assertNull(decoder1.read());
    }
    
    @Test
    public void twoObjects() throws IOException {
        ProtoStreamBytesEncoder encoder1 = ProtoStreamBytesEncoder.newEncoder(writeToStream, Source.class);
        encoder1.write(testSource.toByteArray());
        encoder1.write(tripleSource.toByteArray());
        ProtoStreamDecoder<Source> decoder1 = ProtoStreamDecoder.newDecoder(new ByteArrayInputStream(writeToStream.toByteArray()),
                new Provider<Source.Builder> () {

                    @Override
                    public Source.Builder get() {
                        return Source.newBuilder();
                    }
            
        });
        Assert.assertEquals(testSource, decoder1.read());
        Assert.assertEquals(tripleSource, decoder1.read());
        Assert.assertNull(decoder1.read());
    }
    
}
