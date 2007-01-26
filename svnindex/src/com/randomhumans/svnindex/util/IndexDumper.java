
package com.randomhumans.svnindex.util;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;

public class IndexDumper
{

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {

        IndexReader ir = IndexReader.open("C:\\index");
        for (int i = 0; i < ir.numDocs(); i++)
        {
            Document d = ir.document(i);
            for (Enumeration e = d.fields(); e.hasMoreElements();)
            {
                Field f = (Field) e.nextElement();
                System.out.println(f.name());
                System.out.println(f.stringValue());
                System.out.println();

            }

        }

    }

}
