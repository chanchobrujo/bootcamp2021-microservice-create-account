package com.everisbootcamp.createaccount.Constant.Enums.Types;

import com.everisbootcamp.createaccount.Common.Utils;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeAccount {
    ACCOUNT_SAV("Cuenta de ahorro.", false, true),
    ACCOUNT_COR("Cuenta corriente.", true, false),
    ACCOUNT_FIX("Cuenta plazo fijo.", false, true);

    private String typeaccount;
    private Boolean commissionMaintenance;
    private Boolean maximumLimitMonthlyMovements;

    public static Optional<TypeAccount> FindByName(String name) {
        for (TypeAccount type : values()) {
            Boolean verify = Utils.equalsOrContains(type.getTypeaccount(), name);
            if (verify) return Optional.of(type);
        }
        return Optional.empty();
    }
}
