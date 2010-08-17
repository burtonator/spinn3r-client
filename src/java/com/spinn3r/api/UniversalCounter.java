package com.spinn3r.api;

class UniversalCounter {
    private long value;
    
    UniversalCounter() {
        this(0);
    }
    
    UniversalCounter(long initialValue) {
        this.value = initialValue;
    }
    
    public synchronized long next() {
        return value++;
    }
}
