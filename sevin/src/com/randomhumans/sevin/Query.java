package com.randomhumans.sevin;

import java.io.IOException;
import org.apache.lucene.search.Hits;
import com.randomhumans.svnindex.CommitQuery;

public class Query
{
    private String query = "";
    
    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public Document[] getResults()
    {
        CommitQuery cq = new CommitQuery();
        try
        {
        Hits h = cq.performQuery(query);
        Document[] docs = new Document[h.length()];
     
        for(int i = 0; i < h.length(); i++)
        {
            try
            {
                docs[i] = new Document(h.doc(i));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                e.printStackTrace();
            }
        }
        return docs;
        } finally {
            cq.close();
        }
    }

}
