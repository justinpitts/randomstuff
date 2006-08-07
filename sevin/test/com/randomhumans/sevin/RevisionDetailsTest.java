package com.randomhumans.sevin;

import junit.framework.TestCase;

public class RevisionDetailsTest extends TestCase
{
    public void testGetAuthor()
    {
        RevisionDetails rd = new RevisionDetails();
        rd.setRevision(3);
        assertEquals(rd.getAuthor(), "justinpitts");
    }
}
