package com.example.relmes.datageneration.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("weather")
public class MongoWeather {

    @Id
    private String measurementId;

    private float temp; // The air temperature in °C
    private int rhum; // The relative humidity in percent (%)

    Instant time = null;

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
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

    @Override
    public String toString() {
        return time + " " + temp + " " + rhum + " " + measurementId + "\n";
    }
}
