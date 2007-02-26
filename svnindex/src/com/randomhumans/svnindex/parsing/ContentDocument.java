
package com.randomhumans.svnindex.parsing;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;

public class ContentDocument extends IndexDocument
{

    public ContentDocument(final long revision, final String author, final Date date, final String message, final String url, final String md5Hash, final Reader content)
    {
        super(revision, author, date, message);
        this.url = url;
        this.md5Hash = md5Hash;
        this.content = content;
    }

    public static ContentDocument fromDocument(final Document doc)
    {
        final IndexDocument id = IndexDocument.fromDocument(doc);
        final String url = doc.get(ContentDocument.URL_FIELD);
        final String md5 = doc.get(ContentDocument.MD5HASH_FIELD);        
        return new ContentDocument(id.getRevision(), id.getAuthor(), id.getDate(), id.getMessage(), url, md5, null);
    }
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ContentDocument.class);

    public static final String CONTENT_FIELD = "CONTENT";

    public static final String URL_FIELD = "URL";

    public static final String MD5HASH_FIELD = "MD5HASH";

    private String url;

    private String md5Hash;

    private Reader content;

    @Override
    public Term getUniqueTerm()
    {
        return new Term(ContentDocument.URL_FIELD, this.getUrl());
    }

    public String getMd5Hash()
    {
        return this.md5Hash;
    }

    public void setMd5Hash(final String md5Hash)
    {
        this.md5Hash = md5Hash;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(final String url)
    {
        this.url = url;
    }

    @Override
    protected List<Field> getFields()
    {
        final ArrayList<Field> fields = new ArrayList<Field>(super.getFields());
        fields.add(new Field(ContentDocument.CONTENT_FIELD, this.content));
        fields.add(new Field(ContentDocument.URL_FIELD, this.getUrl(), Store.YES, Index.NO));
        fields.add(new Field(ContentDocument.MD5HASH_FIELD, this.getMd5Hash(), Store.YES, Index.UN_TOKENIZED));
        return fields;
    }

    public Reader getContent()
    {
        return this.content;
    }

    public void setContent(final Reader content)
    {
        this.content = content;
    }
    
    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return this.getUrl();
    }
    
    @Override
    public void close()
    {        
        super.close();
        try
        {
            this.getContent().close();
        }
        catch (IOException e)
        {
            log.error(e);
        }
    }
}
