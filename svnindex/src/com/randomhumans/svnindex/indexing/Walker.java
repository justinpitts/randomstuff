
package com.randomhumans.svnindex.indexing;

import java.util.Calendar;
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
        log.info(Configuration.getConfig().toString());
        ContentIndexerThread.init();
        final IFilter urlAction = new DefaultNameAndSizeFilter(Configuration.getConfig().getIgnoredNames());
        final SVNRepoTreeWalker walker = new SVNRepoTreeWalker();
        final Long start = Calendar.getInstance().getTimeInMillis();
        walker.map(Configuration.getConfig().getRepositoryURL(), urlAction);
        final Long done = Calendar.getInstance().getTimeInMillis();
        Walker.log.info("repository walk completed in " + (done - start) + " ms");
        ContentIndexerThread.close();
        DirectoryEntryThreadPool.shutdown();
        Walker.log.info("shutdown complete");
    }
}
