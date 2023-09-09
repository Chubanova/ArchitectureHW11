package com.chubanova.ioc;

public interface Initializer<T> {
    void initialize(T obj);
}
