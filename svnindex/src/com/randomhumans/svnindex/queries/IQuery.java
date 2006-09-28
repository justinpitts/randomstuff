
package com.randomhumans.svnindex.queries;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

public interface IQuery
{

    public Hits performQuery(String query, Sort sort);

    public void close();

}