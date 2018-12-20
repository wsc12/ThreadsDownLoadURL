package Ïß³ÌËø;

import java.text.SimpleDateFormat;

import java.util.concurrent.locks.ReentrantLock;

public class OrderNum {
		private  ReentrantLock lock=new ReentrantLock();
		public static  int count=0;
		
		public void getNumber() {
			lock.lock();
			try {
//				
				count++;
			}finally {
				lock.unlock();
			}
			
		
		}
	
		
}
