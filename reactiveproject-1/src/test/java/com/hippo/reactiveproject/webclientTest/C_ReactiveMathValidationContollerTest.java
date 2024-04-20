package com.hippo.reactiveproject.webclientTest;

import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.hippo.reactiveproject.controller.ReactiveMathValidationContoller;
import com.hippo.reactiveproject.models.InputFailedValidationResponse;
import com.hippo.reactiveproject.service.MyReactiveMathService;

@WebFluxTest(ReactiveMathValidationContoller.class)
public class C_ReactiveMathValidationContollerTest {
    private static final String MSG = "Allowd range is 10 - 20";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private MyReactiveMathService mathService;
    
    @Test
    void testGetSquareValueMonoErrorException() {
        WebTestClient webTestClientWithExtendedTimeout =
                webClient.mutate().responseTimeout(Duration.ofSeconds(120)).build(); //this is helpful feature for Debugging the test
        webTestClientWithExtendedTimeout.get().uri("/math/valid/reactive/square/{input}", 5)
                .exchange().expectStatus().isBadRequest()
                .expectBody(InputFailedValidationResponse.class)
                .value(v -> Assertions.assertThat(v.getMessage()).isEqualTo(MSG));
    }
    
    
}
