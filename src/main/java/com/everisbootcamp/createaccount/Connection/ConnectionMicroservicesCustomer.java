package com.everisbootcamp.createaccount.Connection;

import com.everisbootcamp.createaccount.Model.Response.ResponseCustomer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConnectionMicroservicesCustomer {

    public ResponseEntity<ResponseCustomer> findCustomerById(String idcustomer) {
        return Consumer.webClientCustomer
            .get() // 61378daca5a222750f6658b4
            .uri(UriBuilder -> UriBuilder.path("findById").queryParam("id", idcustomer).build())
            .retrieve()
            .toEntity(ResponseCustomer.class)
            .block();
    }
}
