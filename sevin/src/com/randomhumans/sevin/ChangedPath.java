package com.randomhumans.sevin;

import java.util.Map;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;

import com.randomhumans.svnindex.Configuration;

public class ChangedPath
{
    public enum ChangeTypes {Modified, Added, Deleted, Replaced};
    
    public static ChangedPath[] buildChangedPaths(SVNLogEntry log)
    {
        Map cp = log.getChangedPaths();
        ChangedPath[] paths = new ChangedPath[cp.size()];
        int i = 0;
        for(Object o : cp.values())
        {
            SVNLogEntryPath logEntryPath = (SVNLogEntryPath)o;
            paths[i++] = new ChangedPath(logEntryPath);
        }
        return paths;        
    }
    
    private SVNLogEntryPath source;
    public ChangedPath(SVNLogEntryPath p) 
    {
        source = p;        
    }
    
    
    public long getCopyRevision()
    {
           return source.getCopyRevision();
    }
    
    public String getPath()
    {        
        return Configuration.getConfig().getRepositoryURL() + source.getPath();
    }
    
    public ChangeTypes getType()
    {
        switch (source.getType())
        {
            case 'M': return ChangeTypes.Modified;
            case 'A': return ChangeTypes.Added;
            case 'D': return ChangeTypes.Deleted;
            case 'R': return ChangeTypes.Replaced;
            default : return ChangeTypes.Added;
        }
    }
    
    

}
