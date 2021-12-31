package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Interface.AccounRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterAccountService {

    @Autowired
    private AccounRepository repository;

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
