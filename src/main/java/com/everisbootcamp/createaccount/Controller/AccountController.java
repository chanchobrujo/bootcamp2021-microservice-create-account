package com.everisbootcamp.createaccount.Controller;

import com.everisbootcamp.createaccount.Model.AccountModel;
import com.everisbootcamp.createaccount.Service.AccountService;

import java.util.Map;
import javax.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import reactor.core.publisher.Mono; 

@RestController
@RequestMapping
public class AccountController {
    @Autowired
    private AccountService service;
    
    @GetMapping("/")
    public Mono<ResponseEntity<String>> findByAll() {
        return Mono.just( ResponseEntity.ok().body(service.find()) );
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Map<String, Object>>> save(
        @RequestBody @Valid AccountModel model,
        BindingResult bindinResult
    ) {
        return null;
    }
}
