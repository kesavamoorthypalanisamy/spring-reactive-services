package com.hippo.userservice.service;

import org.springframework.stereotype.Service;
import com.hippo.userservice.models.TransactionRequestDto;
import com.hippo.userservice.models.TransactionResponseDto;
import com.hippo.userservice.models.TrasactionStatus;
import com.hippo.userservice.models.UserTransactionsDto;
import com.hippo.userservice.repository.UserRepository;
import com.hippo.userservice.repository.UserTransactionRepository;
import com.hippo.userservice.util.EntityMapperUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTransactionService {
    private final UserRepository userRepository;
    private final UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
            return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                            .filter(result -> result.booleanValue()) //This will check the Mono<Boolean> returns true to continue futher.
                            .map(result -> EntityMapperUtil.toTransactionEntity(requestDto))
                            .flatMap(t -> transactionRepository.save(t)) //this will return userTransaction object mono
                            .map(ut -> EntityMapperUtil.toTrasactionResponseDto(requestDto,
                                            TrasactionStatus.APPROVED))
                            .defaultIfEmpty(EntityMapperUtil.toTrasactionResponseDto(requestDto, // this is the else fallbak code if Mono<Boolean> returns false
                                            TrasactionStatus.DECLINED));
    }
    
    public Flux<UserTransactionsDto> getTransactionsByUserId(final Integer userId) {
        return transactionRepository
                        .findByUserId(userId)
                        .map(EntityMapperUtil::toUserTransactionsDto);
    }

}
