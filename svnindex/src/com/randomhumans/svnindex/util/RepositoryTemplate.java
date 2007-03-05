
package com.randomhumans.svnindex.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.DefaultSVNRepositoryPool;
import org.tmatesoft.svn.core.wc.ISVNRepositoryPool;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryTemplate<T>
{
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(RepositoryTemplate.class);
    private static final ISVNRepositoryPool pool;
    static
    {
        try
        {
            log.info("Initializing repository factory for " + Configuration.getConfig().getPathToRepository());
            final URL repoURL = new URL(Configuration.getConfig().getPathToRepository());
            final String protocol = repoURL.getProtocol();
            if ("HTTP".equalsIgnoreCase(protocol) || "HTTPS".equalsIgnoreCase(protocol))
            {
                DAVRepositoryFactory.setup();
            }
            else if ("FILE".equalsIgnoreCase(protocol))
            {
                FSRepositoryFactory.setup();
            }
            else if ("SVN".equalsIgnoreCase(protocol) || "SVN+SSH".equalsIgnoreCase(protocol))
            {
                SVNRepositoryFactoryImpl.setup();
            } else {
                throw new MalformedURLException(repoURL.toString() + " is not a recognized Subversion URL.");
            }
        }
        catch (final MalformedURLException e)
        {
            RepositoryHelper.log.error("Unable to initialize repository factory", e);
        }
        final String user = Configuration.getConfig().getRepoUser();
        final String password = Configuration.getConfig().getRepoPassword();
        final ISVNAuthenticationManager auth = SVNWCUtil.createDefaultAuthenticationManager(user, password);
        pool = new DefaultSVNRepositoryPool(auth, null);
    }

    private SVNRepository getRepo(final String url) throws SVNException
    {
        return pool.createRepository(SVNURL.parseURIEncoded(url), true);
    }
    
    public T execute(final String repositoryUrl, final RepositoryAction<T> action) throws SVNException 
    {
        SVNRepository repo = null;
        try
        {
            repo = getRepo(repositoryUrl);
            return action.execute(repo);
        }
        finally
        {
            try
            {
                if (repo != null)
                {
                    repo.closeSession();
                }
            }
            catch (final SVNException e)
            {
                RepositoryTemplate.log.error("An error occured closing the repository driver:",e);
            }
        }        
    }

}
