package com.randomhumans.svnindex.indexing;

import org.tmatesoft.svn.core.SVNDirEntry;

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
