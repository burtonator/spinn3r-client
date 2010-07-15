package com.spinn3r.api.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ChainDecoder<E> implements Decoder<E> {
    
    private final Decoder<Decoder<E>> decoderDecoder;
    private Decoder<E> currentDecoder;
    
    public static <T> ChainDecoder<T> newDecoder(Decoder<Decoder<T>> decoderDecoder) {
        return new ChainDecoder<T>(decoderDecoder);
    }
    
    private ChainDecoder(Decoder<Decoder<E>> decoderDecoder) {
        this.decoderDecoder = decoderDecoder;
    }

    @Override
    public int available() throws IOException {
        if(currentDecoder != null)
            return currentDecoder.available();
        else
            return 0;
    }

    @Override
    public void close() throws IOException {
        // We don't support closing, so have no effect
    }

    @Override
    public void mark(int readAheadLimit) throws IOException {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public boolean markSupported() {
        return false;
    }
    

    @Override
    public E read() throws IOException {
        if(currentDecoder == null) {
            currentDecoder = decoderDecoder.read();
            if(currentDecoder == null)
                return null;
        }
        
        E item = currentDecoder.read();
        if(item == null) {
            currentDecoder = decoderDecoder.read();
            if(currentDecoder == null)
                item = null;
            else
                item = currentDecoder.read();
        }
        
        return item;
    }

    @Override
    public Collection<? extends E> read(int len) throws IOException {
        Collection<E> retval = new ArrayList<E>(len);
        
        for(int i = 0; i < len; i++) {
            E item = read();
            if(item == null)
                break;
            retval.add(item);
        }
        
        return retval;
    }

    @Override
    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) throws IOException {
        E item;
        for(long i = 0; i < n; i++) {
            item = read();
            if(item == null)
                return i;
        }
        
        return n;
    }
    
    

}
