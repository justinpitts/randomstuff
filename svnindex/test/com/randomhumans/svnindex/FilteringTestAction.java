package com.randomhumans.svnindex;


import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.indexing.filters.IFilter;


public class FilteringTestAction implements IFilter<String,SVNDirEntry>
{
    SortedSet<String> nameFilters = null;
    public FilteringTestAction(final Set<String> filters)
    {
        this.nameFilters = new TreeSet<String>(filters);        
    }

    public boolean allow(final String url, final SVNDirEntry entry)
    {        
        final boolean result = !this.nameFilters.contains(entry.getName())  && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder");        
        return result ;
    }

}
