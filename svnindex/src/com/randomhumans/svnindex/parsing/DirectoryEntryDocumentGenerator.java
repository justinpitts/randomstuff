
package com.randomhumans.svnindex.parsing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;

import com.randomhumans.svnindex.util.RepositoryHelper;

public class DirectoryEntryDocumentGenerator
{
    public static final String CONTENT = "content";

    public static final String URL = "url";

    public static final String REVISION = "revision";

    public static final String DATE = "date";

    public static final String MESSAGE = "message";

    public static final String AUTHOR = "author";

    public static final String MD5 = "md5";

    static Log log = LogFactory.getLog(DirectoryEntryDocumentGenerator.class);

    public static Document createDocument(final SVNDirEntry entry, final String path)
    {
        final Document doc = new Document();
        try
        {
            if (RepositoryHelper.checkPath(path) == SVNNodeKind.NONE)
            {
                return doc;
            }
        }
        catch (final SVNException e1)
        {
            DirectoryEntryDocumentGenerator.log.warn(e1);
        }

        DirectoryEntryDocumentGenerator.addFields(entry, doc, new SVNDirectoryEntryHeaderParser());
        DirectoryEntryDocumentGenerator.addFields(entry, doc, new SVNDirEntryContentParser());
        return doc;
    }

    private static void addFields(final SVNDirEntry entry, final Document doc, final IFieldParser<SVNDirEntry> p)
    {
        for (final Field f : p.parse(entry))
        {
            doc.add(f);
        }
    }
}
