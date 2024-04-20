package com.hippo.reactiveproject.webclientTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.hippo.reactiveproject.controller.ReactiveMathController;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.service.MyReactiveMathService;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class B_ControllerGetTestUsingFluxTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private MyReactiveMathService mathService;

    @Test
    public void reactiveMathFluentAssertionTest() {
        when(mathService.findSqure(anyInt())).thenReturn(Mono.just(new CustomResponse(25)));
        webClient.get().uri("/math/reactive/square/{input}", 5).exchange().expectStatus()
                .is2xxSuccessful().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomResponse.class)
                .value(v -> Assertions.assertThat(v.getOutputValue()).isEqualTo(25));
    }
    
}
