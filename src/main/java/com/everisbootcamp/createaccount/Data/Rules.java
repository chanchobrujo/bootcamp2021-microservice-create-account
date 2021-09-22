package com.everisbootcamp.createaccount.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rules {

    private String customerType;
    private boolean commissionMaintenance;
    private boolean maximumLimitMonthlyMovements;
    private Integer maximumLimitMonthlyMovementsQuantity;
}
