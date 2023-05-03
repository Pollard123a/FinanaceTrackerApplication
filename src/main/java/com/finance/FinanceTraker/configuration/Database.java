package com.finance.FinanceTraker.configuration;

import com.finance.FinanceTraker.database.tables.*;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;


@Configuration
public class Database {

    Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean(value = "SessionFactory")
    public SessionFactory connectToDatabase() {
        logger.info("Connecting to database....");
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration().configure().addAnnotatedClass(User.class).addAnnotatedClass(Income.class)
                .addAnnotatedClass(Expense.class).addAnnotatedClass(Investment.class).addAnnotatedClass(FinancialGoal.class).addAnnotatedClass(Reminder.class)
                .addAnnotatedClass(LendMoney.class);
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        SessionFactory sessionFactory = config.buildSessionFactory(registry);
        logger.info("Database connection established!!");
        return sessionFactory;
    }
}
