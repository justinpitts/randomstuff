
package com.randomhumans.svnindex.indexing;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import com.randomhumans.svnindex.util.Configuration;

public class ContentIndexer implements Runnable
{
    private static ExecutorService indexerPool = Executors.newSingleThreadExecutor();

    Document doc;

    public static void queueDocument(Document d)
    {        
        indexerPool.submit(new ContentIndexer(d));
    }

    public ContentIndexer(Document d)
    {
        doc = d;
    }

    public void run()
    {        
        IndexWriter iw = null;
        try
        {
            iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), false);
            iw.addDocument(doc);
        }
        catch (IOException e)
        {
            queueDocument(doc);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (iw != null)
                {
                    iw.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void init()
    {
        IndexWriter iw = null;
        try
        {
            iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                iw.close();                
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                ;
            }
        }
    }

    public static void optimizeIndex()
    {
        indexerPool.shutdown();
        try
        {
            indexerPool.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        IndexWriter iw = null;
        try
        {
            iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), true);
            iw.optimize();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                iw.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
