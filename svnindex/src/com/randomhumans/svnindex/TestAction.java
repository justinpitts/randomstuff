package com.randomhumans.svnindex;

import org.tmatesoft.svn.core.SVNDirEntry;

public class TestAction implements ISVNUrlAction
{
    int i =0;

    public void execute(String url, SVNDirEntry entry)
    {
        i++;
        System.out.println(i);
    }

    public int getI()
    {
        return i;
    }


}
