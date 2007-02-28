
package com.randomhumans.svnindex.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;

import com.randomhumans.svnindex.document.IndexDocument;
import com.randomhumans.svnindex.indexing.Predicate;

public class IndexHelper
{

    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(IndexHelper.class);

    public static void updateIndexDoc(final IndexDocument d) throws IOException
    {
        final IndexWriter iw = IndexHelper.getWriter();
        try
        {
            iw.updateDocument(d.getUniqueTerm(), d.toDocument());
        }
        finally
        {
            iw.close();
            d.close();
        }
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
            d.close();
        }
    }

    public static void createIndex() throws IOException
    {
        IndexHelper.log.info("Create index");
        final IndexWriter iw = new IndexWriter(Configuration.getConfig().getIndexLocation(), IndexHelper.getAnalyzer(),
            true);
        iw.close();
    }

    public static void optimize() throws IOException
    {
        final IndexWriter iw = IndexHelper.getWriter();
        try
        {
            iw.optimize();
        }
        finally
        {
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

    public static List<String> getTerms(final String fieldName, final Predicate<String> filter)
    {
        int i = 0;
        final ArrayList<String> results = new ArrayList<String>();
        try
        {
            final IndexReader ir = IndexReader.open(Configuration.getConfig().getIndexLocation());
            try
            {
                final TermEnum te = ir.terms();
                while (te.next() && (i < 25))
                {
                    final Term t = te.term();
                    if (t.field().equals(fieldName))
                    {
                        if (filter.eval(t.text()))
                        {
                            results.add(t.text());
                            i++;
                        }
                    }
                }
                te.close();
            }
            finally
            {
                ir.close();
            }
        }
        catch (final IOException e)
        {
            IndexHelper.log.error(e);
            return null;
        }
        return results;
    }

    public static List<String> getTerms(final String fieldName)
    {
        final Predicate<String> truePred = new Predicate<String>()
        {
            public boolean eval(String t)
            {
                return true;
            }
        };
        return IndexHelper.getTerms(fieldName, truePred);
    }
}
