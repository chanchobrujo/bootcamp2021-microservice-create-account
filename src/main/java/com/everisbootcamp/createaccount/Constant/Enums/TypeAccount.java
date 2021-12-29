package com.everisbootcamp.createaccount.Constant.Enums;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeAccount {
    ACCOUNT_FIX("Cuenta plazo fijo."),
    ACCOUNT_SAV("Cuenta de ahorro."),
    ACCOUNT_COR("Cuenta corriente.");

    private String name;

    public static Optional<TypeAccount> FindByName(String name) {
        for (TypeAccount type : values()) {
            Boolean verify = type.getName().toUpperCase().equals(name.toUpperCase());
            if (verify) return Optional.of(type);
        }
        return Optional.empty();
    }
}
