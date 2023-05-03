package com.finance.FinanceTraker.validation;

import com.finance.FinanceTraker.request.ExpenseRequest;
import com.finance.FinanceTraker.request.LendMoneyRequest;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LendMoneyValidation {
    public static void validateLendMoneyRequest(LendMoneyRequest request) throws Exception {
        validateDate(request.getLendDate());
        validateBorrowDate(request.getLendDate(),request.getBorrowDate());
        validateMobileNo(request.getMobileNo());
        validateInterestRate(request.getInterestRate());


    }
    private static void validateDate(Date date) throws Exception {
        if(date.after(new Date())) {
            throw new Exception("Date should be less than or equal to today's date");
        }
    }
    private static void validateBorrowDate(Date lendDate, Date borrowDate) throws Exception {
        if(lendDate.before(borrowDate)) {
            throw new Exception("Borrow date should be after the lend date");
        }
    }

    private static void validateMobileNo(Long mobileNo) throws Exception {
        Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher matcher = pattern.matcher(mobileNo.toString());
        boolean match = matcher.matches();
        if(!match) {
            throw new Exception("Mobile no is not in valid format!!");
        }
    }

    private static void validateInterestRate(Double interestRate) throws Exception {

        if (interestRate < 0) {
            throw new Exception("Interest rate can not be negative!!");
        }
    }
}
