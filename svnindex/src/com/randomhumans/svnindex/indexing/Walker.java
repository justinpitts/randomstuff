package com.randomhumans.svnindex.indexing;

import java.util.Calendar;
import java.util.TreeSet;

import com.randomhumans.svnindex.util.Configuration;


public class Walker
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
    // TODO Auto-generated method stub
        ContentIndexer.init();
        TreeSet<String> t = new TreeSet<String>();
        t.add("tags");
        t.add("branches");
        t.add("oracleRetail");
        ISVNUrlAction ta = new ContentTokenizer(t);
        SVNRepoTreeWalker w = new SVNRepoTreeWalker();
        Long start = Calendar.getInstance().getTimeInMillis();
        w.map(Configuration.getConfig().getRepositoryURL(), ta);
        Long done = Calendar.getInstance().getTimeInMillis();
        System.out.println("************************************************");
        System.out.println(done-start);    
        ContentIndexer.close();
        ContentDocumentThread.shutdown();
        System.out.println("walker done");       
    }
}
