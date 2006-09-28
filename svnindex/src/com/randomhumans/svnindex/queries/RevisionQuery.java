package com.randomhumans.svnindex.queries;

import java.io.IOException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;
import com.randomhumans.svnindex.indexing.RevisionDocument;

public class RevisionQuery extends AbstractQuery implements IQuery 
{
    /**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
    
    /* (non-Javadoc)
     * @see com.randomhumans.svnindex.queries.IQuery#performQuery(java.lang.String, org.apache.lucene.search.Sort)
     */
    public Hits performQuery(String query, Sort sort)
	{ 
        return performQuery(query, sort, RevisionDocument.MESSAGE_FIELDNAME);
	}

}
