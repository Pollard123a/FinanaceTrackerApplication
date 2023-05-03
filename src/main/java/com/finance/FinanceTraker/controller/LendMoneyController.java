package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.LendMoney;
import com.finance.FinanceTraker.request.LendMoneyRequest;
import com.finance.FinanceTraker.response.LendMoneyResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LendMoneyController {

    private static final Logger logger = LoggerFactory.getLogger(LendMoneyController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/lendMoney/{user_id}")
    public List<LendMoney> getLendMoney(@PathVariable Long user_id) {

        logger.info("START Get Lend Money for user with  id " + user_id);
        List<LendMoney> lendMonies;
        lendMonies = service.getLendMoney(user_id);
        logger.info("END GET Lend Money for USER ID: "+ user_id);
        return lendMonies;
    }

    @PostMapping("/addLendMoney")
    public LendMoneyResponse createLendMoney(@RequestBody LendMoneyRequest request) {
        logger.info("START Add Lend Money request{} "+ request);
        LendMoneyResponse response;
        response = service.addLendMoney(request);
        logger.info("END Add Lend Money response{} "+ response);
        return response;
    }

    @PutMapping("/updateLendMoney")
    public LendMoney updateLendMoney(@RequestBody LendMoneyRequest request) {
        logger.info("START Update Lend Money request{} "+ request);
        LendMoney lendMoney;
        lendMoney = service.updateLendMoney(request);
        logger.info("END update Lend Money response{}  "+ lendMoney);
        return lendMoney;
    }

    @DeleteMapping("/deleteLendMoney/{id}")
    public LendMoneyResponse deleteLendMoney(@PathVariable Long id) {
        logger.info("START Delete Lend Money with  id "+ id);
        LendMoneyResponse response;
        response = service.deleteLendMoney(id);
        logger.info("END Delete Lend Money response{}  "+ response);
        return response;
    }
}
