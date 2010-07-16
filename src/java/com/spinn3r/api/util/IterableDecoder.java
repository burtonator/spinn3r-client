package com.spinn3r.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class IterableDecoder<E> implements Decoder<E> {
    
    private final Iterator<? extends E> iterator;
    
    public IterableDecoder(E ... values) {
        this(Arrays.asList(values));
    }
    
    public IterableDecoder(Iterable<? extends E> iterable) {
        this(iterable.iterator());
    }
    
    public IterableDecoder(Iterator<? extends E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public void close() {
        // Do not support, does nothing
    }

    @Override
    public void mark(int readAheadLimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public E read() {
        if(iterator.hasNext())
            return iterator.next();
        else
            return null;
    }

    @Override
    public Collection<? extends E> read(int len) {
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
    public void reset() {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public long skip(long n) {
        E item;
        for(long i = 0; i < n; i++) {
            item = read();
            if(item == null)
                return i;
        }
        
        return n;
    }

}
