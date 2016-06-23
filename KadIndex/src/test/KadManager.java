package test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import test.ReadTxt;

public class KadManager {

	private static String DATA_DIR = "D:\\searchEngine\\baike.baidu.com\\view";
	private static String INDEX_DIR = "D:\\searchEngine\\lucene\\newIndex";
	private static String content = "";
	private static String title="";

	public static void main(String[] args) throws IOException {

		createIndex(DATA_DIR);
//		searchIndex("世界");

	}

	public static boolean isTxtFile(String fileName) {
		if (fileName.lastIndexOf(".txt") > 0) {
			return true;
		}
		return false;
	}

	private static List<File> fileList = new ArrayList<File>();
	public static void getFileList(String dirPath) {
		File[] files = new File(dirPath).listFiles();
		
		for (File file : files) {
			if(!file.isFile()){
				getFileList(file.toString());
			}
			if (isTxtFile(file.getName())) {
				fileList.add(file);
			}
		}
	}

	public static void createIndex(String path) throws IOException {
		// File indexDir = new File("/lucene-test/index");
		fileList.clear();
		getFileList(path);
		
		System.out.println("GGGG");
		System.out.println(fileList);
		for (File file : fileList) {
			content = "";
			title="";
			String abs_path = file.getAbsolutePath();
			int last_dian = abs_path.lastIndexOf('.');
			File title_file = new File(abs_path.substring(0, last_dian) + ".title");
			
			FileReader file_reader = new FileReader(title_file);
			char[] buf = new char[10000];			
			int len = file_reader.read(buf);
			title = new String(buf, 0, len);
			
			System.out.println(file.getName());
			System.out.println(title);
			
			
			String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if ("txt".equalsIgnoreCase(type)) {
				ReadTxt read = new ReadTxt(file);
				content += read.readString();
				read.close();
			}
			// System.out.println("name :"+file.getName());
			// System.out.println("path :"+file.getPath());
			System.out.println("path :"+content);
			
			Analyzer analyzer = new IKAnalyzer5x();
			Document doc = new Document();
			doc.add(new TextField("filename", file.getName(), Store.YES));
			doc.add(new TextField("content", content, Store.YES));
			doc.add(new TextField("path", file.getPath(), Store.YES));
			IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
			iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			try {
				Directory fsDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
				IndexWriter indexWriter = new IndexWriter(fsDirectory, iwConfig);
				indexWriter.addDocument(doc);
				indexWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void searchIndex(String key) {
		try {
			Analyzer analyzer = new IKAnalyzer5x();
			Directory fsDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
			// Directory fsDirectory = FSDirectory.open(indexDir);
			DirectoryReader ireader = DirectoryReader.open(fsDirectory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

			QueryParser qp = new QueryParser("content", analyzer); // 使用QueryParser查询分析器构造Query对象
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(key); // 搜索Lucene
			TopDocs topDocs = isearcher.search(query, 5); // 搜索相似度最高的5条记录
			System.out.println("命中:" + topDocs.totalHits);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
//				System.out.println("内容:" + targetDoc.toString());
				System.out.print("********************************");
				System.out.println(targetDoc.get("filename"));
                System.out.println(targetDoc.get("content"));
                System.out.println(targetDoc.get("path"));
                System.out.print("********************************");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
