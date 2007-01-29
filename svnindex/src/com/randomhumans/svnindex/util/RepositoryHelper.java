
package com.randomhumans.svnindex.util;

import java.util.ArrayList;
import java.util.Collection;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.DefaultSVNRepositoryPool;
import org.tmatesoft.svn.core.wc.ISVNRepositoryPool;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryHelper
{
    private static ISVNRepositoryPool pool = null;
    static
    {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();

        final String user = Configuration.getConfig().getRepoUser();
        final String password = Configuration.getConfig().getRepoPassword();
        final ISVNAuthenticationManager auth = SVNWCUtil.createDefaultAuthenticationManager(user, password);
        RepositoryHelper.pool = new DefaultSVNRepositoryPool(auth, null);

    }

    public static SVNRepository getRepo() throws SVNException
    {
        return RepositoryHelper.getRepo(RepositoryHelper.getRepoURL());
    }

    public static SVNRepository getRepo(final String url) throws SVNException
    {
        return RepositoryHelper.pool.createRepository(SVNURL.parseURIEncoded(url), true);
    }

    public static long getLatestRevision() throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo();
            return repo.getLatestRevision();
        }
        finally
        {
            repo.closeSession();
        }
    }

    public static SVNNodeKind checkPath(final String path) throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo();
            return repo.checkPath(path, -1);
        }
        finally
        {
            repo.closeSession();
        }

    }

    public static SVNDirEntry getInfo(final String path) throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo();
            return repo.info(path, -1);
        }
        finally
        {
            repo.closeSession();
        }
    }

    public static Collection<SVNDirEntry> dir(final String url) throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo();
            return RepositoryHelper.dir(repo, url);
        }
        finally
        {
            repo.closeSession();
        }
    }

    public static Collection<SVNDirEntry> dir(final SVNRepository repo, final String url) throws SVNException
    {
        return RepositoryHelper.dir(repo, url, repo.getLatestRevision());
    }

    public static Collection<SVNDirEntry> dir(final SVNRepository repo, final String url, final long revision) throws SVNException
    {
        final ArrayList<SVNDirEntry> entries = new ArrayList<SVNDirEntry>();
        repo.getDir(url, revision, null, entries);
        return entries;
    }

    private static String getRepoURL()
    {
        return Configuration.getConfig().getRepositoryURL();
    }

    @SuppressWarnings("unchecked")
    public static SVNLogEntry getLogEntry(final long revisionNumber)
    {
        SVNRepository repo = null;
        SVNLogEntry logEntry = null;
        try
        {
            try
            {
                repo = RepositoryHelper.getRepo();
                ArrayList<SVNLogEntry> logs;
                try
                {
                    logs = new ArrayList(repo.log(new String[] {""}, null, revisionNumber, revisionNumber, true, true));
                    logEntry = logs.get(0);

                }
                catch (final SVNException e)
                {
                    e.printStackTrace();
                }

            }
            catch (final SVNException e)
            {
                e.printStackTrace();
            }
            return logEntry;
        }
        finally
        {
            try
            {
                repo.closeSession();
            }
            catch (final SVNException e)
            {
                e.printStackTrace();
            }
        }
    }

}
