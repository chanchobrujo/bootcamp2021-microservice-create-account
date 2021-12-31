package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Common.Utils;
import com.everisbootcamp.createaccount.Constant.Enums.RuleName;
import com.everisbootcamp.createaccount.Constant.Enums.Types.TypeAccount;
import com.everisbootcamp.createaccount.Constant.Enums.Types.TypeCustomer;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, Object> defineRules(
        String customerType,
        String accountType,
        String profile,
        Integer MAX
    ) {
        TypeAccount type = TypeAccount.FindByName(accountType).get();

        Boolean commissionMaintenance = type.getCommissionMaintenance();
        Boolean maximumLimitMonthlyMovements = type.getMaximumLimitMonthlyMovements();
        Boolean verifyAccountFix = type
            .getTypeaccount()
            .equals(TypeAccount.ACCOUNT_FIX.getTypeaccount());
        Boolean verifyProfile = !Utils.StringEmpty(profile);

        Map<String, Object> rules = new HashMap<>();
        rules.put(RuleName.CUSTOMERTYPE.getName(), customerType);
        rules.put(RuleName.COMMISSIONMAINTENANCE.getName(), commissionMaintenance);
        rules.put(RuleName.MAXLIMITMOVMONTHLY.getName(), maximumLimitMonthlyMovements);

        if (maximumLimitMonthlyMovements) {
            if (verifyAccountFix) MAX = 1;
            rules.put(RuleName.MAXLIMITMOVMONTHLYNUMBER.getName(), MAX);
        }
        if (verifyProfile) rules.put(RuleName.PROFILE.getName(), profile);
        return rules;
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

    public Map<String, Object> SetPropertiesRules(Map<String, Object> rule) {
        String commissionMaintenance = RuleName.COMMISSIONMAINTENANCE.getName();
        String maximumLimitMonthlyMovements = RuleName.MAXLIMITMOVMONTHLY.getName();

        Boolean commissionMaintenanceValue = (Boolean) rule.get(commissionMaintenance);
        Boolean maximumLimitMonthlyMovementsValue = (Boolean) rule.get(
            maximumLimitMonthlyMovements
        );

        String rescm = Utils.BooleanToString(commissionMaintenanceValue);
        String resmxm = Utils.BooleanToString(maximumLimitMonthlyMovementsValue);

        rule.put(commissionMaintenance, rescm);
        rule.put(maximumLimitMonthlyMovements, resmxm);

        return rule;
    }
}
