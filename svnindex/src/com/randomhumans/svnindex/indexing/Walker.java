package com.randomhumans.svnindex.indexing;

import java.util.Calendar;
import java.util.TreeSet;


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
        ISVNUrlAction ta = new ContentTokenizer(t);
        SVNRepoTreeWalker w = new SVNRepoTreeWalker();
        Long start = Calendar.getInstance().getTimeInMillis();
        w.map("http://svn.cns.com/repo/", ta);
        Long done = Calendar.getInstance().getTimeInMillis();
        System.out.println("************************************************");
        System.out.println(done-start);    
        ContentIndexer.optimizeIndex();
        
    }

}
