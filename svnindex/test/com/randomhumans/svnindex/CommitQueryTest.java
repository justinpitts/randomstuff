package com.randomhumans.svnindex;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.indexing.RevisionDocument;
import com.randomhumans.svnindex.queries.IQuery;
import com.randomhumans.svnindex.queries.RevisionQuery;

import junit.framework.TestCase;

public class CommitQueryTest extends TestCase
{
	Log log = LogFactory.getLog(CommitQueryTest.class);

    /*
     * Test method for 'com.randomhumans.svnindex.CommitQuery.performQuery(String)'
     */
    public void testPerformQuery() throws IOException
    {
        IQuery t = new RevisionQuery();
        try {
        Hits h = t.performQuery("author:justinpitts", new Sort(RevisionDocument.REVISION_FIELDNAME));
        assertNotNull(h);
        for(int i = 0; i < h.length(); i ++)
        {
            Document d = h.doc(i);
            log.debug(d);            
        }
        } finally {
            t.close();
        }
        
    }

}
