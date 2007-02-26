package com.randomhumans.svnindex.indexing;

public interface Predicate<T>
{
    public boolean eval(T t);
}
