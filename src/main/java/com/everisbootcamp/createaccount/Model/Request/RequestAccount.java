package com.everisbootcamp.createaccount.Model.Request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAccount {

    @NotBlank(message = "El campo tipo de cuenta, no debe estar vacio.")
    private String typeaccount;

    private String profile = "";

    private int maximumLimitMonthlyMovementsQuantity;
}
