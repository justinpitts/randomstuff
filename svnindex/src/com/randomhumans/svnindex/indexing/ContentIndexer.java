
package com.randomhumans.svnindex.indexing;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import com.randomhumans.svnindex.util.Configuration;

public class ContentIndexer implements Runnable
{
    private static ExecutorService indexerPool = null;
    private static BlockingQueue<Document> documentQueue = new LinkedBlockingQueue<Document>();
    private volatile static boolean signal = false;
    
        
    public synchronized static void queueDocument(Document d) throws InterruptedException
    {        
        if (indexerPool == null)
        {
            indexerPool = Executors.newSingleThreadExecutor();
            indexerPool.submit(new ContentIndexer());
        }
        documentQueue.put(d);        
    }

   
    public void run()
    {        
        Document doc = null;
        IndexWriter iw = null;
        try
        {
            iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), false);
            do
            {
                doc = documentQueue.poll(1, TimeUnit.SECONDS);
                if (doc != null)
                {
                    iw.addDocument(doc);
                }
            }
            while (!signal);
            doc = documentQueue.poll();
            while(doc != null)
            {
                iw.addDocument(doc);
                doc = documentQueue.poll();
            }
            
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {        
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
        signal = true;
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
