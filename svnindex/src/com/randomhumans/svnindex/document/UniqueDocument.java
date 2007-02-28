
package com.randomhumans.svnindex.document;

import org.apache.lucene.index.Term;

public interface UniqueDocument
{

    //returns the term that uniquely identifies this document in the index. Used for update/delete.
    public abstract Term getUniqueTerm();

}