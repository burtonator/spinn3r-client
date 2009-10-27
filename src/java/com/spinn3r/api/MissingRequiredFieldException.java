package com.spinn3r.api;


public class MissingRequiredFieldException extends RuntimeException {
    public MissingRequiredFieldException ( String message ) {
        super( message );
    }
}