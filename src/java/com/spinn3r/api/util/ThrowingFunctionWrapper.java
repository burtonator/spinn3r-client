package com.spinn3r.api.util;

import com.google.common.base.Function;

class ThrowingFunctionWrapper<F, T> implements ThrowingFunction<F, T> {
    
    private final Function<F,T> wrapped;
    
    public static <F,T> ThrowingFunctionWrapper<F,T> wrap(Function<F,T> wrapped) {
        return new ThrowingFunctionWrapper<F,T>(wrapped);
    }
    
    private ThrowingFunctionWrapper(Function<F,T> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public T apply(F a) {
        return wrapped.apply(a);
    }

}
