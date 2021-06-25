package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import com.mongodb.client.result.DeleteResult;

import java.util.List;

public interface ServerWeatherRepoCustom {

    List<Weather> getLastX(int maxCount);
    Weather       findOneByTime_Local(String time_local);
    DeleteResult removeFirstX(int maxCount);
}