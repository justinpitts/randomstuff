
package com.randomhumans.svnindex.indexing.revision;

import java.io.IOException;
import java.util.HashSet;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import com.randomhumans.svnindex.indexing.IndexMetaDocument;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class LogEntryUpdateWalker
{
    HashSet<String> visitedUrls = new HashSet<String>();

    public void walk() throws IOException, SVNException
    {
        final IndexMetaDocument info = IndexMetaDocument.loadFromIndex();
        final long endRevision = info.getRevision() + 1;
        final long currentRevision = RepositoryHelper.getLatestRevision();

        for (long i = currentRevision; i > endRevision; i--)
        {
            final SVNLogEntry logEntry = RepositoryHelper.getLogEntry(i);
            for (final Object o : logEntry.getChangedPaths().keySet())
            {
                final String url = o.toString();
                if (!this.visitedUrls.contains(url))
                {
                 //   ContentIndexerThread.queueDocument(d)
                    this.visitedUrls.add(o.toString());
                }
            }
        }
    }

}
