
package com.randomhumans.svnindex.indexing;

import java.io.IOException;
import java.util.HashSet;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import com.randomhumans.svnindex.util.RepositoryHelper;

public class LogEntryUpdateWalker
{
    HashSet<String> visitedUrls = new HashSet<String>();

    public void walk() throws IOException, SVNException
    {
        IndexInfo info = IndexInfo.loadFromIndex();
        long endRevision = info.getRevision() + 1;
        long currentRevision = RepositoryHelper.getLatestRevision();

        for (long i = currentRevision; i > endRevision; i--)
        {
            SVNLogEntry logEntry = RepositoryHelper.getLogEntry(i);
            for (Object o : logEntry.getChangedPaths().keySet())
            {
                String url = o.toString();
                if (!visitedUrls.contains(url))
                {
                 //   ContentIndexerThread.queueDocument(d)
                    visitedUrls.add(o.toString());
                }
            }
        }
    }

}
