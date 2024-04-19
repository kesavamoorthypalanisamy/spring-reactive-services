package com.hippo.reactiveproject;

import org.junit.jupiter.api.Test;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PostRequestTest extends BaseTest {
    
    @Test
    public void postTest() {
        Mono<CustomResponse> responseMono = webClient.post().uri("math/reactive/multiply")
                .bodyValue(MultiplyRequestDto.builder().first(1).second(3).build()).retrieve()
                .bodyToMono(CustomResponse.class).doOnNext(System.out::println);
        StepVerifier.create(responseMono).expectNextMatches(r -> r.getOutputValue() == 3)
                .verifyComplete();

    }
    
    

    
}
