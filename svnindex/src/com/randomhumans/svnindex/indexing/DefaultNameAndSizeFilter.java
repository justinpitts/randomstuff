
package com.randomhumans.svnindex.indexing;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tmatesoft.svn.core.SVNDirEntry;

public class DefaultNameAndSizeFilter implements IFilter
{
    SortedSet<String> nameFilters = null;

    static final long KB = 1024;

    static final long MB = KB * KB;

    int i = 0;
    public DefaultNameAndSizeFilter(Set<String> filters)
    {
        nameFilters = new TreeSet<String>(filters);
    }

    public boolean allow(String url, SVNDirEntry entry)
    {        
        boolean process = !nameFilters.contains(entry.getName())
                          && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder") && entry.getSize() < 2 * MB;
        if (process)
        {            
            DirectoryEntryThreadPool.queueEntry(url, entry);
        }
        return process;
    }
}
