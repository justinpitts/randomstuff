
package com.randomhumans.svnindex.indexing;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.randomhumans.svnindex.parsing.IndexDocument;
import com.randomhumans.svnindex.util.Configuration;

public class IndexHelper
{
    
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(IndexHelper.class);

    public static void updateIndexDoc(final IndexDocument d) throws IOException
    {
        IndexHelper.deleteTermDocs(d.getUniqueTerm());
        IndexHelper.addIndexDocument(d);

    }

    public static void addIndexDocument(final IndexDocument d) throws IOException
    {
        final IndexWriter iw = IndexHelper.getWriter();
        try
        {            
            iw.addDocument(d.toDocument());
        }
        finally
        {
            iw.close();
        }
    }
    
    public static void createIndex() throws IOException
    {
        log.info("Create index");
        final IndexWriter iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), IndexHelper.getAnalyzer(), true);
        iw.close();        
    }
    
    public static void optimize() throws IOException
    {
        final IndexWriter iw = IndexHelper.getWriter();
        try
        {
            iw.optimize();
        } finally {
            iw.close();
        }
    }

    private static StandardAnalyzer getAnalyzer()
    {
        return new StandardAnalyzer();
    }

    private static IndexWriter getWriter() throws IOException
    {
        return new IndexWriter(Configuration.getConfig().getIndexLocation(), IndexHelper.getAnalyzer(), false);

    }

    private static void deleteTermDocs(final Term t) throws IOException
    {
        final IndexReader ir = IndexHelper.getReader();
        try
        {
            ir.deleteDocuments(t);
        }
        finally
        {
            ir.close();
        }
    }

    private static IndexReader getReader() throws IOException
    {
        final IndexReader ir = IndexReader.open(Configuration.getConfig().getIndexLocation());
        return ir;
    }

}
