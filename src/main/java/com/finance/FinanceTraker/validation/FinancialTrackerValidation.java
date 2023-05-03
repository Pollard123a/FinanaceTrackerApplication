package com.finance.FinanceTraker.validation;

import java.util.Date;

public class FinancialTrackerValidation {

    public static void validateDate(Date date) throws Exception {
        if(date.after(new Date())) {
            throw new Exception("Date should be less than or equal to today's date");
        }
    }

    public static void validateYear(int year) throws Exception {
        int currentYear = (new Date()).getYear();
        if(year > currentYear) {
            throw new Exception("Year should be before or equal to current year");
        }
    }
}
