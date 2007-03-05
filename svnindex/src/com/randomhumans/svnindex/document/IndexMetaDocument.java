
package com.randomhumans.svnindex.document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexModifier;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.tmatesoft.svn.core.SVNException;

import com.randomhumans.svnindex.util.RepositoryHelper;

public class IndexMetaDocument implements UniqueDocument
{
    private static final String LASTMODIFIED = "LASTMODIFIED";

    private static final String REVISION = "REVISION";

    public static String UUID = "AAA9C74F-B5D3-435a-B8B4-3D12826851DB";

    static Log log = LogFactory.getLog(IndexMetaDocument.class);

    public static IndexMetaDocument loadFromIndex(final String indexindexPath) throws IOException
    {
        try
        {
            final IndexReader ir = IndexReader.open(indexindexPath);
            try
            {
                final Term t = new Term(IndexMetaDocument.UUID, IndexMetaDocument.UUID);
                final TermDocs td = ir.termDocs(t);
                if (td.next())
                {
                    return new IndexMetaDocument(ir.document(td.doc()));
                }
                return new IndexMetaDocument();
            }
            finally
            {
                ir.close();
            }
        }
        catch (final FileNotFoundException f)
        {
            return new IndexMetaDocument();
        }
    }

    Date lastModified;

    long revision = -1;

    public IndexMetaDocument()
    {

    }

    protected IndexMetaDocument(final Document doc)
    {
        assert (doc.get(IndexMetaDocument.UUID).equals(IndexMetaDocument.UUID));
        final String rev = doc.get(IndexMetaDocument.REVISION);
        try
        {
            this.revision = Long.parseLong(rev);
        }
        catch (final NumberFormatException variableDeclaratorInstance)
        {
            this.revision = -1;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat();
        try
        {
            this.lastModified = sdf.parse(doc.get(IndexMetaDocument.LASTMODIFIED));
        }
        catch (final ParseException e)
        {
            IndexMetaDocument.log.warn(e);
            this.lastModified = Calendar.getInstance().getTime();
        }
    }

    private Field createField(final String name, final String value)
    {
        return new Field(name, value, Store.YES, Index.UN_TOKENIZED);
    }

    public long getRevision()
    {
        return this.revision;
    }

    public void setRevision(final long revision)
    {
        if (this.revision != revision)
        {
            this.revision = revision;
            this.lastModified = Calendar.getInstance().getTime();
        }
    }

    public Document toDocument()
    {
        final Document doc = new Document();
        doc.add(this.createField(IndexMetaDocument.UUID, IndexMetaDocument.UUID));
        doc.add(this.createField(IndexMetaDocument.LASTMODIFIED, new SimpleDateFormat().format(this.lastModified)));
        doc.add(this.createField(IndexMetaDocument.REVISION, Long.toString(this.revision)));
        return doc;
    }

    public void save(final String indexLocation) throws IOException
    {
        final IndexModifier index = new IndexModifier(indexLocation, new StandardAnalyzer(), false);
        try
        {
            final Term t = new Term(IndexMetaDocument.UUID, IndexMetaDocument.UUID);
            index.deleteDocuments(t);
            index.addDocument(this.toDocument());
        }
        finally
        {
            index.close();
        }
    }

    public boolean isCurrentWithRepository() throws SVNException
    {
        return this.getRevision() == RepositoryHelper.getLatestRevision();
    }

    public Date getLastModified()
    {
        return this.lastModified;
    }

    public Term getUniqueTerm()
    {
        return new Term(IndexMetaDocument.UUID, IndexMetaDocument.UUID);
    }

}
