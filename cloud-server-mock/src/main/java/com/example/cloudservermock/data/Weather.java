package com.example.cloudservermock.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Base64;
import java.util.Random;

@Document("server-weather")
public class Weather {

    private static final short ID_LEN = 10;
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder().withoutPadding();
    private static final Random RAND = new Random();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        return time + " " + temp + " " + rhum + "\n";
    }

    public static Weather csvToString(String weatherString) {
        String[] var = weatherString.split(" ");
        Weather toReturn = new Weather();
        String[] splittedLocalString = var[0].split("T");
        String time = splittedLocalString[0] + " " + splittedLocalString[1];
        Instant instant = extractInstant(time.substring(0, 19));

        toReturn.setMeasurementId(generateId());
        toReturn.setTemp(Float.parseFloat(var[1]));
        toReturn.setRhum(Integer.parseInt(var[2]));
        toReturn.setTime(instant);
        return toReturn;
    }

    private static Instant extractInstant(String time) {
        TemporalAccessor temporalAccessor = FORMATTER.parse(time);
        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Instant result = Instant.from(zonedDateTime);
        return result;
    }

    private static String generateId() {
        return BASE64_ENCODER.encodeToString(getRaw());
    }

    private static byte[] getRaw() {
        byte[] raw = new byte[ID_LEN];
        RAND.nextBytes(raw);
        return raw;
    }
}
