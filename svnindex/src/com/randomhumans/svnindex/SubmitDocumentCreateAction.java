package com.randomhumans.svnindex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.tmatesoft.svn.core.SVNDirEntry;

public class SubmitDocumentCreateAction implements ISVNUrlAction
{
    private static ExecutorService pool = Executors.newFixedThreadPool(16);
    public void execute(String url, SVNDirEntry entry)
    {
        pool.submit(new ContentDocumentThread());
        

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
    // TODO Auto-generated method stub

    }

}
