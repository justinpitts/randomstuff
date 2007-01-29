
package com.randomhumans.svnindex.util;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermEnum;

public class IndexDumper
{

    /**
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException
    {

        final IndexReader ir = IndexReader.open("C:\\index");
        for (int i = 0; i < ir.numDocs(); i++)
        {
            final Document d = ir.document(i);
            for (final Enumeration e = d.fields(); e.hasMoreElements();)
            {
                final Field f = (Field) e.nextElement();
                System.out.println(f.name());
                System.out.println(f.stringValue());
                System.out.println();

            }

        }
        for(final TermEnum terms = ir.terms();terms.next();)
        {
            System.out.println(terms.term().field() + ":" + terms.term().text());
        }
        
    }

}
