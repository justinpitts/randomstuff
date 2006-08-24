package com.randomhumans.svnindex;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

public class SVNRepoTreeWalker
{
    public void map(String url, ISVNUrlAction action)
    {
        SVNRepository repo = null;
        try
        {
            repo = RepositoryHelper.getRepo(url);
            map("", action, repo);            
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
    
    private void map(String url, ISVNUrlAction action, SVNRepository repo)
    {
        try
        {
            for(SVNDirEntry entry : RepositoryHelper.dir(repo, url))
            {
                action.execute(url + "/" + entry.getName(), entry);
                if (entry.getKind() == SVNNodeKind.DIR)
                {                    
                    map( (url.equals("") ?  entry.getName() : url + "/" + entry.getName()), action, repo);                        
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
