
package com.randomhumans.svnindex.indexing;

import java.util.SortedSet;
import java.util.TreeSet;

import org.tmatesoft.svn.core.SVNDirEntry;

public class DefaultNameAndSizeFilter implements IFilter
{
    SortedSet<String> nameFilters = null;

    static final long KB = 1024;

    static final long MB = DefaultNameAndSizeFilter.KB * DefaultNameAndSizeFilter.KB;

    public DefaultNameAndSizeFilter(final String[] filters)
    {
        this.nameFilters = new TreeSet<String>();
        for (final String s : filters)
        {
            this.nameFilters.add(s);
        }        
    }

    public boolean allow(final String url, final SVNDirEntry entry)
    {
        final boolean process = !this.nameFilters.contains(entry.getName())
            && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder")
            && (entry.getSize() < 2 * DefaultNameAndSizeFilter.MB);
        if (process)
        {
            DirectoryEntryThreadPool.queueEntry(url, entry);
        }
        return process;
    }
}
