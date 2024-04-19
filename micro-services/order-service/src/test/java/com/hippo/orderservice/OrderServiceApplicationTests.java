package com.hippo.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hippo.orderservice.client.ProductServicesClient;
import com.hippo.orderservice.client.UserServiceClient;
import com.hippo.orderservice.models.OrderRequestDto;
import com.hippo.orderservice.models.OrderResponseDto;
import com.hippo.orderservice.models.ProductDto;
import com.hippo.orderservice.models.UserDto;
import com.hippo.orderservice.service.OrderFulfilmentService;
import com.hippo.orderservice.util.EntityDtoUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {
	@Autowired
	private UserServiceClient userServiceClient;

	@Autowired
	private ProductServicesClient productServicesClient;

	@Autowired
	private OrderFulfilmentService fulfilmentService;
	
	@Test
	void getAllUsers() {
		Flux<UserDto> users = userServiceClient.getAllUsers().doOnNext(System.out::println);
		StepVerifier.create(users).expectNextCount(4).verifyComplete();
	}

	@Test
	void getAllProducts() {
		Flux<ProductDto> allProducts =
				productServicesClient.getAllProducts().doOnNext(System.out::println);
		StepVerifier.create(allProducts).expectNextCount(6).verifyComplete();
	}
	
	@Test
	void createTransactionForEachUsers() {
		Flux<OrderRequestDto> result1 = Flux.zip(userServiceClient.getAllUsers(), productServicesClient.getAllProducts())
		.map(t -> EntityDtoUtil.toOrderRequestDto(t.getT1(),t.getT2()));
		Flux<OrderResponseDto> result2 =
				result1.flatMap(orderReq -> fulfilmentService.processOrder(Mono.just(orderReq)))
						.doOnNext(System.out::println);
		StepVerifier.create(result2).expectNextCount(4).verifyComplete();
	}

}
