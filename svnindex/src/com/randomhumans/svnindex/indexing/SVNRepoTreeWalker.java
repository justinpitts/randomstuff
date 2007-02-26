
package com.randomhumans.svnindex.indexing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class SVNRepoTreeWalker
{
    Log log = LogFactory.getLog(Walker.class);

    public long map(final String url, final IFilter action)
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo(url);
            final long currentRevision = repo.getLatestRevision();
            log.debug("mapping");
            this.map("", action, repo, currentRevision);
            log.debug("done");
            
            return currentRevision;
        }
        catch (final SVNException e)
        {
            this.log.error(e);
            return -1;
        }
        finally
        {
            try
            {
                repo.closeSession();
            }
            catch (final SVNException e)
            {
                this.log.error(e);
            }
        }
    }

    private void map(final String url, final IFilter action, final SVNRepository repo, final long revision)
    {
        try
        {
            for (final SVNDirEntry entry : RepositoryHelper.dir(repo, url))
            {
                final boolean process = action.allow(url + "/" + entry.getName(), entry);
                if (process && (entry.getKind() == SVNNodeKind.DIR))
                {
                    this.map((url.equals("") ? entry.getName() : url + "/" + entry.getName()), action, repo, revision);
                }
            }
        }
        catch (final SVNException e)
        {
            this.log.error(e);
        }
    }

}
