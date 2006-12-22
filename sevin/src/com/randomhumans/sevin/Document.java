package com.randomhumans.sevin;

import com.randomhumans.svnindex.indexing.ContentDocument;;

public class Document
{
    private org.apache.lucene.document.Document doc;
    
    public Document(org.apache.lucene.document.Document source)
    {
        doc = source;
    }
  
    public String getAuthor()
    {
        return doc.get(ContentDocument.AUTHOR);
    }
    
    public String  getMessage()
    {
        String message = doc.get(ContentDocument.MESSAGE) + "";
        return message.length() > 50 ? message.substring(0,47) + "..." : message;            
    }
    
    public String getDate()
    {
        return doc.get(ContentDocument.DATE);
    }
    
    public String getRevision()
    {
        return doc.get(ContentDocument.REVISION);
    }
    
    public String getURL()
    {
    	return doc.get(ContentDocument.URL);
    }
    
    public String getMD5()
    {
    	return doc.get(ContentDocument.MD5);
    }
}
