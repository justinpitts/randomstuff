
package com.randomhumans.svnindex.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import com.randomhumans.svnindex.document.ContentDocument;

public class ContentDocumentGenerator
{
    static Log log = LogFactory.getLog(ContentDocumentGenerator.class);

    public static ContentDocument createDocument(final SVNDirEntry entry, final String path)
    {
        final long revision = entry.getRevision();
        final String author = entry.getAuthor();
        final Date date = entry.getDate();
        final String url = entry.getURL().toString();        
        try
        {

            try
            {
                return new ContentDocument(revision, author, date, url, ContentDocumentGenerator.getContentReader(entry));
            }
            catch (final IOException e)
            {
                ContentDocumentGenerator.log.error(e);
            }
        }
        catch (final SVNException e)
        {
            ContentDocumentGenerator.log.error(e);
        }
        return null;
    }

    private static Reader getContentReader(final SVNDirEntry entry) throws SVNException, IOException
    {        
        if (entry.getSize() < 5 * 1024 * 1024)
        {
            final URLConnection conn = new URL(entry.getURL().toString()).openConnection();
            return new InputStreamReader(conn.getInputStream());
        }
        return null;
    }
}
