package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Connection.ConnectionMicroservicesCustomer;
import com.everisbootcamp.createaccount.Connection.ConnectionMicroservicesLogic;
import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesError;
import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesSuccess;
import com.everisbootcamp.createaccount.Constant.Enums.Types.TypeAccount;
import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Model.Request.RequestAccount;
import com.everisbootcamp.createaccount.Model.Request.RequestUpdateBalance;
import com.everisbootcamp.createaccount.Model.Response.Response;
import com.everisbootcamp.createaccount.Model.Response.ResponseCustomer;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    @Autowired
    private AccounRepository repository;

    @Autowired
    private RulesService rulesService;

    @Autowired
    private ConnectionMicroservicesCustomer ConnectionMicroservicesCustomer;

    @Autowired
    private ConnectionMicroservicesLogic ConnectionMicroservicesLogic;

    public Mono<Response> save(String idcustomer, RequestAccount model) {
        Response response = new Response();
        ResponseEntity<ResponseCustomer> modelCustomer =
            this.ConnectionMicroservicesCustomer.findCustomerById(idcustomer);

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
                String numberaccount = this.ConnectionMicroservicesLogic.generatedNumberRandom(8);
                Account account = Account
                    .builder()
                    .idcustomer(idcustomer)
                    .typeaccount(model.getTypeaccount())
                    .amount(0.0)
                    .numberaccount(numberaccount)
                    .build();

                Integer max = model.getMaximumLimitMonthlyMovementsQuantity();
                Map<String, Object> rule =
                    this.rulesService.defineRules(
                            typecustomer,
                            account.getTypeaccount(),
                            model.getProfile(),
                            max
                        );

                account.setRules(rule);
                repository.save(account).subscribe();
                response = new Response(MessagesSuccess.SUCCESS_REGISTER);
            }
        }

        return Mono.just(response);
    }

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

    public Mono<Account> findByNumber(String number) {
        return repository.findByNumberaccount(number);
    }
}
