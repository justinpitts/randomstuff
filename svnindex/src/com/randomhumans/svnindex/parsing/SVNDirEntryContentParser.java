
package com.randomhumans.svnindex.parsing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.randomhumans.svnindex.util.MD5Formatter;
import com.randomhumans.svnindex.util.RepositoryHelper;
import com.randomhumans.svnindex.util.TempFileReader;

public class SVNDirEntryContentParser implements IFieldParser<SVNDirEntry>
{
    static Log log = LogFactory.getLog(SVNDirEntryContentParser.class);

    public List<Field> parse(final SVNDirEntry entry)
    {
        final ArrayList<Field> fields = new ArrayList<Field>();
        String digest = "";
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
                        digest = MD5Formatter.formatHash(stream.getMessageDigest().digest());
                    }
                    finally
                    {
                        stream.close();
                    }
                }
                catch (final NoSuchAlgorithmException e)
                {
                    SVNDirEntryContentParser.log.error(e);
                }
                if (SVNProperty.isTextMimeType(mimeType))
                {                    
                    fields.add(new Field(DirectoryEntryDocumentGenerator.CONTENT, new TempFileReader(temp), TermVector.YES));
                }
            }
            catch (final IOException e)
            {
                SVNDirEntryContentParser.log.error(e);
            }
            finally
            {
                repo.closeSession();
            }
        }
        catch (final SVNException e)
        {
            SVNDirEntryContentParser.log.error(e);
        }
        fields.add(new Field(DirectoryEntryDocumentGenerator.MD5, digest, Field.Store.YES, Field.Index.UN_TOKENIZED));

        return fields;
    }

}
