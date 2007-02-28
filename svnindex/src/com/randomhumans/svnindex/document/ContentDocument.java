
package com.randomhumans.svnindex.document;

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

public class ContentDocument extends IndexDocument implements UniqueDocument
{

    public ContentDocument(final long revision, final String author, final Date date,

    final String url, final Reader content)
    {
        super(revision, author, date);
        this.url = url;
        this.content = content;
    }

    public static ContentDocument fromDocument(final Document doc)
    {
        final IndexDocument id = IndexDocument.fromDocument(doc);
        final String url = doc.get(ContentDocument.URL_FIELD);
        return new ContentDocument(id.getRevision(), id.getAuthor(), id.getDate(), url, null);
    }

    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ContentDocument.class);

    public static final String CONTENT_FIELD = "CONTENT";

    public static final String URL_FIELD = "URL";

    private String url;

    private Reader content;

    @Override
    public Term getUniqueTerm()
    {
        return new Term(ContentDocument.URL_FIELD, this.getUrl());
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
        catch (final IOException e)
        {
            ContentDocument.log.error(e);
        }
    }
}
