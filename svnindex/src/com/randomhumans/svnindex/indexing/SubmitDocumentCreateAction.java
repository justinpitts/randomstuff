package com.randomhumans.svnindex.indexing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.tmatesoft.svn.core.SVNDirEntry;


public class SubmitDocumentCreateAction implements ISVNUrlAction
{
    private static ExecutorService pool = Executors.newFixedThreadPool(16);
    public boolean execute(String url, SVNDirEntry entry)
    {
        pool.submit(new ContentDocumentThread());
        return true;
        

    }

 

}
