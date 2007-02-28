package com.randomhumans.svnindex.indexing.filters;

 
/**
 * @author jpitts
 * 
 */

public interface IFilter<T,U>
{
    //define a action to be performed on a repository directory entry. 
    // returns true iff the entry's children should be processed
    public boolean allow(T t, U u);
}
