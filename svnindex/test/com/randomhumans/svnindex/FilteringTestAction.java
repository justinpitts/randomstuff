package com.randomhumans.svnindex;


import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.indexing.IFilter;


public class FilteringTestAction implements IFilter
{
    SortedSet<String> nameFilters = null;
    public FilteringTestAction(Set<String> filters)
    {
        nameFilters = new TreeSet<String>(filters);        
    }

    public boolean allow(String url, SVNDirEntry entry)
    {        
        boolean result = !nameFilters.contains(entry.getName())  && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder");        
        return result ;
    }

}
