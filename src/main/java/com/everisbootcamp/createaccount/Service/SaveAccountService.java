package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Connection.ConnectionMicroservicesCustomer;
import com.everisbootcamp.createaccount.Connection.ConnectionMicroservicesLogic;
import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesError;
import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesSuccess;
import com.everisbootcamp.createaccount.Constant.Enums.Types.TypeAccount;
import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Model.Request.RequestAccount;
import com.everisbootcamp.createaccount.Model.Response.Response;
import com.everisbootcamp.createaccount.Model.Response.ResponseCustomer;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveAccountService {

    @Autowired
    private AccounRepository repository;

    @Autowired
    private DefineRulesService rulesService;

    @Autowired
    private ConnectionMicroservicesCustomer ConnectionMicroservicesCustomer;

    @Autowired
    private ConnectionMicroservicesLogic ConnectionMicroservicesLogic;

    private Boolean FilterCATC(String IDC, String TYC, String TYA) {
        return this.rulesService.filterCreatedAccountByTypeCustomer(IDC, TYC, TYA);
    }

    public Mono<Response> save(String idcustomer, RequestAccount model) {
        Response response = new Response();
        ResponseEntity<ResponseCustomer> modelCustomer =
            this.ConnectionMicroservicesCustomer.findCustomerById(idcustomer);

        Boolean verifyEmptyCustomer = Objects.isNull(modelCustomer.getBody());
        Boolean verifyEmptyTypeAccount = TypeAccount.FindByName(model.getTypeaccount()).isEmpty();

        if (verifyEmptyCustomer || verifyEmptyTypeAccount) {
            response = new Response(MessagesError.NOTFOUND_DATA);
        } else {
            String IDC = modelCustomer.getBody().getIdcustomer();
            String TYC = modelCustomer.getBody().getTypecustomer();
            String TYA = model.getTypeaccount();
            String PROFILE = model.getProfile();
            Boolean verifyFilter = this.FilterCATC(IDC, TYC, TYA);

            if (verifyFilter) {
                response = new Response(MessagesError.CLIENT_ACCOUNT_DENIED);
            } else {
                String numberaccount = this.ConnectionMicroservicesLogic.generatedNumberRandom(8);
                Account account = Account
                    .builder()
                    .numberaccount(numberaccount)
                    .idcustomer(IDC)
                    .typeaccount(TYA)
                    .amount(0.0)
                    .build();

                Integer max = model.getMaximumLimitMonthlyMovementsQuantity();
                Map<String, Object> rule = this.rulesService.defineRules(TYC, TYA, PROFILE, max);

                account.setRules(rule);
                repository.save(account).subscribe();
                response = new Response(MessagesSuccess.SUCCESS_REGISTER);
            }
        }

        return Mono.just(response);
    }
}
