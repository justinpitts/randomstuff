package svnindex;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.tmatesoft.svn.core.SVNLogEntry;

public class RevisionDocument implements Runnable
{
	static Document createRevisionDocument(long revisionNumber )
	{
		Document doc = new Document();		
		SVNLogEntry logEntry = RepositoryHelper.getLogEntry(revisionNumber);	
		
		
		doc.add(new Field("author", logEntry.getAuthor(), Field.Store.YES, Field.Index.UN_TOKENIZED));
		doc.add(new Field("date", logEntry.getDate().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
		doc.add(new Field("message", logEntry.getMessage(), Field.Store.YES, Field.Index.TOKENIZED));
		doc.add(new Field("revision", Long.toString(logEntry.getRevision()), Field.Store.YES, Field.Index.UN_TOKENIZED));	
		
		return doc;
	}

	public void run()
	{
		// TODO Auto-generated method stub
		
	}

}
