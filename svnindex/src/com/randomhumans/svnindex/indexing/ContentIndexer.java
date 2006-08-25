
package com.randomhumans.svnindex.indexing;

import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.randomhumans.svnindex.util.Configuration;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class ContentIndexer 
{
    public static void main(String[] args) throws IOException, SVNException
    {
        ContentIndexer indexer = new ContentIndexer();
        indexer.index();
    }

    IndexWriter iw = null;

    public void index() throws IOException, SVNException
    {
        iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), true);
        try
        {
            SVNRepository repo = RepositoryHelper.getRepo();
            try
            {
            index("", repo);
            } finally {
                repo.closeSession();
            }
        }
        finally
        {
            try
            {
                iw.optimize();
            }
            finally
            {
                iw.close();
            }
        }
    }

    public void index(String path, SVNRepository repo)
    {
        try
        {
            for(SVNDirEntry entry : RepositoryHelper.dir(repo, path))
            {
                try
                {
                    if (entry.getKind() == SVNNodeKind.FILE)
                    {
                        iw.addDocument(ContentDocument.createDocument(entry, path + "/" + entry.getName()));
                    }
                    else if (entry.getKind() == SVNNodeKind.DIR)
                    {
                        index( (path.equals("") ?  entry.getName() : path + "/" + entry.getName()), repo);                        
                    }
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block -- Finish Me
                    e.printStackTrace();
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
