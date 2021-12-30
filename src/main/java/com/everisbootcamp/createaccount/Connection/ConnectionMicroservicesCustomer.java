package com.everisbootcamp.createaccount.Connection;

import com.everisbootcamp.createaccount.Model.CustomerModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConnectionMicroservicesCustomer {

    public ResponseEntity<CustomerModel> findCustomerById(String idcustomer) {
        return Consumer.webClientCustomer
            .get() // 61378daca5a222750f6658b4
            .uri(UriBuilder -> UriBuilder.path("findById").queryParam("id", idcustomer).build())
            .retrieve()
            .toEntity(CustomerModel.class)
            .block();
    }
}
