package com.everisbootcamp.createaccount.Constant.Enums;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeAccount {
    ACCOUNT_FIX("Cuenta plazo fijo.", true, true),
    ACCOUNT_SAV("Cuenta de ahorro.", true, true),
    ACCOUNT_COR("Cuenta corriente.", true, true);

    private String typeaccount;
    private Boolean commissionMaintenance;
    private Boolean maximumLimitMonthlyMovements;

    public static Optional<TypeAccount> FindByName(String name) {
        for (TypeAccount type : values()) {
            Boolean verify = type.getTypeaccount().toUpperCase().equals(name.toUpperCase());
            if (verify) return Optional.of(type);
        }
        return Optional.empty();
    }
}
