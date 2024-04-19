package com.hippo.reactiveproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    // @Bean
    // @Primary
    // public WebClient getWebClient() {
    //     return WebClient.builder().baseUrl("http://localhost:8080")
    //             // .defaultHeaders(h -> h.setBasicAuth("userName", "somePassword"))
    //             .build();
    // }

    // @Bean
    // public WebClient getWebClientWithToken() {
    //     return WebClient.builder()
    //             .baseUrl("http://localhost:8080")
    //             .defaultHeaders(h -> h.setBearerAuth("some-jwt-token"))
    //             .build();
    // }
    @Bean
    public WebClient getWebClientBasedOnAttributes() {
        return WebClient.builder().baseUrl("http://localhost:8080")
                .filter((r, f) -> sessionToken(r, f)).build();
    }
    
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        ClientRequest cr = request.attribute("auth")
                .map(v -> v.equals("basic") ? withbasicAuth(request) : withOAuth(request))
                .orElse(request);
        return ex.exchange(cr);
    }
    
    private ClientRequest withbasicAuth(ClientRequest c) {
        return ClientRequest.from(c) //c is immutable so we need to deep copy to set values
                .headers(h -> h.setBasicAuth("userName", "password")).build();
    }

    private ClientRequest withOAuth(ClientRequest c) {
        return ClientRequest.from(c) //c is immutable so we need to deep copy to set values
                .headers(h -> h.setBearerAuth("some-jwt-token")).build();
    }
}
