package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Model.CustomerModel;
import com.everisbootcamp.createaccount.Model.Response;
import com.everisbootcamp.createaccount.Web.Consumer;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountService {

    //@Autowired
    //private AccounRepository repository;

    private ResponseEntity<CustomerModel> findById() {
        return Consumer.webClientCustomer
            .get()
            .uri("/61378daca5a222750f6658b4d")
            .retrieve()
            .onStatus(status -> status.value() == 404, clientResponse -> Mono.empty())
            .toEntity(CustomerModel.class)
            .block();
    }

    public String find() {
        if (findById().getBody() == null) log.info(
            " +++ " + "no hay nada mi king color kong."
        ); else log.info(" --- " + "GAAAAAAA.");

        return "-";
    }

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(BindingResult bindinResult) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }
}
