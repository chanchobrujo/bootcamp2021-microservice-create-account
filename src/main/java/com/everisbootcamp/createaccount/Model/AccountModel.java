package com.everisbootcamp.createaccount.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {

    private String idcustomer;
    private String typeaccount;
    private Double amount;
}
