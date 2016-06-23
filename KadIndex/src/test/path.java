package test;

import java.io.IOException;

public class path {
	public static void main(String[] args) throws Exception {
		trueIndex("1740794e3f7471704de2f28a.txt");
	}
	
	public static void trueIndex(String path) throws Exception {
//    	String truepath = "";
		System.out.println(path);
		
//		int id = path.indexOf(".");
//		System.out.println(id);
//		String want = path.substring(0,  id);
//		System.out.println(want);
//		System.out.println();
		
    	String p[] = path.split("\\.");
    	for(int i = 0; i < p.length; i++)
    	{
    		System.out.println(p[i]);
    	}
//    	return truepath;
    }
}
