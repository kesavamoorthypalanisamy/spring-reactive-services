package com.hippo.userservice.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.hippo.userservice.entity.User;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    String updateBalanceQuery = """
              update users
              set balance = balance - :amount
              where id = :userId and balance >= :amount
            """;
    
    @Modifying
    @Query(updateBalanceQuery)
    Mono<Boolean> updateUserBalance(Integer userId,Integer amount);
}
