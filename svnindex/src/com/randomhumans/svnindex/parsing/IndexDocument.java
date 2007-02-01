package com.randomhumans.svnindex.parsing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;

public class IndexDocument
{
    public static final String REVISION_FIELD = "REVISION";
    public static final String DATE_FIELD = "DATE";
    public static final String MESSAGE_FIELD = "MESSAGE";
    public static final String AUTHOR_FIELD = "AUTHOR";
    
    private long revision;    
    private String author;    
    private Date date;
    private String message;
    
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(IndexDocument.class);
    
    public Term getUniqueTerm()
    {
        return new Term(IndexDocument.REVISION_FIELD, Long.toString(this.getRevision()));
    }
    
    public static IndexDocument fromDocument(final Document doc)
    {
        try
        {
            final Date date = new SimpleDateFormat().parse(doc.get(IndexDocument.DATE_FIELD));
            final long revision = Long.parseLong(doc.get(IndexDocument.REVISION_FIELD));
            final String author = doc.get(IndexDocument.AUTHOR_FIELD);
            final String message = doc.get(IndexDocument.MESSAGE_FIELD);
            return new IndexDocument(revision, author, date, message);
        }
        catch (final ParseException e)
        {
            IndexDocument.log.error(e);
            return null;
        }             
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setAuthor(final String author)
    {
        this.author = author;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(final Date date)
    {
        this.date = date;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public long getRevision()
    {
        return this.revision;
    }

    public void setRevision(final long revision)
    {
        this.revision = revision;
    }
    
    public Document toDocument()
    {
        final Document doc = new Document();
        for(final Field f : this.getFields())
        {
            doc.add(f);
        }
        return doc;
    }
    
    protected List<Field> getFields()
    {
        final ArrayList<Field> fields = new ArrayList<Field>();        
        fields.add(new Field(IndexDocument.REVISION_FIELD, Long.toString(this.getRevision()), Store.YES, Index.UN_TOKENIZED));
        fields.add(new Field(IndexDocument.DATE_FIELD, new SimpleDateFormat().format(this.getDate()),Store.YES, Index.UN_TOKENIZED ));
        fields.add(new Field(IndexDocument.MESSAGE_FIELD, this.getMessage(), Store.YES, Index.TOKENIZED));
        fields.add(new Field(IndexDocument.AUTHOR_FIELD, this.getAuthor(), Store.YES, Index.UN_TOKENIZED));        
        return fields;
    }

    public IndexDocument(final long revision, final String author, final Date date, final String message)
    {
        super();
        this.revision = revision;
        this.author = author;
        this.date = date;
        this.message = message;
    }
        
}
