
package com.randomhumans.svnindex.indexing.tree;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import com.randomhumans.svnindex.indexing.filters.IFilter;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class SVNRepoTreeWalker
{
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(SVNRepoTreeWalker.class);

    public long map(String[] folders, final IFilter<String, SVNDirEntry> action) throws SVNException
    {
        final long currentRevision = RepositoryHelper.getLatestRevision();
        for(String folder : folders)
        {
            SVNRepoTreeWalker.this.map(folder, action, currentRevision);
        }
        return currentRevision;
    }
    public long map(String folder, final IFilter<String, SVNDirEntry> action) throws SVNException
    {        
        final long currentRevision = RepositoryHelper.getLatestRevision();
        SVNRepoTreeWalker.this.map(folder, action, currentRevision);
        return currentRevision;
    }

    private void map(final String path, final IFilter<String, SVNDirEntry> action, final long revision)        throws SVNException
    {        
        for (final SVNDirEntry entry : RepositoryHelper.dir(path, revision))
        {            
            String newPath = formatEntryPath(path, entry);
            final boolean process = action.allow(path, entry);
            if (process && (entry.getKind() == SVNNodeKind.DIR))
            {
                this.map(newPath, action, revision);
            }
        }
    }
        
    private String formatEntryPath(final String folderUrl, final SVNDirEntry entry)
    {
        String s = folderUrl.equals("") ? entry.getName() : folderUrl + (folderUrl.endsWith("/") ? "" : "/") + entry.getName();        
        return s;
    }
}
