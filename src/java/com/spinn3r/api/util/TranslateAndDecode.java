package com.spinn3r.api.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Function;
import com.spinn3r.api.util.Decoder;

public class TranslateAndDecode<T, E> implements Decoder<E> {
    
    private final Decoder<? extends T> input;
    private final ThrowingFunction<? super T, ? extends E> translator;
    
    public static <T,E> TranslateAndDecode<T,E> newDecoder(Decoder<? extends T> input,
            Function<? super T, ? extends E> translator) {
        return new TranslateAndDecode<T,E>(input, ThrowingFunctionWrapper.wrap(translator));
    } 
    
    public static <T,E> TranslateAndDecode<T,E> newDecoder(Decoder<? extends T> input,
            ThrowingFunction<? super T, ? extends E> translator) {
        return new TranslateAndDecode<T,E>(input, translator);
    }
    
    
    TranslateAndDecode(Decoder<? extends T> input,
            ThrowingFunction<? super T, ? extends E> translator) {
        this.input = input;
        this.translator = translator;
    }
  

    @Override
    public int available() throws IOException {
        return input.available();
    }

    @Override
    public void close() throws IOException {
        input.close();
        
    }

    @Override
    public void mark(int readlimit) throws IOException {
        input.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return input.markSupported();
    }

    @Override
    public E read() throws IOException {
        T obj = input.read();
        
        // If we've hit the end, don't translate. Just return null
        if(obj == null)
            return null;
        
        try {
            return translator.apply(obj);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public Collection<? extends E> read(int len) throws IOException {
        Collection<? extends T> subResults = input.read(len);
        Collection<E> retval = new ArrayList<E>(subResults.size());
        
        for(T obj : subResults) {
            try {
                retval.add(translator.apply(obj));
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
        
        return retval;
    }

    @Override
    public void reset() throws IOException {
        input.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return input.skip(n);
    }

}
