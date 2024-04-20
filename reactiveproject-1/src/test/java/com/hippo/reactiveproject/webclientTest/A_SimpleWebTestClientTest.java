package com.hippo.reactiveproject.webclientTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.hippo.reactiveproject.models.CustomResponse;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class A_SimpleWebTestClientTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void reactiveMathSquareStepVerifierTest() {
        Flux<CustomResponse> responseFlux = webClient.get().uri("/math/reactive/square/{input}", 5)
                .exchange().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(CustomResponse.class).getResponseBody();
        StepVerifier.create(responseFlux).expectNextMatches(r -> r.getOutputValue() == 25)
                .verifyComplete();
    }

    @Test
    public void reactiveMathFluentAssertionTest() {
        webClient.get().uri("/math/reactive/square/{input}", 5).exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomResponse.class)
                // .isEqualTo(null) --> like this also can be used
                .value(v -> Assertions.assertThat(v.getOutputValue()).isEqualTo(25));
    }
    
}
