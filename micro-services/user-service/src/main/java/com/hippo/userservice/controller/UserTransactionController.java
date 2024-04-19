package com.hippo.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hippo.userservice.models.TransactionRequestDto;
import com.hippo.userservice.models.TransactionResponseDto;
import com.hippo.userservice.models.UserTransactionsDto;
import com.hippo.userservice.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {
    private final UserTransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTrasaction(
            @RequestBody Mono<TransactionRequestDto> entityMono) {
        return entityMono.flatMap(e -> transactionService.createTransaction(e));
    }
    
    @GetMapping("{userId}")
    public Flux<UserTransactionsDto> getMethodName(@PathVariable("userId") Integer userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    @GetMapping("{userId}/extra")
    public Mono<ResponseEntity<Flux<UserTransactionsDto>>> getMethodNameExtra(
            @PathVariable("userId") Integer userId) {
        Flux<UserTransactionsDto> transactionsByUserId =
                transactionService.getTransactionsByUserId(userId);
        return transactionsByUserId.hasElements().filter(b -> b.booleanValue())
                .map(b -> ResponseEntity.ok().body(transactionsByUserId))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
