package main;

import java.util.LinkedList;

import data.Weather;

public class ReliableClient {
	private static LinkedList<Weather> queue = new LinkedList<Weather>();
	public static void main(String[] args) {
		SensorThread dt = new SensorThread(queue);
		Thread t = new Thread(dt);
		t.start();
		
		while(true) {
			Weather curr = queue.poll();
			if(curr != null) {
				// TODO: Create HTTP Request to a server and repeat until it was successful
				System.out.println("Weather data:\n" + queue.poll() + "\nQueue Size: " + queue.size());
				System.out.println("-------------------------------------");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
