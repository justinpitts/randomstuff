
package com.randomhumans.svnindex.indexing;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;

import com.randomhumans.svnindex.indexing.filters.DefaultNameAndSizeFilter;
import com.randomhumans.svnindex.indexing.filters.IFilter;
import com.randomhumans.svnindex.indexing.filters.NameSizeAndRevisionFilter;
import com.randomhumans.svnindex.indexing.tree.SVNRepoTreeWalker;
import com.randomhumans.svnindex.util.Configuration;

public class Walker
{
    private Walker()
    {}
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Walker.class);

    /**
     * @param args
     * @throws IOException
     * @throws SVNException
     */
    public static void main(final String[] args) throws IOException, SVNException
    {
        Walker.log.info(Configuration.getConfig().toString());        
        final IndexMetaDocument info = IndexMetaDocument.loadFromIndex();
        final boolean rebuild = ((args.length > 0) && args[0].equalsIgnoreCase("--REBUILD")) ;
        IFilter filter;
        if (rebuild)
        {
            filter = new DefaultNameAndSizeFilter(Configuration.getConfig().getIgnoredNames());
            Walker.log.debug("rebuild mode");
        }
        else
        {
            filter = new NameSizeAndRevisionFilter(Configuration.getConfig().getIgnoredNames(), info.getRevision() + 1);
            Walker.log.debug("update mode");
        }
        ContentIndexerThread.init(rebuild);
        final SVNRepoTreeWalker walker = new SVNRepoTreeWalker();
        info.setRevision(walker.map(Configuration.getConfig().getRepositoryURL(), filter));
        DirectoryEntryThreadPool.shutdown();
        ContentIndexerThread.shutdown();
        info.save();
        Walker.log.info("shutdown complete");
    }
}
