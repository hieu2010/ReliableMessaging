package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;

import java.time.Instant;
import java.util.List;

public interface ServerWeatherRepoCustom {

    List<Weather> getLastX(int maxCount);   // Retrieves #maxcount weather data
    Weather findOneByTime(Instant time); // Finds one weather data depending on time_local
    void          create(); // Creates a capped collection server-weather if necessary
    List<Weather> getNoOlderThan(short min);
    List<Weather> getWeatherData();
}