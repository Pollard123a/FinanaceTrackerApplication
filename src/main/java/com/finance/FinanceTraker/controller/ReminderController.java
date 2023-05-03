package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.Reminder;
import com.finance.FinanceTraker.request.ReminderRequest;
import com.finance.FinanceTraker.response.ExpenseResponse;
import com.finance.FinanceTraker.response.ReminderResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReminderController {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/reminder/{user_id}")
    public List<Reminder> getReminder(@PathVariable Long user_id) {

        logger.info("START Get Reminder for user with  id " + user_id);
        List<Reminder> reminders;
        reminders = service.getReminder(user_id);
        logger.info("END GET Reminder for USER ID: "+ user_id);
        return reminders;
    }

    @PostMapping("/addReminder")
    public ReminderResponse createReminder(@RequestBody ReminderRequest request) {
        logger.info("START Add Reminder request{} "+ request);
        ReminderResponse response;
        response = service.addReminder(request);
        logger.info("END Add Reminder response{} "+ response);
        return response;
    }

    @PutMapping("/updateReminder")
    public Reminder updateReminder(@RequestBody ReminderRequest request) {
        logger.info("START Update Reminder request{} "+ request);
        Reminder reminder;
        reminder = service.updateReminder(request);
        logger.info("END Update Reminder response{} "+ reminder);
        return reminder;
    }

    @DeleteMapping("/deleteReminder/{id}")
    public ReminderResponse deleteReminder(@PathVariable Long id) {
        logger.info("START Delete Reminder with  id "+ id);
        ReminderResponse response;
        response = service.deleteReminder(id);
        logger.info("END Delete Reminder response{}  "+ response);
        return response;
    }
}
