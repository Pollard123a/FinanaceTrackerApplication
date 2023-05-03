package com.finance.FinanceTraker.validation;

import com.finance.FinanceTraker.request.ExpenseRequest;

import java.util.Date;

public class ExpenseValidation {
    public static void validateExpenseRequest(ExpenseRequest request) throws Exception {
        validateDate(request.getDate());
    }
    private static void validateDate(Date date) throws Exception {
        if(date.after(new Date())) {
            throw new Exception("Date should be less than or equal to today's date");
        }
    }
}
