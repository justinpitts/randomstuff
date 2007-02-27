package com.randomhumans.svnindex.indexing.filters;

import org.tmatesoft.svn.core.SVNDirEntry;

 
/**
 * @author jpitts
 * 
 */

public interface IFilter
{
    //define a action to be performed on a repository directory entry. 
    // returns true iff the entry's children should be processed
    public boolean allow(String url, SVNDirEntry entry);
}
