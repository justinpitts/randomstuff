package com.randomhumans.sevin;

import java.io.IOException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.indexing.RevisionDocument;
import com.randomhumans.svnindex.queries.IQuery;
import com.randomhumans.svnindex.queries.RevisionQuery;

public class Query
{
    private String query = "";
    
    private String sortField = RevisionDocument.REVISION_FIELDNAME;
    
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
        IQuery cq = new RevisionQuery();
        try
        {
        Hits h = cq.performQuery(query, new Sort(sortField));
        
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

    public String getSortField()
    {
        return sortField;
    }

    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
    
    

}
