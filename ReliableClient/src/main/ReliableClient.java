package main;

import java.util.LinkedList;

import data.Weather;

public class ReliableClient {
	private static LinkedList<Weather> queue = new LinkedList<Weather>();
	public static void main(String[] args) {
		SensorThread dt = new SensorThread(queue);
		Thread t = new Thread(dt);
		t.start();
		CommunicationClient client = new CommunicationClient();
		
		while(true) {
			Weather curr = queue.poll();
			if(curr != null) {
				System.out.println("Weather data:\n" + queue.poll() + "\nQueue Size: " + queue.size());
				client.sendReliableRequest(curr);
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
