
package com.randomhumans.svnindex.queries.render;

import java.io.IOException;

import org.apache.lucene.search.Hits;

import com.randomhumans.svnindex.document.ContentDocument;

public class ConsoleHitsRenderer implements IHitsRenderer
{

    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ConsoleHitsRenderer.class);

    public String render(final Hits h)
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < h.length(); i++)
        {
            try
            {
                sb.append(h.doc(i).get(ContentDocument.URL_FIELD)).append("\r\n");
            }
            catch (final IOException e)
            {                
                ConsoleHitsRenderer.log.error(e);
            }
        }
        return sb.toString();
    }

}
