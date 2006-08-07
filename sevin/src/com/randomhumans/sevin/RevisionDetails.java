package com.randomhumans.sevin;

import org.tmatesoft.svn.core.SVNLogEntry;

import com.randomhumans.svnindex.RepositoryHelper;

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
        log = RepositoryHelper.getLogEntry(revision);
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
