package com.randomhumans.svnindex.queries.render;

import org.apache.lucene.search.Hits;

public interface IHitsRenderer
{
    String render(Hits h);
}
