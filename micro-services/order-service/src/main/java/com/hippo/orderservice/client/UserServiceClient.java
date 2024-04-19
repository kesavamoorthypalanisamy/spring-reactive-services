package com.hippo.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.hippo.orderservice.models.TransactionRequestDto;
import com.hippo.orderservice.models.TransactionResponseDto;
import reactor.core.publisher.Mono;

@Service
public class UserServiceClient {
    private final WebClient webClient;

    public UserServiceClient(@Value("${user.service.url}") String urlString) {
        this.webClient = WebClient.builder().baseUrl(urlString).build();
    }
    
    public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto requestDto) {
        return webClient.post().uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }
    
}
