package com.randomhumans.svnindex.indexing;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.lucene.document.Document;
import org.tmatesoft.svn.core.SVNDirEntry;

public class ContentDocumentThread implements Runnable
{

    private static ExecutorService indexerPool = Executors.newFixedThreadPool(20);
    String docUrl = "";
    SVNDirEntry dirEntry = null;
    
    public static void queueEntry(String url, SVNDirEntry entry)
    {
        indexerPool.submit(new ContentDocumentThread(url, entry));
    }
    
    public ContentDocumentThread(String url, SVNDirEntry entry)
    {
        docUrl = url;
        dirEntry = entry;        
    }
    
    public void run()
    {
        try
        {            
            Document doc = ContentDocument.createDocument(dirEntry, docUrl);
            ContentIndexer.queueDocument(doc);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }        
    }
    
    public static void shutdown()
    {
        indexerPool.shutdown();
    }

}
