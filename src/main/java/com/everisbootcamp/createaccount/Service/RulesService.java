package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Constant.Enums.TypeAccount;
import com.everisbootcamp.createaccount.Constant.Enums.TypeCustomer;
import com.everisbootcamp.createaccount.Data.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    @Autowired
    private FilterServiceAccount filterServiceAccount;

    private Boolean VerifyTypeAccount(TypeAccount typeAccount, String name) {
        return typeAccount.getTypeaccount().toUpperCase().equals(name.toUpperCase());
    }

    private Boolean VerifyTypeCustomer(TypeCustomer typeCustomer, String name) {
        return typeCustomer.getName().toUpperCase().equals(name.toUpperCase());
    }

    public Rules addRule(
        String customerType,
        String accountType,
        String profile,
        Integer maximumLimitMonthlyMovementsQuantity
    ) {
        TypeAccount type = TypeAccount.FindByName(accountType).get();
        Boolean commissionMaintenance = type.getCommissionMaintenance();
        Boolean maximumLimitMonthlyMovements = type.getMaximumLimitMonthlyMovements();

        return Rules
            .builder()
            .customerType(customerType)
            .commissionMaintenance(commissionMaintenance)
            .maximumLimitMonthlyMovements(maximumLimitMonthlyMovements)
            .maximumLimitMonthlyMovementsQuantity(maximumLimitMonthlyMovementsQuantity)
            .build();
    }

    public Boolean filterCreatedAccountByTypeCustomer(
        String idcustomer,
        String typecustomer,
        String typeaccount
    ) {
        Boolean filter = false;
        Boolean VerifyTypePer = this.VerifyTypeCustomer(TypeCustomer.PERSONAL, typecustomer);
        Boolean VerifyTypeEmp = this.VerifyTypeCustomer(TypeCustomer.EMPRESARIAL, typecustomer);

        if (VerifyTypePer) {
            Long VerifyAccounts =
                this.filterServiceAccount.accountByTypeCustomer(idcustomer, typeaccount);
            filter = VerifyAccounts == 1;
        }
        if (VerifyTypeEmp) {
            Boolean VerifyTypeAccountAh =
                this.VerifyTypeAccount(TypeAccount.ACCOUNT_SAV, typeaccount);
            Boolean VerifyTypeAccountCo =
                this.VerifyTypeAccount(TypeAccount.ACCOUNT_COR, typeaccount);

            filter = VerifyTypeAccountAh || VerifyTypeAccountCo;
        }

        return filter;
    }
}
