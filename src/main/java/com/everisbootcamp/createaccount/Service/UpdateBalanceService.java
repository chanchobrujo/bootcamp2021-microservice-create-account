package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesError;
import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesSuccess;
import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Model.Request.RequestUpdateBalance;
import com.everisbootcamp.createaccount.Model.Response.Response;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateBalanceService {

    @Autowired
    private AccounRepository repository;

    public Mono<Response> updateBalance(RequestUpdateBalance model) {
        Response response = new Response(MessagesError.NOTFOUND_DATA);
        Optional<Account> accountM =
            this.repository.findAll()
                .toStream()
                .filter(ac -> ac.getNumberaccount().equals(model.getNumberaccount()))
                .findFirst();
        if (accountM.isPresent()) {
            Account account = accountM
                .map(
                    mapper -> {
                        mapper.setAmount(model.getBalance());
                        return mapper;
                    }
                )
                .get();
            repository.save(account).subscribe();
            response = new Response(MessagesSuccess.SUCCESS_REGISTER);
        }
        return Mono.just(response);
    }
}
