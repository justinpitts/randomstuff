
package com.randomhumans.svnindex;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.parsing.DirectoryEntryDocumentGenerator;
import com.randomhumans.svnindex.queries.ContentQuery;
import com.randomhumans.svnindex.queries.IQuery;

public class CommitQueryTest extends TestCase
{
    Log log = LogFactory.getLog(CommitQueryTest.class);

    /*
     * Test method for 'com.randomhumans.svnindex.CommitQuery.performQuery(String)'
     */
    public void testPerformQuery() throws IOException
    {
        IQuery t = new ContentQuery();
        try
        {
            Hits h = t.performQuery("author:jpitts", new Sort(DirectoryEntryDocumentGenerator.REVISION));
            assertNotNull(h);
            assertTrue(0 < h.length());
            for (int i = 0; i < h.length(); i++)
            {
                Document d = h.doc(i);
                log.debug(d);
            }
        }
        finally
        {
            t.close();
        }

    }

}
