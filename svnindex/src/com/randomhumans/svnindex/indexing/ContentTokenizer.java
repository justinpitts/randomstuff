
package com.randomhumans.svnindex.indexing;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tmatesoft.svn.core.SVNDirEntry;

public class ContentTokenizer implements ISVNUrlAction
{
    SortedSet<String> nameFilters = null;

    static long KB = 1024;

    static long MB = KB * KB;

    int i = 0;
    public ContentTokenizer(Set<String> filters)
    {
        nameFilters = new TreeSet<String>(filters);
    }

    public boolean execute(String url, SVNDirEntry entry)
    {
        boolean process = !nameFilters.contains(entry.getName())
                          && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder") && entry.getSize() < 2 * MB;
        if (process)
        {            
            ContentDocumentThread.queueEntry(url, entry);
            if (i++ % 100 == 0)
            {
                System.out.println(url);
            }
        }
        return process;
    }
}
