package com.spinn3r.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.base.Function;
import com.google.inject.Provider;
import com.spinn3r.api.protobuf.ContentApi;
import com.spinn3r.io.ChainDecoder;
import com.spinn3r.io.Decoder;
import com.spinn3r.io.IterableDecoder;
import com.spinn3r.io.TranslateAndDecode;
import com.spinn3r.io.protostream.ProtoStreamDecoder;
import com.spinn3r.util.ThrowingFunction;

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
    
    private final Function<InputStream, Decoder<ContentApi.Entry>> INPUTSTREAM_TO_ENTRY =
        new Function<InputStream, Decoder<ContentApi.Entry>>() {
        
        @Override
        public Decoder<ContentApi.Entry> apply(InputStream input) {
            return EntryDecoderFactory.this.get(input);
        }
    };
    
    private static final ThrowingFunction<File, InputStream, IOException> FILE_TO_INPUTSTREAM = 
        new ThrowingFunction<File, InputStream, IOException>() {
        
        @Override
        public InputStream apply(File f) throws IOException {
            return new FileInputStream(f);
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
    
    public Decoder<ContentApi.Entry> get(Iterable<File> files) {
    	Decoder<File> fileDecoder = new IterableDecoder<File>(files);
        
        return get(TranslateAndDecode.newDecoder(fileDecoder, FILE_TO_INPUTSTREAM)); 
    }
        
    public Decoder<ContentApi.Entry> get(File[] files) {
        Decoder<File> fileDecoder = new IterableDecoder<File>(files);
        
        return get(TranslateAndDecode.newDecoder(fileDecoder, FILE_TO_INPUTSTREAM));
    }
    
    public Decoder<ContentApi.Entry> get(InputStream[] inputStreams) {
  
        return get(new IterableDecoder<InputStream>(inputStreams));
    }
    
    private Decoder<ContentApi.Entry> get(Decoder<? extends InputStream> inputStreamDecoder) {
        Decoder<Decoder<ContentApi.Entry>> decoderDecoder = 
            TranslateAndDecode.newDecoder(inputStreamDecoder, INPUTSTREAM_TO_ENTRY);
        
        return ChainDecoder.newDecoder(decoderDecoder);
   }

}
