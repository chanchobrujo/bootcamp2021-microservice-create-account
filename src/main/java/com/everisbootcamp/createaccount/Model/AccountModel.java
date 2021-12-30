package com.everisbootcamp.createaccount.Model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {

    @NotBlank(message = "El campo tipo de cuenta, no debe estar vacio.")
    private String typeaccount;

    private String profile;

    private int maximumLimitMonthlyMovementsQuantity;
}
