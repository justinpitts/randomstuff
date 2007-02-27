package com.randomhumans.svnindex.indexing.filters;

import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.indexing.DirectoryEntryThreadPool;

public class NameSizeAndRevisionFilter extends DefaultNameAndSizeFilter implements IFilter
{

    long minimumRevision = 0;
    public NameSizeAndRevisionFilter(final String[] filters, final long minimumRevision)
    {
        super(filters);
        this.minimumRevision = minimumRevision;
    }
    
    @Override
    public boolean allow(final String url, final SVNDirEntry entry)
    {
        final boolean process = !this.nameFilters.contains(entry.getName())
        && !entry.getAuthor().equalsIgnoreCase("nextgenbuilder")
        && (entry.getSize() < 2 * DefaultNameAndSizeFilter.MB)
        && (entry.getRevision() >= this.minimumRevision);
    if (process)
    {
        DirectoryEntryThreadPool.queueEntry(url, entry);
    }
    return process;
    }

}
