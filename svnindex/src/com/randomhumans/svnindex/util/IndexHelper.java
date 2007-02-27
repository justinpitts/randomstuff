
package com.randomhumans.svnindex.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;

import com.randomhumans.svnindex.indexing.Predicate;
import com.randomhumans.svnindex.parsing.IndexDocument;

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

    public static List<String> getTerms(String fieldName, Predicate<String> filter)
    {
        int i = 0;
        ArrayList<String> results = new ArrayList<String>();
        try
        {
            IndexReader ir = IndexReader.open(Configuration.getConfig().getIndexLocation());
            try
            {
                TermEnum te = ir.terms();
                while (te.next() && i < 25)
                {
                    Term t = te.term();
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
        catch (IOException e)
        {
            log.error(e);
            return null;
        }
        return results;
    }

    public static List<String> getTerms(String fieldName)
    {
        Predicate<String> truePred = new Predicate<String>()
        {
            public boolean eval(String t)
            {
                return true;
            }
        };
        return getTerms(fieldName, truePred);
    }
}
