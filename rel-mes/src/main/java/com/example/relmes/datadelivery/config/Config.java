package com.example.relmes.datadelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient getWebClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9000")
                .build();
        return webClient;
    }
}
