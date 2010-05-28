package com.spinn3r.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.google.inject.Provider;
import com.spinn3r.api.protobuf.ContentApi;
import com.spinn3r.api.util.Decoder;
import com.spinn3r.api.util.ProtoStreamDecoder;

public class EntryDecoderFactory {
    
    public static EntryDecoderFactory newFactory() {
        return new EntryDecoderFactory();
    }
    
    private EntryDecoderFactory() {
        
    }

    private static final Provider<ContentApi.Entry.Builder> BUILDER_PROVIDER = new Provider<ContentApi.Entry.Builder> () {
        public ContentApi.Entry.Builder get() {
            return ContentApi.Entry.newBuilder();
        }
    };
    
    public Decoder<ContentApi.Entry> get(String path) throws FileNotFoundException {
        return get(new File(path));
    }
    
    public Decoder<ContentApi.Entry> get(File inputStream) throws FileNotFoundException {
        
        return get(new FileInputStream(inputStream));
    }
    
    public Decoder<ContentApi.Entry> get(InputStream inputStream) {
        return ProtoStreamDecoder.newProtoStreamDecoder(inputStream, BUILDER_PROVIDER);
    }

}
