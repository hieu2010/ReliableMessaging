package main;

import java.util.LinkedList;

public class DataThread implements Runnable{

	private LinkedList<String> queue;
	
	public DataThread(LinkedList<String> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		int i=0;
		while(true) {
			queue.add("test" + i);
			i++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
