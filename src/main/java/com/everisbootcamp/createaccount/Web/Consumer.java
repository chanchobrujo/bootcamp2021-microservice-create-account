package com.everisbootcamp.createaccount.Web;

import com.everisbootcamp.createaccount.Constant.Constants;
import org.springframework.web.reactive.function.client.WebClient;

public class Consumer {

    public static final WebClient webClientCustomer = WebClient.create(
        Constants.Path.CUSTOMERS_PATH
    );
    public static final WebClient webClientLogic = WebClient.create(Constants.Path.LOGIC_PATH);
}
