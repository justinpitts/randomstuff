package com.randomhumans.svnindex.indexing;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class SVNRepoTreeWalker
{
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
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        } finally {
            try
            {
                repo.closeSession();
            }
            catch (SVNException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                e.printStackTrace();
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
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }        
    }

}
