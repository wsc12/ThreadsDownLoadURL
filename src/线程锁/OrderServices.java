package 线程锁;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class OrderServices implements Runnable {
	private static ReentrantLock lock=new ReentrantLock();
	private static int num=0;
	private static OrderNum ord=new OrderNum();
	public static void main(String[] args) {
		  System.out.println("###生成订单号###");
		 
		  while(true) {
		  for(int i=0;i<50;i++) {
			  new Thread(new OrderServices(),"Thread"+i).start();
			
		  }
		  try {
			  Thread.sleep(700);
		  }catch (Exception e) {
			// TODO: handle exception
		}
		  if(ord.count%50!=0) {
			  System.out.println(ord.count);
			  break;
		  }else {
			  System.out.println(ord.count);
		  }
		 
	 }
		 
	}

	@Override
 	  public void run() {
		// TODO Auto-generated method stub	
		ord.getNumber();
		  
	}
	
	
}
