package com.everisbootcamp.createaccount.Constant.Enums.Types;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeCustomer {
    PERSONAL("PERSONAL"),
    EMPRESARIAL("EMPRESARIAL");

    private String name;

    public static Optional<TypeCustomer> FindByName(String name) {
        for (TypeCustomer type : values()) {
            Boolean verify = type.getName().toUpperCase().equals(name.toUpperCase());
            if (verify) return Optional.of(type);
        }
        return Optional.empty();
    }
}
