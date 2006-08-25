package com.randomhumans.svnindex;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;

import com.randomhumans.svnindex.indexing.LogIndexer;

import junit.framework.TestCase;

public class LogIndexerTest extends TestCase
{


    /*
     * Test method for 'com.randomhumans.svnindex.LogIndexer.rebuild()'
     */
    public void testRebuild() throws IOException, SVNException
    {
        LogIndexer.rebuild();

    }
    
    public void testAdd() throws SVNException, IOException
    {
        LogIndexer.addRevisions();
    }


}
