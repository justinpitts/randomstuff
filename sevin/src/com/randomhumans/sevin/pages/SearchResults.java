package com.randomhumans.sevin.pages;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;

import com.randomhumans.sevin.Document;
import com.randomhumans.svnindex.document.ContentDocument;
import com.randomhumans.svnindex.queries.ContentQuery;
import com.randomhumans.svnindex.queries.IQuery;

public abstract class SearchResults extends BasePage implements PageBeginRenderListener
{
    Log log = LogFactory.getLog(this.getClass());
    String sortField = ContentDocument.AUTHOR_FIELD;
    
    public abstract void setQuery(String query);
    public abstract String getQuery();
    public void pageBeginRender(PageEvent pageevent)
    {
        
    }
    
    public abstract Document getDoc();
    public abstract void setDoc(Document d);
    
    public Document[] getResults()
    {        
        IQuery cq = new ContentQuery();
        try
        {
        Hits h = cq.performQuery(getQuery(), new Sort(sortField));
        
        Document[] docs = new Document[h.length()];
     
        for(int i = 0; i < h.length(); i++)
        {
            try
            {
                docs[i] = new Document(h.doc(i));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block -- Finish Me
                log.error(e);
            }
        }
        log.info(docs.length);
        return docs;
        } catch (Exception ex) {
        	log.error(ex);
        	
        } finally {
            cq.close();
        }
        return null;
    }

}
