package svnindex;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

public class CommitQuery
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException
	{
		IndexReader ir = IndexReader.open("z:/index");
		Searcher s = new IndexSearcher(ir);
		Analyzer a = new StandardAnalyzer();
		
		Query q = new QueryParser("message", a).parse("NGPHASE*");
		
		Hits hits = s.search(q);
		
		for(int i = 0; i < hits.length(); i++)
		{
			Document doc = hits.doc(i);			
			System.out.println(doc.getField("revision").stringValue());
			System.out.println(doc.getField("author").stringValue());
			System.out.println(doc.getField("message").stringValue());
			System.out.println("");	
			System.out.println("----------------------------------------------------------------");
		}
		ir.close();
	}

}
