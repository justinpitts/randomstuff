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
    public void map(String url, ISVNUrlAction action)
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo(url);
            long currentRevision = repo.getLatestRevision();            
            
            map("", action, repo, currentRevision);            
        }
        catch (SVNException e)
        {
            log.error(e);            
        } finally {
            try
            {
                repo.closeSession();
            }
            catch (SVNException e)
            {
            	log.error(e);
            }
        }        
    }
    
    private void map(String url, ISVNUrlAction action, SVNRepository repo, long revision)
    {
        try
        {
            for(SVNDirEntry entry : RepositoryHelper.dir(repo, url))
            {
                boolean process =  action.execute(url + "/" + entry.getName(), entry);
                if ( process && entry.getKind() == SVNNodeKind.DIR )
                {                    
                    map( (url.equals("") ?  entry.getName() : url + "/" + entry.getName()), action, repo, revision);                        
                }
            }
        }
        catch (SVNException e)
        {
            log.error(e);
        }        
    }

}
