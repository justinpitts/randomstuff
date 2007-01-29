
package com.randomhumans.svnindex.indexing;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import com.randomhumans.svnindex.util.Configuration;

public class ContentIndexerThread implements Runnable
{
	static Log log = LogFactory.getLog(ContentIndexerThread.class);
    private static ExecutorService indexerPool = null;
    private static final BlockingQueue<Document> documentQueue = new LinkedBlockingQueue<Document>();
    private volatile static boolean signal = false;
        
    public synchronized static void queueDocument(final Document d) throws InterruptedException
    {        
        ContentIndexerThread.documentQueue.put(d);        
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
                doc = ContentIndexerThread.documentQueue.poll(1, TimeUnit.SECONDS);
                if (doc != null)
                {
                    iw.addDocument(doc);
                }
            }
            while (!ContentIndexerThread.signal);
            ContentIndexerThread.log.info("signal caught; draining queue");            
            doc = ContentIndexerThread.documentQueue.poll();
            while(doc != null)
            {
                ContentIndexerThread.log.debug("Queuesize: " + ContentIndexerThread.documentQueue.size());
                iw.addDocument(doc);
                doc = ContentIndexerThread.documentQueue.poll();
            }
        }
        catch (final IOException e)
        {            
        	ContentIndexerThread.log.error(e);            
        }
        catch (final InterruptedException e)
        {        
        	ContentIndexerThread.log.warn(e);            
        }
        finally
        {
            try
            {
                if (iw != null)
                {
                    ContentIndexerThread.log.debug("optimizing index");
                    iw.optimize();
                    iw.close();
                    ContentIndexerThread.log.debug("index closed");
                }
            }
            catch (final IOException e)
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
        catch (final IOException e)
        {
            ContentIndexerThread.log.error(e);
        }

        finally
        {
            try
            {
                iw.close();                
            }
            catch (final IOException e)
            {
                ContentIndexerThread.log.error(e);
            }
            finally
            {
                ;
            }
        }
        ContentIndexerThread.indexerPool = Executors.newSingleThreadExecutor();
        ContentIndexerThread.indexerPool.submit(new ContentIndexerThread());
    }

    public static void close()
    {
        ContentIndexerThread.signal = true;
        try
        {
            ContentIndexerThread.log.debug("waiting");
            ContentIndexerThread.indexerPool.shutdown();
            ContentIndexerThread.indexerPool.awaitTermination(60, TimeUnit.SECONDS);
            ContentIndexerThread.log.debug("wait complete");
        }
        catch (final InterruptedException e1)
        {
            ContentIndexerThread.log.error(e1);
        }
    }

}
