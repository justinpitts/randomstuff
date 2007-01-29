
package com.randomhumans.svnindex.indexing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.tmatesoft.svn.core.SVNDirEntry;

import com.randomhumans.svnindex.parsing.DirectoryEntryDocumentGenerator;

public class DirectoryEntryThreadPool implements Runnable
{
    static Log log = LogFactory.getLog(DirectoryEntryThreadPool.class);

    private static final ExecutorService indexerPool = Executors.newFixedThreadPool(20);

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
            final Document doc = DirectoryEntryDocumentGenerator.createDocument(this.dirEntry, this.docUrl);
            ContentIndexerThread.queueDocument(doc);
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
