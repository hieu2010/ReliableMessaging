package com.example.relmes.commons.sensor;

import java.time.Instant;

public class Weather {

    private float temp; // The air temperature in Â°C
    private int rhum; // The relative humidity in percent (%)
    private Instant time; // current time

    public Weather(float temp, int rhum) {
        this.temp = temp;
        this.rhum = rhum;
        this.time = Instant.now();
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getRhum() {
        return rhum;
    }

    public void setRhum(int rhum) {
        this.rhum = rhum;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public static Weather createWeather(String temp, String hum) {
        return new Weather(
                Float.parseFloat(temp),
                Integer.parseInt(hum));
    }

    @Override
    public String toString() {
        return "[time=" + time + ", temp=" + temp + ", rhum=" + rhum + "]";
    }
}
