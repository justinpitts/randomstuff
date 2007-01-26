
package com.randomhumans.svnindex.queries;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.parsing.DirectoryEntryParser;
import com.randomhumans.svnindex.queries.render.ConsoleHitsRenderer;
import com.randomhumans.svnindex.queries.render.IHitsRenderer;

public class QueryCommandLine
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String queryString = args[0];
        ContentQuery q = new ContentQuery();
        Sort sort = new Sort(DirectoryEntryParser.DATE);
        Hits hits = q.performQuery(queryString, sort);
        IHitsRenderer renderer = new ConsoleHitsRenderer();
        System.out.println("There are " + hits.length() + " results for query:");
        System.out.println("\t" + queryString);
        System.out.println(renderer.render(hits));
    }

}
