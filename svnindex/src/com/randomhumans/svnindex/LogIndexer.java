package com.randomhumans.svnindex;

import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

public class LogIndexer
{	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SVNException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException
	{		
        if (args.length > 0)            
        {
            if (args[0].equalsIgnoreCase("add"))
            {
                addRevisions();
                return;
            } else if (args[0].equalsIgnoreCase("rebuild"))
            {
                rebuild();
                return;
            }
        }          
        printUsage();
	}

    public static void addRevisions() throws SVNException, IOException
    {        
         
    }

    private static void printUsage()
    {
        System.out.println("LogIndexer add|rebuild");        
    }

    private static void index(long startRevision, long endRevision, boolean create) throws IOException
    {
        IndexWriter iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), true);
        try
        {
            for (long i = startRevision; i < endRevision; i ++)
            {   
                iw.addDocument(RevisionDocument.createRevisionDocument(i));
                System.out.println(Long.toString(i));               
            }
        } finally
        {
            iw.optimize();
            iw.close();
        }
        
    }
    public static void rebuild() throws IOException, SVNException
    {
        index(1, RepositoryHelper.getLatestRevision(), true);
    }
    
        
}
    
    
