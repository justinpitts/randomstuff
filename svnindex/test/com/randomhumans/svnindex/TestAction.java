
package com.randomhumans.svnindex;

import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.indexing.filters.IFilter;

public class TestAction implements IFilter<String,SVNDirEntry>
{
    int i = 0;

    public boolean allow(final String url, final SVNDirEntry entry)
    {
        this.i++;
        return true;
    }

    public int getI()
    {
        return this.i;
    }

}
