package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.FinancialGoal;
import com.finance.FinanceTraker.database.tables.Investment;
import com.finance.FinanceTraker.request.FinancialGoalRequest;
import com.finance.FinanceTraker.request.InvestmentRequest;
import com.finance.FinanceTraker.response.ExpenseResponse;
import com.finance.FinanceTraker.response.FinancialGoalResponse;
import com.finance.FinanceTraker.response.InvestmentResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class FinancialGoalController {
    private static final Logger logger = LoggerFactory.getLogger(FinancialGoalController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/financialGoal/{user_id}")
    public List<FinancialGoal> getFinancialGoal(@PathVariable Long user_id) {

        logger.info("START Get Financial Goal for user with  id " + user_id);
        List<FinancialGoal> financialGoals;
        financialGoals = service.getFinancialGoal(user_id);
        logger.info("END GET Financial goal for USER ID: "+ user_id);
        return financialGoals;
    }

    @PostMapping("/addFinancialGoal")
    public FinancialGoalResponse createFinancialGoal(@RequestBody FinancialGoalRequest request) {
        logger.info("START Add Financial Goal request{} "+ request);
        FinancialGoalResponse response;
        response = service.addFinancialGoal(request);
        logger.info("END Add Financial Goal response{} "+ response);
        return response;
    }

    @PutMapping("/updateFinancialGoal")
    public FinancialGoal updateFinancialGoal(@RequestBody FinancialGoalRequest request) {
        logger.info("START Update Financial Goal request{} "+ request);
        FinancialGoal financialGoal;
        financialGoal = service.updateFinancialGoal(request);
        logger.info("END Update Financial Goal response{} "+ financialGoal);
        return financialGoal;
    }

    @DeleteMapping("/deleteFinancialGoal/{id}")
    public FinancialGoalResponse deleteFinancialGoal(@PathVariable Long id) {
        logger.info("START Delete Financial Goal with  id "+ id);
        FinancialGoalResponse response;
        response = service.deleteFinancialGoal(id);
        logger.info("END Delete Financial Goal response{}  "+ response);
        return response;
    }
}
