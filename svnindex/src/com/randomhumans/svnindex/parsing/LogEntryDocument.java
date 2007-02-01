package com.randomhumans.svnindex.parsing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;

public class LogEntryDocument extends IndexDocument
{

    private static final String CHANGEDPATH_FIELD = "CHANGEDPATH";
    private Map changedPaths;

    @Override
    public Term getUniqueTerm()
    {
        return new Term(IndexDocument.REVISION_FIELD, Long.toString(this.getRevision()));        
    }

    public Map getChangedPaths()
    {
        return this.changedPaths;
    }

    public void setChangedPaths(final Map changedPaths)
    {
        this.changedPaths = changedPaths;
    }

    @Override
    protected List<Field> getFields()
    {
        final ArrayList<Field> fields = new ArrayList<Field>(super.getFields());
        for(final Object path : this.changedPaths.keySet())
        {
            fields.add(new Field(LogEntryDocument.CHANGEDPATH_FIELD, path.toString(), Store.YES, Index.UN_TOKENIZED));
        }
        return fields;
    }

    public LogEntryDocument(final long revision, final String author, final Date date, final String message, final Map changedPaths)
    {
        super(revision, author, date, message);
        this.changedPaths = changedPaths;
    }


}
