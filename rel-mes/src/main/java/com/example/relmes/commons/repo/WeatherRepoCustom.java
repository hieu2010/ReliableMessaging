package com.example.relmes.commons.repo;

import com.example.relmes.datageneration.documents.MongoWeather;

import java.util.List;

public interface WeatherRepoCustom {

    List<MongoWeather> getLast(int maxCount);
    List<MongoWeather> getLogEntries();
    void addLogEntries(List<MongoWeather> data);
}
