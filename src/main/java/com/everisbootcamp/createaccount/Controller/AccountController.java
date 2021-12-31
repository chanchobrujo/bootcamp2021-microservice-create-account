package com.everisbootcamp.createaccount.Controller;

import com.everisbootcamp.createaccount.Error.ResponseBindingResultErrors;
import com.everisbootcamp.createaccount.Model.Request.RequestAccount;
import com.everisbootcamp.createaccount.Model.Request.RequestUpdateBalance;
import com.everisbootcamp.createaccount.Model.Response.ResponseAccount;
import com.everisbootcamp.createaccount.Service.ResponseAccountsService;
import com.everisbootcamp.createaccount.Service.SaveAccountService;
import com.everisbootcamp.createaccount.Service.UpdateBalanceService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class AccountController {

    @Autowired
    private SaveAccountService AccountService;

    @Autowired
    private UpdateBalanceService UpdateBalanceService;

    @Autowired
    private ResponseAccountsService ResponseAccounts;

    @Autowired
    private ResponseBindingResultErrors responseBindingResultErrors;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<ResponseAccount>>> findByAll() {
        return Mono.just(ResponseEntity.ok().body(this.ResponseAccounts.findAll()));
    }

    @GetMapping("/findById")
    public Mono<ResponseEntity<ResponseAccount>> findByNumber(@RequestParam String number) {
        return this.ResponseAccounts.findByNumber(number)
            .map(mapper -> ResponseEntity.ok().body(mapper))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Map<String, Object>>> save(
        @RequestParam String idcustomer,
        @RequestBody @Valid RequestAccount model,
        BindingResult bindinResult
    ) {
        if (bindinResult.hasErrors()) return this.responseBindingResultErrors.BindingResultErrors(
                bindinResult
            );
        return this.AccountService.save(idcustomer, model)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/updateBalance")
    public Mono<ResponseEntity<Map<String, Object>>> updateBalance(
        @RequestBody RequestUpdateBalance model,
        BindingResult bindinResult
    ) {
        if (bindinResult.hasErrors()) return this.responseBindingResultErrors.BindingResultErrors(
                bindinResult
            );
        return this.UpdateBalanceService.updateBalance(model)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
