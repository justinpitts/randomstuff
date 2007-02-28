
package com.randomhumans.svnindex.indexing.revision;

import java.io.IOException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import com.randomhumans.svnindex.indexing.filters.IFilter;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class LogEntryUpdateWalker
{

    public void walk(final long lastRevisionIndexed, final long currentRevision, final IFilter<Long, SVNLogEntry> action)
        throws IOException,
        SVNException
    {
        final long endRevision = lastRevisionIndexed + 1;
        for (long i = currentRevision; i > endRevision; i--)
        {
            action.allow(i, RepositoryHelper.getLogEntry(i));
        }
    }
}
