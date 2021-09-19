package com.everisbootcamp.createaccount.Interface;

import com.everisbootcamp.createaccount.Data.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccounRepository extends ReactiveMongoRepository<Account, String> {
    public Mono<Account> findByIdcustomer(String idcustomer);

    public Mono<Account> findByNumberaccount(String numberaccount);

    public Mono<Account> existsByNumberaccount(String numberaccount);

    public Mono<Account> existsByIdcustomer(String idcustomer);
}
