package com.everisbootcamp.createaccount.Connection;

import com.everisbootcamp.createaccount.Constant.Paths.Path;
import org.springframework.web.reactive.function.client.WebClient;

public class Consumer {

    public static final WebClient webClientCustomer = WebClient.create(Path.URLS.CUSTOMERS_PATH);
    public static final WebClient webClientLogic = WebClient.create(Path.URLS.LOGIC_PATH);
}
