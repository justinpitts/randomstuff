package com.randomhumans.sevin;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import com.randomhumans.svnindex.util.RepositoryHelper;

public class RevisionDetails
{
    public RevisionDetails() {}
    
    
    SVNLogEntry log = null;

    private long revision;

    public long getRevision()
    {
        return revision;
    }

    public void setRevision(long revision)
    {
        this.revision = revision;
        try {
			log = RepositoryHelper.getLogEntry(revision);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String getAuthor()
    {
        return log.getAuthor();
    }
    
    public String getDate()
    {
        return log.getDate().toString();
    }
    
    public String getMessage()
    {
        return log.getMessage();
    }
    
    public ChangedPath[] getChangedPaths()
    {
        return ChangedPath.buildChangedPaths(log);
    }   
    
}
