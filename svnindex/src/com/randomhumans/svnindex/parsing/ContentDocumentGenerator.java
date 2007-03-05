
package com.randomhumans.svnindex.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.io.SVNFileRevision;

import com.randomhumans.svnindex.document.ContentDocument;
import com.randomhumans.svnindex.util.Configuration;
import com.randomhumans.svnindex.util.RepositoryHelper;

public class ContentDocumentGenerator
{
    static Log log = LogFactory.getLog(ContentDocumentGenerator.class);

    public static ContentDocument createDocument(final SVNDirEntry entry, final String path)
    {
        if (!SVNNodeKind.FILE.equals(entry.getKind()))
        {
            log.debug(path + "/" + entry.getName() + " is not a file entry. aborting.");
            return null;
        }
        final long revision = entry.getRevision();
        final String author = entry.getAuthor();
        final Date date = entry.getDate();
        final String url = entry.getURL().toString();
        try
        {
            try
            {
                return new ContentDocument(revision, author, date, url, ContentDocumentGenerator.getContentReader(
                    entry, path));
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

    private static Reader getContentReader(final SVNDirEntry entry, final String path) throws SVNException, IOException
    {

        SVNFileRevision fileRevision = RepositoryHelper.getFileRevision(entry, path);
        if (fileRevision == null)
        {
            log.debug("Unable to get file revision info for " + path  + "/"  + entry.getName());
            return null;
        }
        if (!isIgnoredExtension(entry.getName()))
            if (isTextMimeType(fileRevision))
                if (entry.getSize() < 5 * 1024 * 1024)
                {
                    final URLConnection conn = new URL(entry.getURL().toString()).openConnection();
                    return new InputStreamReader(conn.getInputStream());
                }
        return null;
    }

    private static boolean isIgnoredExtension(String name)
    {
        for (String extension : Configuration.getConfig().getIgnoredExtensions())
        {
            if (name.toLowerCase().endsWith(extension.toLowerCase()))
                return true;
        }
        return false;
    }

    private static boolean isTextMimeType(SVNFileRevision fileRevision)
    {
        if (fileRevision == null)
            return false;
        Map properties = fileRevision.getRevisionProperties();
        String mimeType = (String) properties.get(SVNProperty.MIME_TYPE);
        boolean isTextMimeType = SVNProperty.isTextMimeType(mimeType);
        return isTextMimeType;
    }
}
