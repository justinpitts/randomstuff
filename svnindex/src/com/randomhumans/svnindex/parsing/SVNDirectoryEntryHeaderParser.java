
package com.randomhumans.svnindex.parsing;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Field;
import org.tmatesoft.svn.core.SVNDirEntry;

public class SVNDirectoryEntryHeaderParser implements IFieldParser<SVNDirEntry>
{

    public List<Field> parse(final SVNDirEntry input)
    {
        final ArrayList<Field> fields = new ArrayList<Field>();

        fields.add(new Field(DirectoryEntryDocumentGenerator.URL, input.getURL().toString(), Field.Store.YES, Field.Index.NO));
        fields.add(new Field(DirectoryEntryDocumentGenerator.REVISION, Long.toString(input.getRevision()), Field.Store.YES,
            Field.Index.UN_TOKENIZED));
        fields.add(new Field(DirectoryEntryDocumentGenerator.DATE, input.getDate().toString(), Field.Store.YES, Field.Index.NO));
        fields.add(new Field(DirectoryEntryDocumentGenerator.MESSAGE, input.getCommitMessage() + "", Field.Store.COMPRESS,
            Field.Index.TOKENIZED));
        fields.add(new Field(DirectoryEntryDocumentGenerator.AUTHOR, input.getAuthor() + "", Field.Store.YES,
            Field.Index.TOKENIZED));

        return fields;
    }

}
