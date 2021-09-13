package com.everisbootcamp.createaccount.Controller;

import com.everisbootcamp.createaccount.Data.Account;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping
public class AccountController {

    @GetMapping("/")
    public Mono<ResponseEntity<String>> findByAll() {
        Account account = new Account("customer", "Plazo fijo.", null, 0.0); 
        log.info(account.toString());
        log.info("ID: "+account.getIdaccount()); 
        return Mono.just(ResponseEntity.ok().body("HOLA MI KING COLOR KONG"));
    }
}
