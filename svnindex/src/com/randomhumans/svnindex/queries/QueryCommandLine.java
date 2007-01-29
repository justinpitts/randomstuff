
package com.randomhumans.svnindex.queries;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;

import com.randomhumans.svnindex.parsing.DirectoryEntryDocumentGenerator;
import com.randomhumans.svnindex.queries.render.ConsoleHitsRenderer;
import com.randomhumans.svnindex.queries.render.IHitsRenderer;

public class QueryCommandLine
{

    /**
     * @param args
     */
    public static void main(final String[] args)
    {
        final String queryString = args[0];
        final ContentQuery q = new ContentQuery();
        final Sort sort = new Sort(DirectoryEntryDocumentGenerator.REVISION);
        final Hits hits = q.performQuery(queryString, sort);
        final IHitsRenderer renderer = new ConsoleHitsRenderer();
        System.out.println("There are " + hits.length() + " results for query:");
        System.out.println("\t" + queryString);
        System.out.println(renderer.render(hits));
    }

}
