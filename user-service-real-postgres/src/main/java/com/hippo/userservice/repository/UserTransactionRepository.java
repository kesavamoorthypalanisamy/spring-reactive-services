package com.hippo.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.hippo.userservice.entity.UserTransaction;
import reactor.core.publisher.Flux;


@Repository
public interface UserTransactionRepository
        extends ReactiveCrudRepository<UserTransaction, Integer> {

    Flux<UserTransaction> findByUserId(Integer userId);

}

