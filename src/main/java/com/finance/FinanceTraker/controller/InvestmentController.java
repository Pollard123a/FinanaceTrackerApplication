package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.Expense;
import com.finance.FinanceTraker.database.tables.Investment;
import com.finance.FinanceTraker.request.ExpenseRequest;
import com.finance.FinanceTraker.request.InvestmentRequest;
import com.finance.FinanceTraker.response.ExpenseResponse;
import com.finance.FinanceTraker.response.InvestmentResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvestmentController {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/investment/{user_id}")
    public List<Investment> getInvestment(@PathVariable Long user_id) {

        logger.info("START Get Investment for user with  id " + user_id);
        List<Investment> investments;
        investments = service.getInvestment(user_id);
        logger.info("END GET Investment for USER ID: "+ user_id);
        return investments;
    }

    @PostMapping("/addInvestment")
    public InvestmentResponse createInvestment(@RequestBody InvestmentRequest request) {
        logger.info("START Add Investment request{} "+ request);
        InvestmentResponse response;
        response = service.addInvestment(request);
        logger.info("END Add Investment response{} "+ response);
        return response;
    }

    @PutMapping("/updateInvestment")
    public Investment updateInvestment(@RequestBody InvestmentRequest request) {
        logger.info("START Update Investment request{} "+ request);
        Investment investment;
        investment = service.updateInvestment(request);
        logger.info("END Update Investment response{} "+ investment);
        return investment;
    }

    @DeleteMapping("/deleteInvestment/{id}")
    public InvestmentResponse deleteInvestment(@PathVariable Long id) {
        logger.info("START Delete Investment with  id "+ id);
        InvestmentResponse response;
        response = service.deleteInvestment(id);
        logger.info("END Delete Investment response{}  "+ response);
        return response;
    }
}
