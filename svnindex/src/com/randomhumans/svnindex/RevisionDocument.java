package com.randomhumans.svnindex;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.tmatesoft.svn.core.SVNLogEntry;

public class RevisionDocument 
{
    public static final String REVISION_FIELDNAME = "revision";
    public static final String MESSAGE_FIELDNAME = "message";
    public static final String AUTHOR_FIELDNAME = "author";
    public static final String DATE_FIELDNAME = "date";
    
	static Document createRevisionDocument(long revisionNumber )
	{
		Document doc = new Document();		
		SVNLogEntry logEntry = RepositoryHelper.getLogEntry(revisionNumber);	
		doc.add(new Field(AUTHOR_FIELDNAME, logEntry.getAuthor() + "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		doc.add(new Field(DATE_FIELDNAME, logEntry.getDate().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
		doc.add(new Field(MESSAGE_FIELDNAME, logEntry.getMessage() + "", Field.Store.YES, Field.Index.TOKENIZED));
		doc.add(new Field(REVISION_FIELDNAME, Long.toString(logEntry.getRevision()), Field.Store.YES, Field.Index.UN_TOKENIZED));	
		return doc;
	}
}
