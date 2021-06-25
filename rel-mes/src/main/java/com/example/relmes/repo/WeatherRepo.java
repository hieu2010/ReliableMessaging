package com.example.relmes.repo;

import com.example.relmes.documents.MongoWeather;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherRepo extends MongoRepository<MongoWeather, String> {
}
