package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Interface.AccounRepository;
import com.everisbootcamp.createaccount.Web.WebClientCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterServiceAccount {

    @Autowired
    private AccounRepository repository;

    @Autowired
    private RulesService rulesService;

    @Autowired
    private WebClientCustomer WebClientCustomer;

    public Long accountByTypeCustomer(String idcustomer, String typeaccount) {
        return repository
            .findAll()
            .collectList()
            .block()
            .stream()
            .filter(a -> a.getIdcustomer().equals(idcustomer))
            .filter(aa -> aa.getTypeaccount().equals(typeaccount))
            .count();
    }
}
