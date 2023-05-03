package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.Income;
import com.finance.FinanceTraker.database.tables.User;
import com.finance.FinanceTraker.request.IncomeRequest;
import com.finance.FinanceTraker.request.UserRequest;
import com.finance.FinanceTraker.response.ExpenseResponse;
import com.finance.FinanceTraker.response.IncomeResponse;
import com.finance.FinanceTraker.response.UserResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncomeController {

    private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/income/{user_id}")
    public List<Income> getIncome(@PathVariable Long user_id) {

        logger.info("START Get Income for user with  id " + user_id);
        List<Income> incomes;
        incomes = service.getIncome(user_id);
        logger.info("END GET Income for USER ID: "+ user_id);
        return incomes;
    }

    @PostMapping("/addIncome")
    public IncomeResponse createIncome(@RequestBody IncomeRequest request) {
        logger.info("START Add Income request{} "+ request);
        IncomeResponse response;
        response = service.addIncome(request);
        logger.info("END Add Income response{} "+ response);
        return response;
    }

    @PutMapping("/updateIncome")
    public Income updateIncome(@RequestBody IncomeRequest request) {
        logger.info("START Update Income request{} "+ request);
        Income income;
        income = service.modifyIncome(request);
        logger.info("END Update Income response{} "+ income);
        return income;
    }

    @DeleteMapping("/deleteIncome/{id}")
    public IncomeResponse deleteIncome(@PathVariable Long id) {
        logger.info("START Delete Income with  id "+ id);
        IncomeResponse response;
        response = service.deleteIncome(id);
        logger.info("END Delete Income response{}  "+ response);
        return response;
    }

}
