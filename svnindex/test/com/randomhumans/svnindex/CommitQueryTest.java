package com.randomhumans.svnindex;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.indexing.RevisionDocument;
import com.randomhumans.svnindex.queries.CommitQuery;

import junit.framework.TestCase;

public class CommitQueryTest extends TestCase
{

    /*
     * Test method for 'com.randomhumans.svnindex.CommitQuery.performQuery(String)'
     */
    public void testPerformQuery() throws IOException
    {
        CommitQuery t = new CommitQuery();
        try {
        Hits h = t.performQuery("author:justinpitts", new Sort(RevisionDocument.REVISION_FIELDNAME));
        assertNotNull(h);
        for(int i = 0; i < h.length(); i ++)
        {
            Document d = h.doc(i);
            System.out.println(d);        
            
        }
        } finally {
            t.close();
        }
        
    }

}
