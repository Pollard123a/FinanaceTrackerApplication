package com.finance.FinanceTraker.controller;

import com.finance.FinanceTraker.database.tables.User;
import com.finance.FinanceTraker.request.UserRequest;
import com.finance.FinanceTraker.response.UserResponse;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    FinanceTrackerService service;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        logger.info("START Get User with id " + id);
        User user;
        user = service.getUser(id);
        logger.info("END GET User USER: "+ user);
        return user;
    }

    @PostMapping("/registration")
    public UserResponse createUser(@RequestBody UserRequest request) {
        logger.info("START Create User request{} "+ request);
        UserResponse response = null;
        response = service.registration(request);
        logger.info("END Create User response{} "+ response);
        return response;
    }


}
