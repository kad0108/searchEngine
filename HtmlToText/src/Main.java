import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import java.util.Queue;

//class WalkFileTree {
//	
//	private Queue<File> filesQueue = new LinkedList<File>();
//	
//	private WalkFileTree(){
//		
//	}
//	
//	private void dfsWalkFileTree(File rootFile,boolean addDirectory){
//		if(rootFile.isDirectory())
//		{
//			if(addDirectory)
//			{
//				filesQueue.add(rootFile);
//			}
//			File[] list = rootFile.listFiles();
//			for(File file:list)
//			{
//				dfsWalkFileTree(file,addDirectory);
//			}
//		}
//		else
//		{
//			filesQueue.add(rootFile);
//		}
//	}
//	
//	public static Queue<File> visitFileTree(File rootFile,boolean addDirectory) throws IOException
//	{
//		WalkFileTree wft = new WalkFileTree();
//		wft.dfsWalkFileTree(rootFile,addDirectory);
//		return wft.filesQueue;
//	}
//}
//
//public class Main
//{
//	public static void main(String args[]) throws Exception
//	{
//		File dir = new File("D:\\searchEngine\\weblech\\site");
//		
//		Queue<File> files = WalkFileTree.visitFileTree(dir, false);
//		
//		for(File  f: files)
//		{
//			
//			System.out.println(f.getName());
//		}
////		System.out.println(files.size());
//		for(File f: files)
//		{			
////			Document doc = Jsoup.connect("http://news.qq.com/a/20151215/026290.htm").get(); 
//			
//			Document doc  = Jsoup.parse(f, null);
//			String html = doc.toString();
//
//			 
//			html = Jsoup.clean(html, Whitelist.none()); 
//			 
//			html = html.replaceAll("&nbsp;","");
//			
////			FileWriter fw =  new FileWriter(f);
////			fw.write(html);
////			fw.close();
//			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f), "gb2312");
//			fw.write(html);
//			fw.flush();
//			fw.close();
//			
//			String   filename= f.getAbsolutePath();
//			if(filename.indexOf(".") >= 0)
//			{
//				filename = filename.substring(0,filename.lastIndexOf("."));
//			}
//			
//			filename += ".txt";
//			f.renameTo(new File(filename));
////			System.out.println(html);
//		}
//	}
//}
//




import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class WalkFileTree {
	
	private Queue<File> filesQueue = new LinkedList<File>();
	
	private WalkFileTree(){
		
	}
	
	private void dfsWalkFileTree(File rootFile,boolean addDirectory){
		if(rootFile.isDirectory())
		{
			if(addDirectory)
			{
				filesQueue.add(rootFile);
			}
			File[] list = rootFile.listFiles();
			for(File file:list)
			{
				dfsWalkFileTree(file,addDirectory);
			}
		}
		else
		{
			filesQueue.add(rootFile);
		}
	}
	
	
	public static Queue<File> visitFileTree(File rootFile,boolean addDirectory) throws IOException
	{
		WalkFileTree wft = new WalkFileTree();
		wft.dfsWalkFileTree(rootFile,addDirectory);
		return wft.filesQueue;
	}
}




public class Main
{
	public static void main(String args[]) throws Exception
	{
		File dir = new File("D:/searchEngine/baike.baidu.com");
		
		Queue<File> files = WalkFileTree.visitFileTree(dir, false);

//		System.out.println(files.size());
		
		
		
		
		for(File f: files)
		{			
//			Document doc = Jsoup.connect("http://news.qq.com/a/20151215/026290.htm").get(); 
			
			Document doc  = Jsoup.parse(f, null);
			String html = doc.toString();

			List<Element> title_list = doc.getElementsByTag("title");
			
			File title_file = new File(f.getParent() + "\\" + f.getName() +  ".title");
			
//			System.out.println(f.getAbsolutePath());
//			System.out.println(f.getParent());
//			System.out.println(title_file.getAbsolutePath());
			
			title_file.createNewFile();
			
			FileWriter fw_title_file = new FileWriter(title_file);
			
			if(title_list.isEmpty())
			{
				fw_title_file.write(f.getName());
			}
			else 
			{
				String title_text = title_list.get(0).text();
				title_text = title_text.replaceAll("&nbsp;","");
				fw_title_file.write(title_text);
			}
			fw_title_file.close();
				
			

			
			html = Jsoup.clean(html, Whitelist.none()); 
			 
			html = html.replaceAll("&nbsp;","");
			
//			FileWriter fw =  new FileWriter(f);
//			
//			fw.write(html);
//			fw.close();
			
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f), "gb2312");
			fw.write(html);
			fw.flush();
			fw.close();
			
			String   filename= f.getAbsolutePath();

			
			filename += ".txt";
			f.renameTo(new File(filename));
			
			


//			System.out.println(html);
		}
		
		
		
		 
		 
		 
		
		
		 
		 
		 

		
		
	}
}





