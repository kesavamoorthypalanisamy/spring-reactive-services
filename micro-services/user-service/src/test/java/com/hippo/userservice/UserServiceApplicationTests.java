package com.hippo.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import com.hippo.userservice.models.TransactionRequestDto;
import com.hippo.userservice.models.TransactionResponseDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserServiceApplicationTests {
	@Autowired
    protected WebClient webClient;

	@Test
	void createTransactionTest() {
		TransactionRequestDto requestDto = new TransactionRequestDto();
		requestDto.setAmount(10);
		requestDto.setUserId(1);
		Mono<TransactionResponseDto> responseMono = webClient.post().uri("/user/transaction").bodyValue(requestDto).retrieve()
				.bodyToMono(TransactionResponseDto.class).doOnNext(System.out::println);
		StepVerifier.create(responseMono).expectNextCount(1).verifyComplete();
		
		
	}

}
