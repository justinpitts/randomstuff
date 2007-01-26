
package com.randomhumans.svnindex;

import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.indexing.IFilter;

public class TestAction implements IFilter
{
    int i = 0;

    public boolean allow(final String url, final SVNDirEntry entry)
    {
        i++;
        return true;
    }

    public int getI()
    {
        return i;
    }

}
