
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
    public static void main(final String[] args)
    {
        ContentIndexerThread.init();
        final TreeSet<String> ignoreList = new TreeSet<String>();
        ignoreList.add("tags");
        ignoreList.add("branches");
        ignoreList.add("oracleRetail");
        final IFilter urlAction = new DefaultNameAndSizeFilter(ignoreList);
        final SVNRepoTreeWalker walker = new SVNRepoTreeWalker();
        final Long start = Calendar.getInstance().getTimeInMillis();
        walker.map(Configuration.loadFromSystemProperties().getRepositoryURL(), urlAction);
        final Long done = Calendar.getInstance().getTimeInMillis();
        Walker.log.info("index run complete");
        Walker.log.info(done - start);
        ContentIndexerThread.close();
        DirectoryEntryThreadPool.shutdown();
        Walker.log.info("shutdown");
    }
}
