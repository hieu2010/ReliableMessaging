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

    private String time; // UTC time stamp (format: YYYY-MM-DD hh:mm:ss)
    private String time_local; // Local time stamp (format: YYYY-MM-DD hh:mm:ss); only provided if tz is set
    private float temp; // The air temperature in °C
    private float dwpt; // The dew point in °C
    private int rhum; // The relative humidity in percent (%)
    private float prcp; // The one hour precipitation total in mm
    private int snow; // The snow depth in mm
    private int wdir; // The wind direction in degrees (°)
    private float wspd; // The average wind speed in km/h
    private float wpgt; // The peak wind gust in km/h
    private float pres; // The sea-level air pressure in hPa
    private int tsun; // The one hour sunshine total in minutes (m)
    private int coco; // The weather condition code

    Instant localTimeInstant = null;

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_local() {
        return time_local;
    }

    public void setTime_local(String time_local) {
        this.time_local = time_local;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getDwpt() {
        return dwpt;
    }

    public void setDwpt(float dwpt) {
        this.dwpt = dwpt;
    }

    public int getRhum() {
        return rhum;
    }

    public void setRhum(int rhum) {
        this.rhum = rhum;
    }

    public float getPrcp() {
        return prcp;
    }

    public void setPrcp(float prcp) {
        this.prcp = prcp;
    }

    public int getSnow() {
        return snow;
    }

    public void setSnow(int snow) {
        this.snow = snow;
    }

    public int getWdir() {
        return wdir;
    }

    public void setWdir(int wdir) {
        this.wdir = wdir;
    }

    public float getWspd() {
        return wspd;
    }

    public void setWspd(float wspd) {
        this.wspd = wspd;
    }

    public float getWpgt() {
        return wpgt;
    }

    public void setWpgt(float wpgt) {
        this.wpgt = wpgt;
    }

    public float getPres() {
        return pres;
    }

    public void setPres(float pres) {
        this.pres = pres;
    }

    public int getTsun() {
        return tsun;
    }

    public void setTsun(int tsun) {
        this.tsun = tsun;
    }

    public int getCoco() {
        return coco;
    }

    public void setCoco(int coco) {
        this.coco = coco;
    }

    public Instant getLocalTimeInstant() {
        return localTimeInstant;
    }

    public void setLocalTimeInstant(Instant localTimeInstant) {
        this.localTimeInstant = localTimeInstant;
    }

    @Override
    public String toString() {
        return time_local + " " + temp + " " + rhum + "\n";
    }

    public static Weather csvToString(String weatherString) {
        String[] var = weatherString.split(" ");
        Weather toReturn = new Weather();

        String[] splittedLocalString = var[0].split("T");
        String time = splittedLocalString[0] + splittedLocalString[1];
        Instant instant = extractInstant(time);

        toReturn.setMeasurementId(generateId());
        toReturn.setTime_local(time);
        toReturn.setTemp(Float.parseFloat(var[1]));
        toReturn.setRhum(Integer.parseInt(var[2]));
        toReturn.setLocalTimeInstant(instant);
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
