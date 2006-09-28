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

public class AbstractQuery
{

    private IndexReader ir = null;
    protected IndexReader getReader() throws IOException
    {
        if (ir == null)
            ir = IndexReader.open(Configuration.getConfig().getIndexLocation());
        return ir;
    }

    public void close()
    {
        try
        {
            if(ir != null)
                ir.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }
    }

    public Hits performQuery(String query, Sort sort, String defaultFieldName)
    {
        try
        {
            Searcher s = new IndexSearcher(getReader());
            Analyzer a = new StandardAnalyzer();
            QueryParser qp = new QueryParser(defaultFieldName, a);            
            Query q = qp.parse(query);            
            return s.search(q, sort);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
            return null;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
            return null;
        }
    }

}
