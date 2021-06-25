package com.example.relmes.commons.repo;

import com.example.relmes.datageneration.documents.MongoWeather;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WeatherRepo extends MongoRepository<MongoWeather, String>, WeatherRepoCustom {
}
