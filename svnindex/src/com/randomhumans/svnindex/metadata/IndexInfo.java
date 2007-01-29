package com.randomhumans.svnindex.metadata;

import java.util.Calendar;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import com.randomhumans.svnindex.util.Configuration;

public class IndexInfo
{

    long revision;
    
    public long getRevision()
    {
        return this.revision;
    }
    public void setRevision(final long revision)
    {
        this.revision = revision;
    }
    
    public IndexInfo()
    {
        
    }
    public Document toDocument()
    {        
        final Document doc = new Document();
        doc.add(new Field("RepositoryURL", Configuration.getConfig().getRepositoryURL(), Store.YES, Index.UN_TOKENIZED));
        doc.add(new Field("IndexLocation", Configuration.getConfig().getIndexLocation(), Store.YES, Index.UN_TOKENIZED));
        doc.add(new Field("LastModified", Calendar.getInstance().toString(), Store.YES, Index.UN_TOKENIZED));
        doc.add(new Field("Revision", Long.toString(this.revision), Store.YES, Index.UN_TOKENIZED));
        return doc;
    }
}
