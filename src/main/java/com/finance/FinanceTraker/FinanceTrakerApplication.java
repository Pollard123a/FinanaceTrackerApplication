package com.finance.FinanceTraker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceTrakerApplication {

	private final static Logger logger = LoggerFactory.getLogger(FinanceTrakerApplication.class);
	public static void main(String[] args) {
		logger.info("Finance Tracker application starting....");
		SpringApplication.run(FinanceTrakerApplication.class, args);
		logger.info("********Finance Tracker application started********");
	}
}
