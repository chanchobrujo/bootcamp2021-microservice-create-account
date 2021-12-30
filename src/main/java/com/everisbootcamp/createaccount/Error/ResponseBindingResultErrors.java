package com.everisbootcamp.createaccount.Error;

import com.everisbootcamp.createaccount.Model.Response.Response;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Mono;

@Service
public class ResponseBindingResultErrors {

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(
        BindingResult bindinResult
    ) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }
}
