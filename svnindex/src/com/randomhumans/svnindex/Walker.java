package com.randomhumans.svnindex;

import java.util.Calendar;

public class Walker
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
    // TODO Auto-generated method stub
        TestAction ta = new TestAction();
        SVNRepoTreeWalker w = new SVNRepoTreeWalker();
        Long start = Calendar.getInstance().getTimeInMillis();
        w.map("http://svn.cns.com/repo/merch", ta);
        Long done = Calendar.getInstance().getTimeInMillis();
        System.out.println(done-start);
        System.out.println(ta.getI());
        
    }

}
