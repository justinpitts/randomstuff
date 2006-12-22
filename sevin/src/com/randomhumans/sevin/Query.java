package com.randomhumans.sevin;

import java.io.IOException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;
import com.randomhumans.svnindex.indexing.ContentDocument;
import com.randomhumans.svnindex.queries.ContentQuery;
import com.randomhumans.svnindex.queries.IQuery;

public class Query
{
    private String queryString = "";
    
    private String sortField = ContentDocument.URL;
    
    public String getQueryString()
    {
        return queryString;
    }

    public void setQueryString(final String query)
    {
        this.queryString = query;
    }

    public Document[] getResults()
    {
        final IQuery contentQuery = new ContentQuery();
        try
        {
        Hits hits = contentQuery.performQuery(queryString, new Sort(sortField));
        
        Document[] docs = new Document[hits.length()];
     
        for(int i = 0; i < hits.length(); i++)
        {
            try
            {
                docs[i] = new Document(hits.doc(i));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                e.printStackTrace();
            }
        }
        return docs;
        } finally {
            contentQuery.close();
        }
    }

    public String getSortField()
    {
        return sortField;
    }

    public void setSortField(final String sortField)
    {
        this.sortField = sortField;
    }
    
    

}
