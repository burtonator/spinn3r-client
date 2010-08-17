package com.spinn3r.api.util;

public interface ThrowingFunction<F, T> {
    public T apply(F a) throws Exception;
}
