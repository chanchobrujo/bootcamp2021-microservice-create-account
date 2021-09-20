package com.everisbootcamp.createaccount.Service;

import com.everisbootcamp.createaccount.Data.Rules;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    public Rules addRule(String customerType, String accountType, String profile, int maximumLimitMonthlyMovementsQuantity) {
        boolean commissionMaintenance = false, maximumLimitMonthlyMovements = false;

        if (accountType.equals("Cuenta de ahorro")) maximumLimitMonthlyMovements = true;
        if (accountType.equals("Cuenta corriente")) commissionMaintenance = true; 
        if (accountType.equals("Cuenta plazo fijo")) {
            maximumLimitMonthlyMovements = true;
            maximumLimitMonthlyMovementsQuantity = 1;
        }
        

        return new Rules(customerType, commissionMaintenance, maximumLimitMonthlyMovements, maximumLimitMonthlyMovementsQuantity);
    }
}
