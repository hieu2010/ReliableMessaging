package com.example.cloudservermock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient getWebClient() {
        WebClient webClient = WebClient.builder()
                //.baseUrl("https://cloud-component-htqvfl4nhq-ew.a.run.app/")
                .baseUrl("http://localhost:8181")
                .build();
        return webClient;
    }
}
