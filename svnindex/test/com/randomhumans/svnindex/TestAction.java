package com.randomhumans.svnindex;

import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.indexing.ISVNUrlAction;


public class TestAction implements ISVNUrlAction
{
    int i =0;

    public boolean execute(final String url, final SVNDirEntry entry)
    {
        i++;
        return true;
    }

    public int getI()
    {
        return i;
    }


}
