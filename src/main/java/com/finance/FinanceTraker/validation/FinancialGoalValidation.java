package com.finance.FinanceTraker.validation;

import com.finance.FinanceTraker.request.FinancialGoalRequest;

import java.util.Date;

public class FinancialGoalValidation {

    public static void validateFinancialGoalRequest(FinancialGoalRequest request) throws Exception {
        validateDate(request.getAcheiveDate());
    }

    private static void validateDate(Date date) throws Exception {
        if(date.after(new Date())) {
            throw new Exception("Date should be less than or equal to today's date");
        }
    }
}
