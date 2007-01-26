
package com.randomhumans.svnindex.indexing;

import java.util.Calendar;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.randomhumans.svnindex.util.Configuration;

public class Walker
{
    private Walker()
    {}

    static Log log = LogFactory.getLog(Walker.class);

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ContentIndexerThread.init();
        TreeSet<String> ignoreList = new TreeSet<String>();
        ignoreList.add("tags");
        ignoreList.add("branches");
        ignoreList.add("oracleRetail");
        IFilter urlAction = new DefaultNameAndSizeFilter(ignoreList);
        SVNRepoTreeWalker walker = new SVNRepoTreeWalker();
        Long start = Calendar.getInstance().getTimeInMillis();
        walker.map(Configuration.loadFromSystemProperties().getRepositoryURL(), urlAction);
        Long done = Calendar.getInstance().getTimeInMillis();
        log.info("index run complete");
        log.info(done - start);
        ContentIndexerThread.close();
        DirectoryEntryThreadPool.shutdown();
        log.info("shutdown");
    }
}
