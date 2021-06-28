package com.example.cloudservermock.repo;

import com.example.cloudservermock.command.Command;
import com.example.cloudservermock.command.MongoCommand;
import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;

import java.time.Instant;
import java.util.List;

public interface ServerWeatherRepoCustom {

    List<Weather> getLast(int maxCount);   // Retrieves #maxcount weather data
    Weather findOneById(String measurementId); // Finds one weather data depending on time_local
    void          create(); // Creates a capped collection server-weather if necessary
    List<Weather> getNoOlderThan(short min);
    List<Weather> getWeatherData();
    void addWeatherLogEntries(List<Weather> weatherData);
    List<Weather> getWeatherLogEntries();
}
