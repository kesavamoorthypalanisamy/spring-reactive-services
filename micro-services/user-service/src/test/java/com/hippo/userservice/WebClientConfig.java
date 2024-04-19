package com.hippo.userservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient getWebClientBasedOnAttributes() {
        return WebClient.builder().baseUrl("http://localhost:8092").build();
    }
    
}
