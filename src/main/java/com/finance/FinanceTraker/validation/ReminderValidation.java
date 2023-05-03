package com.finance.FinanceTraker.validation;

import com.finance.FinanceTraker.request.ReminderRequest;

import java.util.Date;

public class ReminderValidation {

    public static void validateReminderRequest(ReminderRequest request) throws Exception {
        validateDate(request.getDate());
    }

    private static void validateDate(Date date) throws Exception {
        if(date.after(new Date())) {
            throw new Exception("Date should be less than or equal to today's date");
        }
    }
}
