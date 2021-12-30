package com.everisbootcamp.createaccount.Constant.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RuleName {
    CUSTOMERTYPE("Tipo de cliente"),
    COMMISSIONMAINTENANCE("Comisión por mantenimiento"),
    MAXLIMITMOVMONTHLY("Limite de movimiento por mes"),
    MAXLIMITMOVMONTHLYNUMBER("Movimientos por mes");

    private String name;
}
