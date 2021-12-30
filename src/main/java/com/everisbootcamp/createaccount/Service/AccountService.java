package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Constant.Enums.MessagesError;
import com.everisbootcamp.createaccount.Constant.Enums.MessagesSuccess;
import com.everisbootcamp.createaccount.Constant.Enums.TypeAccount;
import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Model.AccountModel;
import com.everisbootcamp.createaccount.Model.CustomerModel;
import com.everisbootcamp.createaccount.Model.Response;
import com.everisbootcamp.createaccount.Model.updateBalanceModel;
import com.everisbootcamp.createaccount.Web.WebClientCustomer;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private RulesService rulesService;

    @Autowired
    private WebClientCustomer WebClientCustomer;

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(
        BindingResult bindinResult
    ) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }

    public Mono<Response> save(String idcustomer, AccountModel model) {
        Response response = new Response();
        ResponseEntity<CustomerModel> modelCustomer =
            this.WebClientCustomer.findCustomerById(idcustomer);

        Boolean verifyEmptyCustomer = Objects.isNull(modelCustomer.getBody());
        Boolean verifyEmptyTypeAccount = TypeAccount.FindByName(model.getTypeaccount()).isEmpty();

        if (verifyEmptyCustomer || verifyEmptyTypeAccount) {
            response = new Response(MessagesError.NOTFOUND_DATA);
        } else {
            String typecustomer = modelCustomer.getBody().getTypecustomer();
            Boolean verifyFilter =
                this.rulesService.filterCreatedAccountByTypeCustomer(
                        idcustomer,
                        typecustomer,
                        model.getTypeaccount()
                    );

            if (verifyFilter) {
                response = new Response(MessagesError.CLIENT_ACCOUNT_DENIED);
            } else {
                Account account = new Account(idcustomer, model.getTypeaccount(), null, 0.0);

                account.setRules(
                    rulesService.addRule(
                        typecustomer,
                        account.getTypeaccount(),
                        account.getProfile(),
                        model.getMaximumLimitMonthlyMovementsQuantity()
                    )
                );
                repository.save(account).subscribe();
                response = new Response(MessagesSuccess.SUCCESS_REGISTER);
            }
        }

        return Mono.just(response);
    }

    public Mono<Response> updateBalance(updateBalanceModel model) {
        Response response = new Response();

        try {
            Account account = repository
                .findByNumberaccount(model.getNumberaccount())
                .map(
                    mapper -> {
                        mapper.setAmount(model.getBalance());
                        return mapper;
                    }
                )
                .block();
            repository.save(account).subscribe();
        } catch (Exception e) {}

        return Mono.just(response);
    }

    public Mono<Account> findByNumber(String number) {
        return repository.findByNumberaccount(number);
    }

    public Flux<Account> findAll() {
        return repository.findAll();
    }
}
