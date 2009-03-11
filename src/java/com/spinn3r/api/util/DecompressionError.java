package com.spinn3r.api.util;


public class DecompressionError extends RuntimeException {
    public DecompressionError ( Exception t ) {
        super( t );
    }
}