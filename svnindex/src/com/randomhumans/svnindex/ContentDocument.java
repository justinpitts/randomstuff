
package com.randomhumans.svnindex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.io.SVNRepository;

public class ContentDocument
{
    private static final String CONTENT = "content";

    private static final String URL = "url";

    private static final String REVISION = "revision";

    private static final String DATE = "date";

    private static final String MESSAGE = "message";

    private static final String AUTHOR = "author";

    public static Document createDocument(SVNDirEntry entry, String path) throws IOException
    {
        Document doc = new Document();
        try
        {
            if (RepositoryHelper.checkPath(path) == SVNNodeKind.NONE)
                return doc;
        }
        catch (SVNException e1)
        {}

        doc.add(new Field(AUTHOR, entry.getAuthor() + "", Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field(MESSAGE, entry.getCommitMessage() + "", Field.Store.COMPRESS, Field.Index.TOKENIZED));
        doc.add(new Field(DATE, entry.getDate().toString(), Field.Store.YES, Field.Index.NO));
        doc.add(new Field(REVISION, Long.toString(entry.getRevision()), Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field(URL, entry.getURL().toString(), Field.Store.YES, Field.Index.NO));
        String digest = "";
        Field content = null;
        String mimeType = "";

        try
        {
            SVNRepository repo = RepositoryHelper.getRepo(entry.getURL().toString());
            try
            {
                Map properties = new HashMap();
                File temp = File.createTempFile("index", "dat");
                OutputStream os = new FileOutputStream(temp);
                MessageDigest md5 = null;                
                try
                {
                    md5 = MessageDigest.getInstance("MD5");
                }
                catch (NoSuchAlgorithmException e)
                {
                    // TODO Auto-generated catch block -- Finish Me
                    e.printStackTrace();
                }
                DigestOutputStream stream = new DigestOutputStream(os, md5);

                try
                {
                    repo.getFile("", -1, properties, stream);
                    mimeType = (String) properties.get(SVNProperty.MIME_TYPE);
                    System.out.print(entry.getURL() + "     ");
                    System.out.println(mimeType);
                    digest = new BigInteger(stream.getMessageDigest().digest()).toString(16);
                }
                finally
                {
                    stream.close();
                }
                if (SVNProperty.isTextMimeType(mimeType))
                {
                    Reader r = new InputStreamReader(new FileInputStream(temp));
                    try
                    {
                        content = new Field(CONTENT, r);
                        doc.add(content);
                    }
                    finally
                    {
                        r.close();
                    }
                }
                temp.delete();
            }
            finally
            {
                repo.closeSession();
            }
        }
        catch (SVNException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }        
        doc.add(new Field("MD5", digest, Field.Store.YES, Field.Index.UN_TOKENIZED));

        return doc;
    }
    
}
