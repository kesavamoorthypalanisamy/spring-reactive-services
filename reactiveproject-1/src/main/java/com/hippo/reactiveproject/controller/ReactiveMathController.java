package com.hippo.reactiveproject.controller;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.models.MultiplyRequestDto;
import com.hippo.reactiveproject.service.MyReactiveMathService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("math/reactive/")
@RequiredArgsConstructor
public class ReactiveMathController {
    private final MyReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<CustomResponse> getSquareValue(@PathVariable Integer input) {
        return mathService.findSqure(input);
    }

    @GetMapping("table/{input}")
    public Flux<CustomResponse> getMultiplicationTable(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }
    
    @GetMapping(value = "table/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomResponse> getMultiplicationTableAsStream(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }
    
    @PostMapping("multiply")
    public Mono<CustomResponse> multiply(@RequestBody Mono<MultiplyRequestDto> request,
            @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return mathService.multiplyTwoNumbers(request);
    }
    
    @GetMapping("square")
    public Mono<CustomResponse> getSquareValueQueryParam(@RequestParam("input") Integer input) {
        return mathService.findSqure(input);
    }
    
}
