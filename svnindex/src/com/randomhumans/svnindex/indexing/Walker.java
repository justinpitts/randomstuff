
package com.randomhumans.svnindex.indexing;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

import com.randomhumans.svnindex.document.IndexMetaDocument;
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
        final IndexMetaDocument info = getMetaDoc();
        final boolean rebuild = ((args.length > 0) && args[0].equalsIgnoreCase("--REBUILD")) ;
        IFilter<String, SVNDirEntry> filter;        
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
        updateIndex(info, rebuild, filter);
    }

    private static void updateIndex(final IndexMetaDocument info, final boolean rebuild, IFilter<String, SVNDirEntry> filter) throws IOException, SVNException
    {
        ContentIndexerThread.init(rebuild);
        final SVNRepoTreeWalker walker = new SVNRepoTreeWalker();
        info.setRevision(walker.map(Configuration.getConfig().getFolders(),filter));
        DirectoryEntryThreadPool.shutdown();
        ContentIndexerThread.shutdown();
        info.save(Configuration.getConfig().getIndexLocation());
        Walker.log.info("shutdown complete");
    }

    private static IndexMetaDocument getMetaDoc() throws IOException
    {
        final IndexMetaDocument info = IndexMetaDocument.loadFromIndex(Configuration.getConfig().getIndexLocation());
        return info;
    }
    
    public static void rebuild()
    {
        IFilter<String, SVNDirEntry> filter = new DefaultNameAndSizeFilter(Configuration.getConfig().getIgnoredNames());
        Walker.log.debug("rebuild mode");
        try
        {
            updateIndex(getMetaDoc(), true, filter);
        }
        catch (IOException e)
        {
            log.error(e,e);
        }
        catch (SVNException e)
        {
            log.error(e,e);
         }       
    }
    
    public static void update() throws IOException
    {
        IndexMetaDocument info = getMetaDoc();
        IFilter<String, SVNDirEntry> filter = new NameSizeAndRevisionFilter(Configuration.getConfig().getIgnoredNames(), info.getRevision() + 1);
        Walker.log.debug("update mode");
        try
        {
            updateIndex(info, false, filter);
        }
        catch (IOException e)
        {
            log.error(e,e);
        }
        catch (SVNException e)
        {
            log.error(e,e);
         }       
        
    }
}
