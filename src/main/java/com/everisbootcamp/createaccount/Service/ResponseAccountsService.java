package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Model.Response.ResponseAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResponseAccountsService {

    @Autowired
    private AccounRepository repository;

    @Autowired
    private DefineRulesService RulesService;

    public Flux<ResponseAccount> findAll() {
        List<Account> findAll = this.repository.findAll().toStream().collect(Collectors.toList());
        List<ResponseAccount> CollectionResponse = new ArrayList<ResponseAccount>();

        for (Account account : findAll) {
            ResponseAccount responseAccount = ResponseAccount
                .builder()
                .NumberAccount(account.getNumberaccount())
                .Amount(account.getAmount())
                .DateCreated(account.getDatecreated())
                .Rules(this.RulesService.SetPropertiesRules(account.getRules()))
                .TypeAccount(account.getTypeaccount())
                .build();
            CollectionResponse.add(responseAccount);
        }
        return Flux.fromIterable(CollectionResponse);
    }

    public Mono<ResponseAccount> findByNumber(String number) {
        return Mono.just(
            this.findAll()
                .toStream()
                .filter(ac -> ac.getNumberAccount().equals(number))
                .findFirst()
                .get()
        );
    }
}
