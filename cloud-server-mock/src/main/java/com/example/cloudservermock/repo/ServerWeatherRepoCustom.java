package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;

import java.util.List;

public interface ServerWeatherRepoCustom {

    List<Weather> getLastX(int maxCount);   // Retrieves #maxcount weather data
    Weather       findOneByTime_Local(String time_local); // Finds one weather data depending on time_local
    void          create(); // Creates a capped collection server-weather if necessary
}