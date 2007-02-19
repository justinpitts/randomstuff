
package com.randomhumans.svnindex.indexing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.parsing.ContentDocument;
import com.randomhumans.svnindex.parsing.ContentDocumentGenerator;
import com.randomhumans.svnindex.util.Configuration;

public class DirectoryEntryThreadPool implements Runnable
{
    static Log log = LogFactory.getLog(DirectoryEntryThreadPool.class);

    private static final ExecutorService indexerPool = Executors.newFixedThreadPool(Configuration.getConfig().getDirectoryEntryThreadPoolPoolSize());

    String docUrl = "";

    SVNDirEntry dirEntry = null;

    public static void queueEntry(final String url, final SVNDirEntry entry)
    {
        DirectoryEntryThreadPool.indexerPool.submit(new DirectoryEntryThreadPool(url, entry));
    }

    public DirectoryEntryThreadPool(final String url, final SVNDirEntry entry)
    {
        this.docUrl = url;
        this.dirEntry = entry;
    }

    public void run()
    {
        DirectoryEntryThreadPool.log.debug(this.docUrl);
        try
        {
            final ContentDocument doc = ContentDocumentGenerator.createDocument(this.dirEntry, this.docUrl);
            if (doc != null)
            {
                ContentIndexerThread.queueDocument(doc);
            }
        }
        catch (final InterruptedException e)
        {
            DirectoryEntryThreadPool.log.error(e);
        }
    }

    public static void shutdown()
    {
        DirectoryEntryThreadPool.indexerPool.shutdown();
    }

}
