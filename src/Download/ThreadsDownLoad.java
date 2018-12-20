package Download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import javax.swing.JOptionPane;

public class ThreadsDownLoad extends Thread{
		private  String url,savePath;
		private int ThreadNum=1;
		private String name;
		private final long totalLength;
		private static int index=0;
		
		public ThreadsDownLoad(String url,String savePath,int ThreadNum,long totalLength,String name) {
			 this.url=url;
			 this.savePath=savePath;
			 this.ThreadNum=ThreadNum;
			 this.totalLength=totalLength;
			 this.name=name;
			
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			   long length=totalLength/ThreadNum;
			   long mod=totalLength%length;
			   
			   for(int i=0;i<ThreadNum;i++) {
				    long start=i*length;
				    long end;
				     if(i<ThreadNum-1) {
				    	
				    	 end=(i+1)*length-1;
				     }else {
				    	 end=(i+1)*length+mod;
				     }
				     downloadTest(start,end,end-start+1);
			   }
		}
	
		//方法1：限制获取网络资源的长度
		public void downloadTest(long start,long end,long fileLength) {
			InputStream io=null;
			BufferedInputStream bio=null;
			 HttpURLConnection con=null;
			 RandomAccessFile ace=null;
			File save=new File(savePath,name);			
			 try {
				  ace=new RandomAccessFile(save,"rw");
				 
				  ace.seek(start);
				  
				  URL link=new URL(url);
				 
				   con=(HttpURLConnection)link.openConnection();
				   con.setRequestProperty("Range", "bytes="+start+"-"+end);    //[start,end]      限制获取网络资源的长度
				  con.connect();
				  
					 io=con.getInputStream();
					 bio=new BufferedInputStream(io);
					 int len;
					 byte[] by=new byte[1024];
					 while((len=bio.read(by))!=-1) {
						 ace.write(by, 0, len);
					 }
					 
					 
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
					try {
						if(bio!=null) {
						bio.close();
						}
						if(ace!=null) {
							ace.close();
						}
					if(con!=null) {
						con.disconnect();
					}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		}
		
		
		
		
		
}
