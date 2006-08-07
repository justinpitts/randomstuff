package com.randomhumans.sevin;

import com.randomhumans.svnindex.RevisionDocument;

public class Document
{
    private org.apache.lucene.document.Document doc;
    
    public Document(org.apache.lucene.document.Document source)
    {
        doc = source;
    }
  
    public String getAuthor()
    {
        return doc.get(RevisionDocument.AUTHOR_FIELDNAME);
    }
    
    public String  getMessage()
    {
        String message = doc.get(RevisionDocument.MESSAGE_FIELDNAME) + "";
        return message.length() > 50 ? message.substring(0,47) + "..." : message;            
    }
    
    public String getDate()
    {
        return doc.get(RevisionDocument.DATE_FIELDNAME);
    }
    
    public String getRevision()
    {
        return doc.get(RevisionDocument.REVISION_FIELDNAME);
    }
}
