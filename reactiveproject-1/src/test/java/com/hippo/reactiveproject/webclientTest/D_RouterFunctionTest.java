package com.hippo.reactiveproject.webclientTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.hippo.reactiveproject.config.RouterConfig;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.service.RouteRequestHandler;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {RouterConfig.class})
public class D_RouterFunctionTest {
    
    private WebTestClient webTestClient;

    // @Autowired
    // private RouterConfig config;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private RouteRequestHandler handler;

    @BeforeAll
    public void setTestClient() {
        // webTestClient = WebTestClient.bindToRouterFunction(config.highLevelRouterFunction()).build();
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    public void test() {
        when(handler.handleSquare(any()))
                .thenReturn(ServerResponse.ok().bodyValue(new CustomResponse(25)));
        webTestClient.get().uri("/router/square/{input}", 5).exchange().expectStatus()
                .is2xxSuccessful().expectBody(CustomResponse.class)
                .value(v -> Assertions.assertThat(v.getOutputValue()).isEqualTo(25));
    }
    
}
