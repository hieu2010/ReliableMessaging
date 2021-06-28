package com.example.relmes.datageneration.util;

import com.example.relmes.datageneration.documents.MongoWeather;
import com.example.relmes.commons.sensor.Weather;

import java.util.Base64;
import java.util.Random;


public class Converter {

    private static final short ID_LEN = 10;
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder().withoutPadding();
    private static final Random RAND = new Random();

    private Converter() {}

    public static MongoWeather convertMeasurement(Weather weather) {

        MongoWeather mongoWeather = new MongoWeather();
        mongoWeather.setMeasurementId(generateId());
        mongoWeather.setTime(weather.getTime());
        mongoWeather.setTemp(weather.getTemp());
        mongoWeather.setRhum(weather.getRhum());
        return mongoWeather;
    }

    public static String generateId() {
        return BASE64_ENCODER.encodeToString(getRaw());
    }

    private static byte[] getRaw() {
        byte[] raw = new byte[ID_LEN];
        RAND.nextBytes(raw);
        return raw;
    }
}
