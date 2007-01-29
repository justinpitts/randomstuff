
package com.randomhumans.svnindex.queries.render;

import java.io.IOException;

import org.apache.lucene.search.Hits;

import com.randomhumans.svnindex.parsing.DirectoryEntryDocumentGenerator;

public class ConsoleHitsRenderer implements IHitsRenderer
{

    public String render(final Hits h)
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < h.length(); i++)
        {
            try
            {
                sb.append(h.doc(i).get(DirectoryEntryDocumentGenerator.URL)).append("\r\n");
            }
            catch (final IOException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
