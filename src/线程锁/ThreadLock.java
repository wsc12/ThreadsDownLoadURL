package Ïß³ÌËø;

import java.util.Vector;

public class ThreadLock implements Runnable {

	private  Vector<String> vc=new Vector<String>();
	
	public ThreadLock() {
		vc.add("1");
		vc.add("2");
		vc.add("3");
		vc.add("4");
		vc.add("5");
		vc.add("6");
		vc.add("7");
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 
		 for(int i=0;i<50;i++) {
			 System.out.println(i);
		 }
	}
	
	public void getVec() {
		int index=vc.size()-1;
		System.out.println(vc.get(index));
	}
	
	public void deleteVec() {
		int index=vc.size()-1;
		vc.remove(index);
	}
	
	public static void main(String[] args) {
		ThreadLock rb=new ThreadLock();
		   for(int i=0;i<5;i++) {
			  new Thread(rb).start();
		   }
	}
	
}
