package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.Expense;
import com.finance.FinanceTraker.request.ExpenseRequest;
import com.finance.FinanceTraker.response.ExpenseResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/expense/{user_id}")
    public List<Expense> getExpense(@PathVariable Long user_id) {

        logger.info("START Get Expense for user with  id " + user_id);
        List<Expense> expenses;
        expenses = service.getExpense(user_id);
        logger.info("END GET Expense for USER ID: "+ user_id);
        return expenses;
    }

    @PostMapping("/addExpense")
    public ExpenseResponse createExpense(@RequestBody ExpenseRequest request) {
        logger.info("START Add Expense request{} "+ request);
        ExpenseResponse response;
        response = service.addExpense(request);
        logger.info("END Add Expense response{} "+ response);
        return response;
    }

    @PutMapping("/updateExpense")
    public Expense updateExpense(@RequestBody ExpenseRequest request) {
        logger.info("START Update Expense request{} "+ request);
        Expense expense;
        expense = service.updateExpense(request);
        logger.info("END update Expense response{}  "+ expense);
        return expense;
    }

    @DeleteMapping("/deleteExpense/{id}")
    public ExpenseResponse deleteExpense(@PathVariable Long id) {
        logger.info("START Delete Expense with  id "+ id);
        ExpenseResponse response;
        response = service.deleteExpense(id);
        logger.info("END Delete Expense response{}  "+ response);
        return response;
    }
}
