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
public class DefineRulesService {

    @Autowired
    private FilterAccountService filterServiceAccount;

    private Boolean VerifyTypeAccount(TypeAccount typeAccount, String name) {
        return typeAccount.getTypeaccount().toUpperCase().equals(name.toUpperCase());
    }

    private Boolean VerifyTypeCustomer(TypeCustomer typeCustomer, String name) {
        return typeCustomer.getName().toUpperCase().equals(name.toUpperCase());
    }

    public Map<String, Object> defineRules(String TYC, String TYA, String PRO, Integer MAX) {
        TypeAccount type = TypeAccount.FindByName(TYA).get();

        Boolean commissionMaintenance = type.getCommissionMaintenance();
        Boolean maximumLimitMonthlyMovements = type.getMaximumLimitMonthlyMovements();
        Boolean verifyAccountFix = type
            .getTypeaccount()
            .equals(TypeAccount.ACCOUNT_FIX.getTypeaccount());
        Boolean verifyProfile = !Utils.StringEmpty(PRO);

        Map<String, Object> rules = new HashMap<>();
        rules.put(RuleName.CUSTOMERTYPE.getName(), TYC);
        rules.put(RuleName.COMMISSIONMAINTENANCE.getName(), commissionMaintenance);
        rules.put(RuleName.MAXLIMITMOVMONTHLY.getName(), maximumLimitMonthlyMovements);

        if (maximumLimitMonthlyMovements) {
            if (verifyAccountFix) MAX = 1;
            rules.put(RuleName.MAXLIMITMOVMONTHLYNUMBER.getName(), MAX);
        }
        if (verifyProfile) rules.put(RuleName.PROFILE.getName(), PRO);
        return rules;
    }

    public Boolean filterCreatedAccountByTypeCustomer(String IDC, String TYC, String TYA) {
        Boolean filter = false;
        Boolean VerifyTypePer = this.VerifyTypeCustomer(TypeCustomer.PERSONAL, TYC);
        Boolean VerifyTypeEmp = this.VerifyTypeCustomer(TypeCustomer.EMPRESARIAL, TYC);

        if (VerifyTypePer) {
            Long VerifyAccounts = this.filterServiceAccount.accountByTypeCustomer(IDC, TYA);
            filter = VerifyAccounts == 1;
        }
        if (VerifyTypeEmp) {
            Boolean VerifyTypeAccountAh = this.VerifyTypeAccount(TypeAccount.ACCOUNT_SAV, TYA);
            Boolean VerifyTypeAccountCo = this.VerifyTypeAccount(TypeAccount.ACCOUNT_COR, TYA);

            filter = VerifyTypeAccountAh || VerifyTypeAccountCo;
        }

        return filter;
    }

    public Map<String, Object> SetPropertiesRules(Map<String, Object> rule) {
        String commissionMaintenance = RuleName.COMMISSIONMAINTENANCE.getName();
        String maximumLimitMonthlyMovements = RuleName.MAXLIMITMOVMONTHLY.getName();

        Boolean commissionMaintenanceValue = Utils.ObjectToBoolean(rule.get(commissionMaintenance));
        Boolean maximumLimitMonthlyMovementsValue = Utils.ObjectToBoolean(
            rule.get(maximumLimitMonthlyMovements)
        );

        String rescm = Utils.BooleanToString(commissionMaintenanceValue);
        String resmxm = Utils.BooleanToString(maximumLimitMonthlyMovementsValue);

        rule.put(commissionMaintenance, rescm);
        rule.put(maximumLimitMonthlyMovements, resmxm);

        return rule;
    }
}
