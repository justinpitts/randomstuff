package com.randomhumans.svnindex;

import org.tmatesoft.svn.core.SVNDirEntry;

public interface ISVNUrlAction
{
    public void execute(String url, SVNDirEntry entry);
}
