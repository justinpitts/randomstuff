
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
            System.out.println("signal caught; draining queue");

            
            doc = documentQueue.poll();
            while(doc != null)
            {
                System.out.println( documentQueue.size());
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
                    System.out.println("optimizing index");
                    iw.optimize();
                    iw.close();
                    System.out.println("index closed");
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
        indexerPool = Executors.newSingleThreadExecutor();
        indexerPool.submit(new ContentIndexer());
    }

    public static void close()
    {
        signal = true;
        try
        {
            System.out.println("waiting");
            indexerPool.shutdown();
            indexerPool.awaitTermination(60, TimeUnit.SECONDS);
            System.out.println("waited");
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
    }

}
