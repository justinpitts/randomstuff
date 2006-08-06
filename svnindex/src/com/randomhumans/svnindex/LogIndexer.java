package com.randomhumans.svnindex;

import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.tmatesoft.svn.core.SVNException;

public class LogIndexer
{	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SVNException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException
	{		
		IndexWriter iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), new StandardAnalyzer(), true);
		try
		{
			for (long i = RepositoryHelper.getLatestRevision(); i > 1; i -= 1)
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
}
