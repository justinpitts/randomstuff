
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
            log.info("signal caught; draining queue");            
            doc = documentQueue.poll();
            while(doc != null)
            {
                log.debug("Queuesize: " + documentQueue.size());
                iw.addDocument(doc);
                doc = documentQueue.poll();
            }
        }
        catch (IOException e)
        {            
        	log.error(e);            
        }
        catch (InterruptedException e)
        {        
        	log.warn(e);            
        }
        finally
        {
            try
            {
                if (iw != null)
                {
                    log.debug("optimizing index");
                    iw.optimize();
                    iw.close();
                    log.debug("index closed");
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
            log.error(e);
        }

        finally
        {
            try
            {
                iw.close();                
            }
            catch (IOException e)
            {
                log.error(e);
            }
            finally
            {
                ;
            }
        }
        indexerPool = Executors.newSingleThreadExecutor();
        indexerPool.submit(new ContentIndexerThread());
    }

    public static void close()
    {
        signal = true;
        try
        {
            log.debug("waiting");
            indexerPool.shutdown();
            indexerPool.awaitTermination(60, TimeUnit.SECONDS);
            log.debug("wait complete");
        }
        catch (InterruptedException e1)
        {
            log.error(e1);
        }
    }

}
