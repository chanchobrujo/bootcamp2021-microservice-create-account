package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Constant.Constants;
import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Model.AccountModel;
import com.everisbootcamp.createaccount.Model.CustomerModel;
import com.everisbootcamp.createaccount.Model.Response;
import com.everisbootcamp.createaccount.Web.Consumer;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    @Autowired
    private AccounRepository repository;

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(BindingResult bindinResult) {
        Response response = new Response(bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(), HttpStatus.NOT_ACCEPTABLE);

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }

    private ResponseEntity<CustomerModel> findCustomerById(String idcustomer) {
        return Consumer.webClientCustomer
            .get()
            .uri("/".concat(idcustomer))
            .retrieve()
            .onStatus(status -> status.value() == 404, clientResponse -> Mono.empty())
            .toEntity(CustomerModel.class)
            .block();
    } 

    public Mono<Response> save(String idcustomer, AccountModel model) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = Constants.Messages.CLIENT_NOT_FOUND;

        if ( !( findCustomerById(idcustomer).getBody() == null ) ){
            if ( Constants.TYPE_ACCOUNT.stream().filter(p->p.equals( model.getTypeaccount() )).collect(Collectors.toList()).isEmpty() ) { 
                status = HttpStatus.NOT_ACCEPTABLE;
                message = Constants.Messages.INVALID_DATA;
            }else{
                Account account = new Account(idcustomer, model.getTypeaccount(), null, 0.0);
                repository.save(account).subscribe();
                
                status = HttpStatus.CREATED;
                message = Constants.Messages.CORRECT_DATA;
            }
        }

        return Mono.just(new Response(message, status));
    }

    public Flux<Account> findAll() {
        return repository.findAll();
    }
}
