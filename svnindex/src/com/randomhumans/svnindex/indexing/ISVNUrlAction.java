package com.randomhumans.svnindex.indexing;

import org.tmatesoft.svn.core.SVNDirEntry;

 
/**
 * @author jpitts
 * 
 */

public interface ISVNUrlAction
{
    //define a action to be performed on a repository directory entry. 
    // returns true iff the entry's children should be processed
    public boolean execute(String url, SVNDirEntry entry);
}
