package kad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.opensymphony.xwork2.ActionSupport;

import kad.QueryResult;
import util.IKAnalyzer5x;

//import test.IndexManager;
import util.PageGetBaseAction;

public class IndexAction extends PageGetBaseAction{
	private String key;
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	
	private List<QueryResult> qrList = new ArrayList<QueryResult>();
	private String search_table="";
	
    private static String content="";
    private static String title="";
    private static String url="";
    private int hits=0;
    private long time=0;
    
    private static String INDEX_DIR = "D:\\software\\eclipse\\workspace\\KadSearch\\newIndex";
//    private static String DATA_DIR = "D:\\searchEngine\\baike.baidu.com\\view";
    final private static String Prefix = "D:\\searchEngine\\"; 
    private static Analyzer analyzer = null;
    private static Directory directory = null;
    private static IndexWriter indexWriter = null;
    
    
    /**
     * 查找索引，返回符合条件的文件
     * @param text 查找的字符串
     * @return 符合条件的文件List
     */
    public void searchIndex(String key){
    	Date date1 = new Date();
    	try {
			Analyzer analyzer = new IKAnalyzer5x();
			Directory fsDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
			// Directory fsDirectory = FSDirectory.open(indexDir);
			DirectoryReader ireader = DirectoryReader.open(fsDirectory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

			QueryParser qp = new QueryParser("content", analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(key); // 搜索Lucene
			TopDocs topDocs = isearcher.search(query, 30);
			System.out.println("命中:" + topDocs.totalHits);
			hits = topDocs.totalHits;
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				
				//Highlight
				SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span class=\"light\">", "</span>");
    			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
//    			highlighter.setTextFragmenter(new SimpleFragmenter(targetDoc.get("content").length()));
    			highlighter.setTextFragmenter(new SimpleFragmenter(100));
    			String highLightText = null;
    			if (targetDoc.get("content") != null) {
    				TokenStream tokenStream = analyzer.tokenStream(content, new StringReader(targetDoc.get("content")));
    				highLightText = highlighter.getBestFragment(tokenStream, targetDoc.get("content"));
    			}
    			//Highlight
				
    			//Title
    			title="";
    			String abs_path = targetDoc.get("path");
    			int last_dian = abs_path.lastIndexOf('.');
    			File title_file = new File(abs_path.substring(0, last_dian) + ".title");
    			
    			FileReader file_reader = new FileReader(title_file);
    			char[] buf = new char[10000];
    			int len = file_reader.read(buf);
    			title = new String(buf, 0, len);
    			//Title
    			
    			//url
    			url = targetDoc.get("path").substring(Prefix.length(), targetDoc.get("path").lastIndexOf("."));
//    			System.out.println(url);
    			//url
    			
    			
                QueryResult qr = new QueryResult();
                qr.setFilename(targetDoc.get("filename"));
                qr.setContent(highLightText.toString());
                qr.setPath(targetDoc.get("path"));
                qr.setTitle(title);
                qr.setUrl(url);
                qr.setScore(scoreDocs[i].score);
                qrList.add(qr);
			}
//			System.out.println("*********************");
//			for(int i = 0; i < qrList.size(); i++) {
//				System.out.println(qrList.get(i).getFilename() 
//          			+ " " + qrList.get(i).getContent() + " " + qrList.get(i).getPath());
//			}
//			System.out.println("*********************");
			
//			ireader.close();
//            directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Date date2 = new Date();
        time = (date2.getTime() - date1.getTime());
    }
    
    
    public String searchShow() throws Exception{
    	return SUCCESS;
    }
    
    public String getPage()  throws Exception{
    	qrList.clear();
    	searchIndex(key);
    	qrList = this.makeCurrentPageList(qrList, 4);
    	search_table = util.Util.getJspOutput("queryTable.jsp");
    	return SUCCESS;
    }
    
    public String query() throws Exception{
		System.out.println("Happy!!!" + key);
		qrList.clear();
    	searchIndex(key);
		qrList = this.makeCurrentPageList(qrList, 4);
		System.out.println(this.getTotalPageNum());
		search_table = util.Util.getJspOutput("queryTable.jsp");
    	
		return SUCCESS;
	}
	
		
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public List<QueryResult> getQrList() {
		return qrList;
	}

	public void setQrList(List<QueryResult> qrList) {
		this.qrList = qrList;
	}

	public String getSearch_table() {
		return search_table;
	}

	public void setSearch_table(String search_table) {
		this.search_table = search_table;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public int getHits() {
		return hits;
	}


	public void setHits(int hits) {
		this.hits = hits;
	}
	
}
