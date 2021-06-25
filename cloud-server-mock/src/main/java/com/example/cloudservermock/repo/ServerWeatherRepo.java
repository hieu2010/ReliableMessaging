package com.example.cloudservermock.repo;

import com.example.cloudservermock.data.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ServerWeatherRepo extends MongoRepository<Weather, String>, ServerWeatherRepoCustom {
}
