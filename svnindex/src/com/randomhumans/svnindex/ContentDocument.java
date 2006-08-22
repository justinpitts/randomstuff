package com.randomhumans.svnindex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.UUID;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

public class ContentDocument
{    
    private static final String CONTENT = "content";
    private static final String URL = "url";
    private static final String REVISION = "revision";
    private static final String DATE = "date";
    private static final String MESSAGE = "message";
    private static final String AUTHOR = "author";

    public static Document createDocument(SVNDirEntry entry, String path)
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
        
        System.out.println(entry.getURL());
        
        try
        {
            SVNRepository repo = RepositoryHelper.getRepo(entry.getURL().toString());
            try
            {
                File temp =  getTempFile();
                OutputStream stream = new FileOutputStream(temp);                    
                repo.getFile("", -1, null, stream);
                Reader r = new InputStreamReader(new FileInputStream(temp));
                doc.add(new Field(CONTENT, r));
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                e.printStackTrace();
            } finally
            {
                repo.closeSession();
            }
        }
        catch (SVNException e)
        {
            // TODO Auto-generated catch block -- Finish Me
            e.printStackTrace();
        }
        doc.add(new Field(CONTENT, "", Field.Store.NO, Field.Index.TOKENIZED));
        return doc;
    }
    
    private static File getTempFile()
    {
        String folder = System.getProperty("java.io.tmpdir");
        return new File(folder, UUID.randomUUID().toString());
    }

}
