package com.everisbootcamp.createaccount.Controller;

import com.everisbootcamp.createaccount.Data.Account;
import com.everisbootcamp.createaccount.Model.AccountModel;
import com.everisbootcamp.createaccount.Service.AccountService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<Account>>> findByAll() {
        return Mono.just(ResponseEntity.ok().body(service.findAll()));
    }

    @PostMapping("/save/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> save(@PathVariable("id") String id, @RequestBody @Valid AccountModel model, BindingResult bindinResult) {
        if (bindinResult.hasErrors()) return service.BindingResultErrors(bindinResult);
        return service
            .save(id, model)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
