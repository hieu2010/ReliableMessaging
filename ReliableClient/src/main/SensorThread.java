package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import data.Weather;

public class SensorThread implements Runnable{

	private List<Weather> queue;
	private List<Weather> weatherData;
	
	public SensorThread(LinkedList<Weather> queue) {
		this.queue = queue;
		
		// Loading data.csv
		this.weatherData = new ArrayList<Weather>();
		readData();
	}
	
	private void readData() {
		URL csvPath = SensorThread.class.getResource("../data/data.csv");
		try (BufferedReader br = new BufferedReader(new FileReader(csvPath.getPath()))) {

            // skip first line of csv
            String line = br.readLine();
            line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Weather curr = Weather.createWeather(attributes);

                // adding book into ArrayList
                weatherData.add(curr);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
	}

	@Override
	public void run() {
		// Repeatedly add weather data to the queue
		while(true) {
			for(int i=0; i<weatherData.size(); i++) {
				queue.add(weatherData.get(i));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
