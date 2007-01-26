
package com.randomhumans.svnindex.queries;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.parsing.DirectoryEntryParser;

public class ContentQuery extends AbstractQuery implements IQuery
{

    public Hits performQuery(String query, Sort sort)
    {
        return performQuery(query, sort, DirectoryEntryParser.CONTENT);
    }

}
