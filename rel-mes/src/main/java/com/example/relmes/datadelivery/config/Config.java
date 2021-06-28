package com.example.relmes.datadelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient getWebClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://cloud-component-htqvfl4nhq-ew.a.run.app/")
                //.baseUrl("http://localhost:8080")
                //.baseUrl("http://34.79.133.36:8080/")
                .build();
        return webClient;
    }
}
