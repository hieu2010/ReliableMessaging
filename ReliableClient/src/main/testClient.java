package main;

import java.util.LinkedList;

public class testClient {
	private static LinkedList<String> queue = new LinkedList<String>();
	public static void main(String[] args) {
		DataThread dt = new DataThread(queue);
		Thread t = new Thread(dt);
		t.start();
		
		while(true) {
			System.out.println("value: " + queue.poll() + " - Size: " + queue.size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
