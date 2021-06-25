package com.example.relmes.commons.repo;

import com.example.relmes.datageneration.documents.MongoWeather;

import java.util.List;

public interface WeatherRepoCustom {

    List<MongoWeather> getLastX(int maxCount);

}
