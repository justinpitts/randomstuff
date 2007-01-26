
package com.randomhumans.svnindex.queries.render;

import java.io.IOException;

import org.apache.lucene.search.Hits;

import com.randomhumans.svnindex.parsing.DirectoryEntryParser;

public class ConsoleHitsRenderer implements IHitsRenderer
{

    public String render(Hits h)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < h.length(); i++)
        {
            try
            {
                sb.append(h.doc(i).get(DirectoryEntryParser.URL)).append("\r\n");
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
