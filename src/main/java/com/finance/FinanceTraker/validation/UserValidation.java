package com.finance.FinanceTraker.validation;

import com.finance.FinanceTraker.controller.UserController;
import com.finance.FinanceTraker.database.tables.User;
import com.finance.FinanceTraker.exception.UserNotFoundException;
import com.finance.FinanceTraker.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    @Autowired
    static UserController controller;

    static List<String> countries = new ArrayList<>();

    static {
        String[] isoCountryCodes = Locale.getISOCountries();

        for (String countryCode : isoCountryCodes) {
            Locale locale = new Locale("", countryCode);
            countries.add(locale.getDisplayCountry());
        }
    }

    public static void validateUser(Long userId) throws UserNotFoundException {
        User user = controller.getUser(userId);
        if(user == null) {
            throw new UserNotFoundException("User not found!!");
        }
    }

    public static void validateUserRequest(UserRequest request) throws Exception {
        validatePassword(request.getPassword());
        validateEmail(request.getEmail());
        validateCountry(request.getCountry());
    }

    /**
    * Must have at least one numeric character
    * Must have at least one lowercase character
    * Must have at least one uppercase character
    * Must have at least one special symbol among @#$%
    * Password length should be between 8 and 20
     */
    private static void validatePassword(String password) throws Exception {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        boolean match = matcher.matches();
        if(!match) {
            throw new Exception("Password should contain at least one numeric,lowercase, uppercase, special character and length " +
                    "between 8 to 20");
        }
    }

    private static void validateEmail(String email) throws Exception {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean match = matcher.matches();
        if(!match) {
            throw new Exception("Email is not in proper format!!");
        }
    }

    private static void validateCountry(String country) throws Exception {
        if(!countries.contains(country)) {
            throw new Exception("Country not found!!");
        }
    }
}
