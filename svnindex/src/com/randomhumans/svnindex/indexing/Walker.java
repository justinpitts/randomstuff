package com.randomhumans.svnindex.indexing;

import java.util.Calendar;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.randomhumans.svnindex.util.Configuration;


public class Walker
{
	private Walker() {}
	
    static Log log = LogFactory.getLog(Walker.class);

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ContentIndexer.init();
        TreeSet<String> t = new TreeSet<String>();
        t.add("tags");
        t.add("branches");
        t.add("oracleRetail");
        ISVNUrlAction ta = new ContentTokenizer(t);
        SVNRepoTreeWalker w = new SVNRepoTreeWalker();
        Long start = Calendar.getInstance().getTimeInMillis();
        w.map(Configuration.getConfig().getRepositoryURL(), ta);
        Long done = Calendar.getInstance().getTimeInMillis();
        log.info("index run complete");
        log.info(done-start);    
        ContentIndexer.close();
        ContentDocumentThread.shutdown();
        log.info("shutdown");      
    }
}
