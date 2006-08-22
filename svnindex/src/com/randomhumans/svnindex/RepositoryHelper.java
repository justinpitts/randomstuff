
package com.randomhumans.svnindex;

import java.util.ArrayList;
import java.util.Collection;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryHelper
{
    public static SVNRepository getRepo() throws SVNException
    {
        return getRepo(getRepoURL());
    }
    
    public static SVNRepository getRepo(String url) throws SVNException
    {
        DAVRepositoryFactory.setup();
        SVNRepository repository = null;
        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        ISVNAuthenticationManager authManager = SVNWCUtil
                                                         .createDefaultAuthenticationManager(
                                                                                             Configuration
                                                                                                          .getConfig()
                                                                                                          .getRepoUser(),
                                                                                             Configuration
                                                                                                          .getConfig()
                                                                                                          .getRepoPassword());
        repository.setAuthenticationManager(authManager);
        return repository;
        
    }

    public static long getLatestRevision() throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = getRepo();
            return repo.getLatestRevision();
        }
        finally
        {
            repo.closeSession();
        }
    }
    
    public static SVNNodeKind checkPath(String path) throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = getRepo();
            return repo.checkPath(path, -1);
        }
        finally
        {
            repo.closeSession();
        }
        
    }

    public static SVNDirEntry getInfo(String path) throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = getRepo();
            return repo.info(path, -1);
        }
        finally
        {
            repo.closeSession();
        }
    }

    public static Collection<SVNDirEntry> dir(String url) throws SVNException
    {
        SVNRepository repo = null;
        try
        {
            repo = getRepo();
            return dir(repo, url);
        }
        finally
        {
            repo.closeSession();
        }        
    }

    public static Collection<SVNDirEntry> dir(SVNRepository repo, String url) throws SVNException
    {
        ArrayList<SVNDirEntry> entries = new ArrayList<SVNDirEntry>();
        repo.getDir(url, repo.getLatestRevision(), null, entries);
        return entries;
    }

    private static String getRepoURL()
    {
        return Configuration.getConfig().getRepositoryURL();
    }

    @SuppressWarnings("unchecked")
    public static SVNLogEntry getLogEntry(long revisionNumber)
    {
        SVNRepository repo = null;
        SVNLogEntry logEntry = null;
        try
        {
            try
            {
                repo = getRepo();
                ArrayList<SVNLogEntry> logs;
                try
                {
                    logs = new ArrayList(repo.log(new String[] {""}, null, revisionNumber, revisionNumber, true, true));
                    logEntry = logs.get(0);

                }
                catch (SVNException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            catch (SVNException e)
            {
                // TODO Auto-generated catch block
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
            catch (SVNException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
