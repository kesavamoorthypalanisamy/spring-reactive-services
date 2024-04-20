package com.hippo.reactiveproject.webclientTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import java.time.Duration;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.hippo.reactiveproject.controller.ReactiveMathController;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import com.hippo.reactiveproject.service.MyReactiveMathService;
import reactor.core.publisher.Flux;
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

    @Test
    public void getMultiplicationTableTest() {
        Flux<CustomResponse> serviceResponse = Flux.range(1, 3).map(CustomResponse::new);
        when(mathService.multiplicationTable(anyInt())).thenReturn(serviceResponse);
        webClient.get().uri("/math/reactive/table/{input}", 5).exchange().expectStatus()
                .is2xxSuccessful().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomResponse.class).hasSize(3);
    }

    @Test
    public void getMultiplicationTableStreamingTest() {
        Flux<CustomResponse> serviceResponse =
                Flux.range(1, 3).map(CustomResponse::new).delayElements(Duration.ofSeconds(1));
        when(mathService.multiplicationTable(anyInt())).thenReturn(serviceResponse);
        webClient.get().uri("/math/reactive/table/{input}/stream", 5).exchange().expectStatus()
                .is2xxSuccessful().expectHeader()
                .contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(CustomResponse.class).hasSize(3);
    }
    
    @Test
    public void getSquareValueQueryParamTest() {
        when(mathService.findSqure(anyInt())).thenReturn(Mono.just(new CustomResponse(25)));
        webClient.get()
                .uri(u -> u.path("/math/reactive/square").query("input={input}")
                        .build(Map.of("input", 5)))
                .exchange().expectStatus().is2xxSuccessful().expectHeader()
                .contentType(MediaType.APPLICATION_JSON).expectBody(CustomResponse.class)
                .value(v -> Assertions.assertThat(v.getOutputValue()).isEqualTo(25));
    }
    
    @Test
    public void multiplyPostTest() {
        when(mathService.multiplyTwoNumbers(any())).thenReturn(Mono.just(new CustomResponse(4)));
        webClient.post().uri("/math/reactive/multiply").accept(MediaType.APPLICATION_JSON)
                .bodyValue(MultiplyRequestDto.builder().first(1).second(4).build())
                .exchange().expectStatus().is2xxSuccessful().expectHeader()
                .contentType(MediaType.APPLICATION_JSON).expectBody(CustomResponse.class)
                .value(v -> Assertions.assertThat(v.getOutputValue()).isEqualTo(4));
    }
    
}
