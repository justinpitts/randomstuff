
package com.randomhumans.svnindex.parsing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.randomhumans.svnindex.util.MD5Formatter;
import com.randomhumans.svnindex.util.RepositoryHelper;
import com.randomhumans.svnindex.util.TempFileReader;

public class ContentDocumentGenerator
{
    static Log log = LogFactory.getLog(ContentDocumentGenerator.class);

    public static ContentDocument createDocument(final SVNDirEntry entry, final String path)
    {
        final long revision = entry.getRevision();
        final String author = entry.getAuthor();
        final Date date = entry.getDate();
        final String message = entry.getCommitMessage();
        final String url = entry.getURL().toString();
        String md5Hash = "";
        Reader content = null;
        ;
        String mimeType = "";
        try
        {
            final SVNRepository repo = RepositoryHelper.getRepo(entry.getURL().toString());
            try
            {
                final Map properties = new HashMap();
                final File temp = File.createTempFile("index", "dat");
                final OutputStream os = new FileOutputStream(temp);
                try
                {
                    final DigestOutputStream stream = new DigestOutputStream(os, MessageDigest.getInstance("MD5"));
                    try
                    {
                        repo.getFile("", -1, properties, stream);
                        mimeType = (String) properties.get(SVNProperty.MIME_TYPE);
                        md5Hash = MD5Formatter.formatHash(stream.getMessageDigest().digest());
                    }
                    finally
                    {
                        stream.close();
                    }
                }
                catch (final NoSuchAlgorithmException e)
                {
                    ContentDocumentGenerator.log.error(e);
                }
                finally
                {
                    os.close();
                }
                if (SVNProperty.isTextMimeType(mimeType))
                {
                    content = new TempFileReader(temp);
                }

                return new ContentDocument(revision, author, date, message, url, md5Hash, content);
            }
            catch (final IOException e)
            {
                ContentDocumentGenerator.log.error(e);
            }
            finally
            {
                repo.closeSession();
            }
        }
        catch (final SVNException e)
        {
            ContentDocumentGenerator.log.error(e);
        }
        return null;
    }
}
