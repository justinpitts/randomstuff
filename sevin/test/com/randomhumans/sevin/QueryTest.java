package com.randomhumans.sevin;

import junit.framework.TestCase;

public class QueryTest extends TestCase
{


    /*
     * Test method for 'com.randomhumans.sevin.Query.getResults()'
     */
    public void testGetResults()
    {
        Query q = new Query();
        q.setQuery("author:pitts");
        q.getResults();
        
        q.setQuery("112345678987698769876987698769879876");
        q.getResults();

    }

}
