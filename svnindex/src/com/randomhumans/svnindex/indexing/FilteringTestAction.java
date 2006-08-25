package com.randomhumans.svnindex.indexing;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tmatesoft.svn.core.SVNDirEntry;


public class FilteringTestAction implements ISVNUrlAction
{
    SortedSet<String> nameFilters = null;
    public FilteringTestAction(Set<String> filters)
    {
        nameFilters = new TreeSet<String>(filters);        
    }

    public boolean execute(String url, SVNDirEntry entry)
    {        
        System.out.println(url);
        boolean result = !nameFilters.contains(entry.getName())  && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder");        
        return result ;
    }

}
