
package com.randomhumans.svnindex;

import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.parsing.IndexDocument;
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
        final IQuery t = new ContentQuery();
        try
        {
            final Hits h = t.performQuery("author:jpitts", new Sort(IndexDocument.REVISION_FIELD));
            Assert.assertNotNull(h);
            Assert.assertTrue(0 < h.length());
            for (int i = 0; i < h.length(); i++)
            {
                final Document d = h.doc(i);
                this.log.debug(d);
            }
        }
        finally
        {
            t.close();
        }

    }

}
