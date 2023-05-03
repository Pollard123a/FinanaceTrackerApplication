package com.finance.FinanceTraker.validation;

import com.finance.FinanceTraker.request.ExpenseRequest;
import com.finance.FinanceTraker.request.InvestmentRequest;

import java.util.Date;

public class InvestmentValidation {
    public static void validateInvestmentRequest(InvestmentRequest request) throws Exception {
        validateDate(request.getBuyDate());
        validateDate(request.getSellDate());
        validateBuySellDate(request.getBuyDate(),request.getSellDate());
    }
    private static void validateDate(Date date) throws Exception {
        if(date.after(new Date())) {
            throw new Exception("Date should be less than or equal to today's date");
        }
    }

    private static void validateBuySellDate(Date buyDate, Date sellDate) throws Exception {
        if(buyDate.after(sellDate)) {
            throw new Exception("Buy date should be before sell date!!");
        }
    }
}
