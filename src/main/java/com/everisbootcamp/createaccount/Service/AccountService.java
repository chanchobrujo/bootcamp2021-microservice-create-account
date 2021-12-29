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
import com.everisbootcamp.createaccount.Web.Consumer;
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

    private ResponseEntity<CustomerModel> findCustomerById(String idcustomer) {
        return Consumer.webClientCustomer
            .get()
            .uri("/".concat(idcustomer))
            .retrieve()
            .onStatus(status -> status.value() == 404, clientResponse -> Mono.empty())
            .toEntity(CustomerModel.class)
            .block();
    }

    private Long accountByTypeCustomer(String idcustomer, String typeaccount) {
        return repository
            .findAll()
            .collectList()
            .block()
            .stream()
            .filter(a -> a.getIdcustomer().equals(idcustomer))
            .filter(aa -> aa.getTypeaccount().equals(typeaccount))
            .count();
    }

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(
        BindingResult bindinResult
    ) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }

    private Boolean filterCreatedAccountByTypeCustomer(
        String idcustomer,
        String typecustomer,
        String typeaccount
    ) {
        Boolean filter = false;

        if (typecustomer.equals("Personal")) if (
            accountByTypeCustomer(idcustomer, typeaccount) == 1
        ) filter = true;
        if (typecustomer.equals("Empresarial")) if (
            typeaccount.equals("Cuenta de ahorro") || typeaccount.equals("Cuenta corriente")
        ) filter = true;

        return filter;
    }

    public Mono<Response> save(String idcustomer, AccountModel model) {
        Response response = new Response();

        Boolean verifyEmptyCustomer = Objects.isNull(findCustomerById(idcustomer).getBody());
        Boolean verifyEmptyTypeAccount = TypeAccount.FindByName(model.getTypeaccount()).isEmpty();

        if (verifyEmptyCustomer || verifyEmptyTypeAccount) {
            response = new Response(MessagesError.NOTFOUND_DATA);
        } else {
            String typecustomer = findCustomerById(idcustomer).getBody().getTypecustomer();

            if (
                filterCreatedAccountByTypeCustomer(idcustomer, typecustomer, model.getTypeaccount())
            ) {
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
