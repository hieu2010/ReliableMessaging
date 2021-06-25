package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;

import java.util.List;

public interface ServerWeatherRepoCustom {

    List<Weather> getLastX(int maxCount);

}