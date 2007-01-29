
package com.randomhumans.svnindex.queries;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.util.Configuration;

public abstract class AbstractQuery
{

    private IndexReader ir = null;

    protected IndexReader getReader() throws IOException
    {
        if (this.ir == null)
        {
            this.ir = IndexReader.open(Configuration.getConfig().getIndexLocation());
        }
        return this.ir;
    }

    public void close()
    {
        try
        {
            if (this.ir != null)
            {
                this.ir.close();
            }
        }
        catch (final IOException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }
    }

    abstract public Hits performQuery(String query, Sort sort);

    protected Hits performQuery(final String query, final Sort sort, final String defaultFieldName)
    {
        try
        {
            final Searcher s = new IndexSearcher(this.getReader());
            final Analyzer a = new StandardAnalyzer();
            final QueryParser qp = new QueryParser(defaultFieldName, a);
            final Query q = qp.parse(query);
            return s.search(q, sort);
        }
        catch (final IOException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
            return null;
        }
        catch (final ParseException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
            return null;
        }
    }

}
