
package com.randomhumans.svnindex.queries;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.document.ContentDocument;

public class ContentQuery extends AbstractQuery implements IQuery
{

    @Override
    public Hits performQuery(final String query, final Sort sort)
    {
        return this.performQuery(query, sort, ContentDocument.CONTENT_FIELD);
    }

}
