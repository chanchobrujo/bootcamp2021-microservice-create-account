package com.everisbootcamp.createaccount.Connection;

import org.springframework.stereotype.Service;

@Service
public class ConnectionMicroservicesLogic {

    public String generatedNumberRandom(Integer size) {
        return Consumer.webClientLogic
            .get()
            .uri(UriBuilder -> UriBuilder.path("generatednumber").queryParam("size", size).build())
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
