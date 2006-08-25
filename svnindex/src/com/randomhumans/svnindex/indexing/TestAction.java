package com.randomhumans.svnindex.indexing;

import org.tmatesoft.svn.core.SVNDirEntry;


public class TestAction implements ISVNUrlAction
{
    int i =0;

    public boolean execute(String url, SVNDirEntry entry)
    {
        i++;
        System.out.println(i);
        if(i %100 == 0)
            System.out.println(url);
        return true;
    }

    public int getI()
    {
        return i;
    }


}
