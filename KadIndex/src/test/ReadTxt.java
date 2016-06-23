package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class ReadTxt {
	
	private final File file;
	private BufferedReader br;
	private String getcodeType()
	{
		InputStream inputStream=null;
		byte[] head = new byte[3];
		try {
			inputStream = new FileInputStream(file);
			 inputStream.read(head);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
           
        String code = "";  
   
            code = "gb2312";  
        if (head[0] == -1 && head[1] == -2 )  
            code = "UTF-16";  
        if (head[0] == -2 && head[1] == -1 )  
            code = "Unicode";  
        if(head[0]==-17 && head[1]==-69 && head[2] ==-65)  
            code = "UTF-8"; 
        return code;
	}
	public ReadTxt(File file) throws UnsupportedEncodingException, FileNotFoundException
	{
		if(file==null){
			throw new NullPointerException("file is null");
		}
		if(file.isDirectory()){
			throw new IllegalArgumentException("file is directory");
		}
		this.file = file;
		String code = getcodeType();
//		System.out.println("Code:" + code);
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file),code));
	}
	 public String readString() throws IOException
	 {
		 return br.readLine();
	 }
	 
	 public void close()
	 {
		 if(br==null) return;
		 try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
