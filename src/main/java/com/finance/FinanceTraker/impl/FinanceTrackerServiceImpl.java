package com.finance.FinanceTraker.impl;

import com.finance.FinanceTraker.database.tables.*;

import com.finance.FinanceTraker.exception.UserNotFoundException;
import com.finance.FinanceTraker.model.FinanceTracker;
import com.finance.FinanceTraker.request.*;
import com.finance.FinanceTraker.response.*;
import com.finance.FinanceTraker.service.FinanceTrackerService;
import com.finance.FinanceTraker.validation.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FinanceTrackerServiceImpl implements FinanceTrackerService {

    Logger logger = LoggerFactory.getLogger(FinanceTrackerServiceImpl.class);
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    BCryptPasswordEncoder encoder;


    @Override
    public UserResponse registration(UserRequest request) {
        logger.info("Registration of user with id = " + request.getId());
        UserResponse response = new UserResponse();
        String status = "";
        String msg = "";
        try {
            UserValidation.validateUserRequest(request);
            User user = new User();
            user.setId(request.getId());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setCountry(request.getCountry());

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(user);
            transaction.commit();
            session.close();
            logger.info("Successfully registered the user with id = " + request.getId());

            status = "OK";
            msg = "Successfully registered the user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
        catch (Exception e) {
            logger.error("Exception occurred during registration of user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred during registration " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public User getUser(Long id) {
        logger.info("Getting user with id " + id);
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            user = (User)session.get(User.class,id);
            transaction.commit();
            session.close();
            logger.info("Successfully got the user " + user);
            return user;
        }
        catch (Exception e) {
            logger.error("Exception occurred while getting the user with id = " +id +" "+ e.getMessage());
            return user;
        }
    }

    @Override
    public IncomeResponse addIncome(IncomeRequest request) {
        logger.info("Adding the income for user = " + request.getUserId());
        IncomeResponse response = new IncomeResponse();
        String status = "";
        String msg = "";
        try {
            IncomeValidation.validateIncomeRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Income income = new Income();
            income.setId(request.getId());
            income.setAmount(request.getAmount());
            income.setDate(request.getDate());
            income.setCategory(request.getCategory());
            income.setUser(user);
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(income);
            transaction.commit();
            session.close();
            logger.info("Successfully added the income for user with id = " + request.getUserId());

            status = "OK";
            msg = "Successfully added the income for user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            status = "ERROR";
            msg = "No usr is present for user id " + request.getUserId();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while adding income for user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while adding income for user " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public List<Income> getIncome(Long id) {
        logger.info("Getting income for user = " + id);

        try {
            UserValidation.validateUser(id);
            List<Income> incomes;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery query = session.createSQLQuery("Select * from Income i where i.user_id = "+ id);
            incomes = query.list();
            transaction.commit();
            session.close();
            logger.info("Successfully got the incomes for user with id = " + id);
            return incomes;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + id);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the income for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public Income modifyIncome(IncomeRequest request) {
        logger.info("Updating the income for user = " + request.getUserId());

        try {
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Income income = new Income();
            income.setId(request.getId());
            income.setAmount(request.getAmount());
            income.setDate(request.getDate());
            income.setCategory(request.getCategory());
            income.setUser(user);

                Session session = sessionFactory.openSession();
                Transaction transaction = session.getTransaction();
                transaction.begin();
                session.update(income);
                transaction.commit();
                session.close();
                logger.info("Successfully updated the income for user with id = " + request.getUserId());

                return income;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while updating income for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public IncomeResponse deleteIncome(Long id) {
        logger.info("Deleting income with id = " + id);
        IncomeResponse response = new IncomeResponse();
        String status = "";
        String msg = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Income income = (Income)session.get(Income.class,id);
            if(income != null) {
                session.delete(income);
            }
            else {
                throw new Exception("No income is present with id " + id);
            }
            transaction.commit();
            session.close();
            logger.info("Successfully deleted the incomes with id = " + id);
            status = "OK";
            msg = "Successfully deleted income with id = " + id;
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        }  catch (Exception e) {
            logger.error("Exception occurred while deleting the income " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while deleting the income " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public ExpenseResponse addExpense(ExpenseRequest request) {
        logger.info("Adding the expense for user = " + request.getUserId());
        ExpenseResponse response = new ExpenseResponse();
        String status = "";
        String msg = "";
        try {
            ExpenseValidation.validateExpenseRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Expense expense = new Expense();
            expense.setId(request.getId());
            expense.setAmount(request.getAmount());
            expense.setDate(request.getDate());
            expense.setCategory(request.getCategory());
            expense.setUser(user);
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(expense);
            transaction.commit();
            session.close();
            logger.info("Successfully added the expense for user with id = " + request.getUserId());
            status = "OK";
            msg = "Successfully added the income for user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            status = "ERROR";
            msg = "No user is present for user id " + request.getUserId();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while adding expense for user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while adding expense for user " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public List<Expense> getExpense(Long id) {
        logger.info("Getting expense for user = " + id);

        try {
            UserValidation.validateUser(id);
            List<Expense> expenses;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery query = session.createSQLQuery("Select * from Expense i where i.user_id = "+ id);
            expenses = query.list();
            transaction.commit();
            session.close();
            logger.info("Successfully got the expenses for user with id = " + id);
            return expenses;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + id);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the expense for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public Expense updateExpense(ExpenseRequest request) {
        logger.info("Updating the expense for user = " + request.getUserId());

        try {
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Expense expense = new Expense();
            expense.setId(request.getId());
            expense.setAmount(request.getAmount());
            expense.setDate(request.getDate());
            expense.setCategory(request.getCategory());
            expense.setUser(user);
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(expense);
            transaction.commit();
            session.close();
            logger.info("Successfully updated the expense for user with id = " + request.getUserId());
            return expense;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while updating income for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public ExpenseResponse deleteExpense(Long id) {
        logger.info("Deleting Expense with id = " + id);
        ExpenseResponse response = new ExpenseResponse();
        String status = "";
        String msg = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Expense expense = (Expense)session.get(Expense.class,id);
            if(expense != null) {
                session.delete(expense);
            }
            else {
                throw new Exception("No expense is present with id " + id);
            }
            transaction.commit();
            session.close();
            logger.info("Successfully deleted the expense with id = " + id);
            status = "OK";
            msg = "Successfully deleted expense with id = " + id;
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        }  catch (Exception e) {
            logger.error("Exception occurred while deleting the expense " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while deleting the expense " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public InvestmentResponse addInvestment(InvestmentRequest request) {
        logger.info("Adding the investment for user = " + request.getUserId());

        InvestmentResponse response = new InvestmentResponse();
        String status = "";
        String msg = "";
        try {
            InvestmentValidation.validateInvestmentRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Investment investment = new Investment();
            investment.setId(request.getId());
            investment.setName(request.getName());
            investment.setType(request.getType());
            investment.setPrice(request.getPrice());
            investment.setQuantity(request.getQuantity());
            investment.setBuyDate(request.getBuyDate());
            investment.setSellDate(request.getSellDate());
            investment.setUser(user);
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(investment);
            transaction.commit();
            session.close();
            logger.info("Successfully added the investment for user with id = " + request.getUserId());

            status = "OK";
            msg = "Successfully added the investment for user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            status = "ERROR";
            msg = "No user is present for user id " + request.getUserId();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while adding investment for user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while adding investment for user " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public List<Investment> getInvestment(Long id) {
        logger.info("Getting investment for user = " + id);

        try {
            UserValidation.validateUser(id);
            List<Investment> investments;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery query = session.createSQLQuery("Select * from Investment i where i.user_id = "+ id);
            investments = query.list();
            transaction.commit();
            session.close();
            logger.info("Successfully got the investment for user with id = " + id);
            return investments;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + id);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the investment for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public Investment updateInvestment(InvestmentRequest request) {
        logger.info("Updating the investment for user = " + request.getUserId());

        try {
            InvestmentValidation.validateInvestmentRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Investment investment = new Investment();
            investment.setId(request.getId());
            investment.setName(request.getName());
            investment.setType(request.getType());
            investment.setPrice(request.getPrice());
            investment.setQuantity(request.getQuantity());
            investment.setBuyDate(request.getBuyDate());
            investment.setSellDate(request.getSellDate());
            investment.setUser(user);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(investment);
            transaction.commit();
            session.close();
            logger.info("Successfully updated the investment for user with id = " + request.getUserId());
            return investment;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while updating investment for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public InvestmentResponse deleteInvestment(Long id) {
        logger.info("Deleting investment with id = " + id);
        InvestmentResponse response = new InvestmentResponse();
        String status = "";
        String msg = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Investment investment = (Investment) session.get(Investment.class,id);
            if(investment != null) {
                session.delete(investment);
            }
            else {
                throw new Exception("No investment is present with id " + id);
            }
            transaction.commit();
            session.close();
            logger.info("Successfully deleted the investment with id = " + id);
            status = "OK";
            msg = "Successfully deleted investment with id = " + id;
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        }  catch (Exception e) {
            logger.error("Exception occurred while deleting the investment " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while deleting the investment " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public FinancialGoalResponse addFinancialGoal(FinancialGoalRequest request) {
        logger.info("Adding the financial goal for user = " + request.getUserId());
        FinancialGoalResponse response = new FinancialGoalResponse();
        String status = "";
        String msg = "";
        try {
            FinancialGoalValidation.validateFinancialGoalRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            FinancialGoal financialGoal = new FinancialGoal();
            financialGoal.setId(request.getId());
            financialGoal.setDescription(request.getDescription());
            financialGoal.setAmount(request.getAmount());
            financialGoal.setCompleted(false);
            financialGoal.setCurrentDate(new Date());
            financialGoal.setAcheiveDate(request.getAcheiveDate());
            financialGoal.setUser(user);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(financialGoal);
            transaction.commit();
            session.close();
            logger.info("Successfully added the financial goal for user with id = " + request.getUserId());

            status = "OK";
            msg = "Successfully added the financial goal for user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            status = "ERROR";
            msg = "No user is present for user id " + request.getUserId();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while adding financial goal for user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while adding financial goal for user " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public List<FinancialGoal> getFinancialGoal(Long id) {
        logger.info("Getting financial goal for user = " + id);

        try {
            UserValidation.validateUser(id);
            List<FinancialGoal> financialGoals;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery query = session.createSQLQuery("Select * from FinancialGoal f where f.user_id = "+ id);
            financialGoals = query.list();
            transaction.commit();
            session.close();
            logger.info("Successfully got the financial goal for user with id = " + id);
            return financialGoals;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + id);
            return null;
        } catch (Exception e) {
           logger.error("Exception occurred while getting the financial goal for user " + e.getMessage());
           return null;
        }
    }

    @Override
    public FinancialGoal updateFinancialGoal(FinancialGoalRequest request) {
        logger.info("Updating the financial goal for user = " + request.getUserId());

        try {
            FinancialGoalValidation.validateFinancialGoalRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getId());
            FinancialGoal financialGoal = new FinancialGoal();
            financialGoal.setId(request.getId());
            financialGoal.setDescription(request.getDescription());
            financialGoal.setAmount(request.getAmount());
            financialGoal.setCompleted(request.isCompleted());
            financialGoal.setCurrentDate(new Date());
            financialGoal.setAcheiveDate(request.getAcheiveDate());
            financialGoal.setUser(user);
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(financialGoal);
            transaction.commit();
            session.close();
            logger.info("Successfully updated the financial goal for user with id = " + request.getUserId());
            return financialGoal;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while updating financial goal for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public FinancialGoalResponse deleteFinancialGoal(Long id) {
        logger.info("Deleting financial goal with id = " + id);
        FinancialGoalResponse response = new FinancialGoalResponse();
        String status = "";
        String msg = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            FinancialGoal financialGoal = (FinancialGoal) session.get(FinancialGoal.class,id);
            if(financialGoal != null) {
                session.delete(financialGoal);
            }
            else {
                throw new Exception("No financial goal is present with id " + id);
            }
            transaction.commit();
            session.close();
            logger.info("Successfully deleted the financial goal with id = " + id);
            status = "OK";
            msg = "Successfully deleted financial goal with id = " + id;
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        }  catch (Exception e) {
            logger.error("Exception occurred while deleting the financial goal " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while deleting the financial goal " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public ReminderResponse addReminder(ReminderRequest request) {
        logger.info("Adding the Reminder for user = " + request.getUserId());
        ReminderResponse response = new ReminderResponse();
        String status = "";
        String msg = "";
        try {
            ReminderValidation.validateReminderRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            Reminder reminder = new Reminder();
            reminder.setId(request.getId());
            reminder.setDescription(request.getDescription());
            reminder.setType(request.getType());
            reminder.setDate(request.getDate());
            reminder.setActive(request.isActive());
            reminder.setUser(user);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(reminder);
            transaction.commit();
            session.close();
            logger.info("Successfully added the reminder goal for user with id = " + request.getUserId());

            status = "OK";
            msg = "Successfully added the reminder for user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            status = "ERROR";
            msg = "No user is present for user id " + request.getUserId();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while adding reminder for user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while adding reminder for user " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public List<Reminder> getReminder(Long id) {
        logger.info("Getting Reminder for user = " + id);

        try {
            UserValidation.validateUser(id);
            List<Reminder> reminders;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery query = session.createSQLQuery("Select * from Reminder r where r.user_id = "+ id);
            reminders = query.list();
            transaction.commit();
            session.close();
            logger.info("Successfully got the reminder for user with id = " + id);
            return reminders;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + id);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the reminder for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public Reminder updateReminder(ReminderRequest request) {
        logger.info("Updating the Reminder for user = " + request.getUserId());

        try {
            ReminderValidation.validateReminderRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getId());
            Reminder reminder = new Reminder();
            reminder.setId(request.getId());
            reminder.setDescription(request.getDescription());
            reminder.setType(request.getType());
            reminder.setDate(request.getDate());
            reminder.setActive(request.isActive());
            reminder.setUser(user);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(reminder);
            transaction.commit();
            session.close();
            logger.info("Successfully updated the Reminder for user with id = " + request.getUserId());
            return reminder;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while updating Reminder for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public ReminderResponse deleteReminder(Long id) {
        logger.info("Deleting reminder with id = " + id);
        ReminderResponse response = new ReminderResponse();
        String status = "";
        String msg = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Reminder reminder = (Reminder) session.get(Reminder.class,id);
            if(reminder != null) {
                session.delete(reminder);
            }
            else {
                throw new Exception("No reminder is present with id " + id);
            }
            transaction.commit();
            session.close();
            logger.info("Successfully deleted the reminder with id = " + id);
            status = "OK";
            msg = "Successfully deleted reminder with id = " + id;
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        }  catch (Exception e) {
            logger.error("Exception occurred while deleting the reminder " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while deleting the reminder " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public LendMoneyResponse addLendMoney(LendMoneyRequest request) {
        logger.info("Adding the Lend Money for user = " + request.getUserId());
        LendMoneyResponse response = new LendMoneyResponse();
        String status = "";
        String msg = "";
        try {
            LendMoneyValidation.validateLendMoneyRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getUserId());
            LendMoney lendMoney = new LendMoney();
            lendMoney.setId(request.getId());
            lendMoney.setName(request.getName());
            lendMoney.setPlace(request.getPlace());
            lendMoney.setMobileNo(request.getMobileNo());
            lendMoney.setAmount(request.getAmount());
            lendMoney.setInterestRate(request.getInterestRate());
            lendMoney.setLendDate(request.getLendDate());
            lendMoney.setBorrowDate(request.getBorrowDate());
            lendMoney.setUser(user);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(lendMoney);
            transaction.commit();
            session.close();
            logger.info("Successfully added the Lend Money for user with id = " + request.getUserId());

            status = "OK";
            msg = "Successfully added the Lend Money for user";
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            status = "ERROR";
            msg = "No user is present for user id " + request.getUserId();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while adding Lend Money for user " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while adding Lend Money for user " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }    }

    @Override
    public List<LendMoney> getLendMoney(Long id) {
        logger.info("Getting Lend Money for user = " + id);

        try {
            UserValidation.validateUser(id);
            List<LendMoney> lendMonies;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery query = session.createSQLQuery("Select * from LendMoney l where l.user_id = "+ id);
            lendMonies = query.list();
            transaction.commit();
            session.close();
            logger.info("Successfully got the lend monies for user with id = " + id);
            return lendMonies;

        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + id);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the Lend Money for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public LendMoney updateLendMoney(LendMoneyRequest request) {
        logger.info("Updating the Lend Money for user = " + request.getUserId());

        try {
            LendMoneyValidation.validateLendMoneyRequest(request);
            UserValidation.validateUser(request.getUserId());
            User user = getUser(request.getId());
            LendMoney lendMoney = new LendMoney();
            lendMoney.setId(request.getId());
            lendMoney.setName(request.getName());
            lendMoney.setPlace(request.getPlace());
            lendMoney.setMobileNo(request.getMobileNo());
            lendMoney.setAmount(request.getAmount());
            lendMoney.setInterestRate(request.getInterestRate());
            lendMoney.setLendDate(request.getLendDate());
            lendMoney.setBorrowDate(request.getBorrowDate());
            lendMoney.setUser(user);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(lendMoney);
            transaction.commit();
            session.close();
            logger.info("Successfully updated the Lend Money for user with id = " + request.getUserId());
            return lendMoney;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + request.getUserId());
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while updating Lend Money for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public LendMoneyResponse deleteLendMoney(Long id) {
        logger.info("Deleting Lend Money with id = " + id);
        LendMoneyResponse response = new LendMoneyResponse();
        String status = "";
        String msg = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            LendMoney lendMoney = (LendMoney) session.get(LendMoney.class,id);
            if(lendMoney != null) {
                session.delete(lendMoney);
            }
            else {
                throw new Exception("No Lend Money is present with id " + id);
            }
            transaction.commit();
            session.close();
            logger.info("Successfully deleted the Lend Money with id = " + id);
            status = "OK";
            msg = "Successfully deleted Lend Money with id = " + id;
            response.setStatus(status);
            response.setMessage(msg);
            return response;

        }  catch (Exception e) {
            logger.error("Exception occurred while deleting the Lend Money " + e.getMessage());
            status = "ERROR";
            msg = "Exception occurred while deleting the Lend Money " + e.getMessage();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }
    }

    @Override
    public FinanceTracker getDetailsByDate(Long userId, Date date) {

        logger.info("Getting financial details for user "+ userId + " on date " + date);

        try {
            FinancialTrackerValidation.validateDate(date);
            UserValidation.validateUser(userId);
            Double totalIncome;
            Double totalExpense;
            Double totalInvestment;
            FinanceTracker financeTracker = new FinanceTracker();

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery  totalIncomeQuery = session.createSQLQuery("Select sum(i.amount) from Income i where i.user_id = "+ userId + " and i.date = "+date);
            totalIncome = (Double)totalIncomeQuery.uniqueResult();
            SQLQuery  totalExpenseQuery = session.createSQLQuery("Select sum(e.amount) from Expense e where e.user_id = "+ userId + " and e.date = "+date);
            totalExpense = (Double)totalExpenseQuery.uniqueResult();
            SQLQuery totalInvestmentQuery = session.createSQLQuery("Select sum(i.amount) from Investment i where i.user_id = "+ userId + " and i.date = "+date);
            totalInvestment = (Double)totalInvestmentQuery.uniqueResult();
            transaction.commit();
            session.close();

            financeTracker.setIncome(totalIncome);
            financeTracker.setExpense(totalExpense);
            financeTracker.setInvestment(totalInvestment);
            logger.info("Successfully got the financial details for user with id = " + userId);
            return financeTracker;
        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + userId);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the financial details for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public FinanceTracker getDetailsLessThanByDate(Long userId, Date date) {
        logger.info("Getting financial details for user "+ userId + " on date " + date);

        try {
            FinancialTrackerValidation.validateDate(date);
            UserValidation.validateUser(userId);
            Double totalIncome;
            Double totalExpense;
            Double totalInvestment;
            FinanceTracker financeTracker = new FinanceTracker();

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery  totalIncomeQuery = session.createSQLQuery("Select sum(i.amount) from Income i where i.user_id = "+ userId + " and i.date <= "+date);
            totalIncome = (Double)totalIncomeQuery.uniqueResult();
            SQLQuery  totalExpenseQuery = session.createSQLQuery("Select sum(e.amount) from Expense e where e.user_id = "+ userId + " and e.date <= "+date);
            totalExpense = (Double)totalExpenseQuery.uniqueResult();
            SQLQuery totalInvestmentQuery = session.createSQLQuery("Select sum(i.amount) from Investment i where i.user_id = "+ userId + " and i.date <= "+date);
            totalInvestment = (Double)totalInvestmentQuery.uniqueResult();
            transaction.commit();
            session.close();

            financeTracker.setIncome(totalIncome);
            financeTracker.setExpense(totalExpense);
            financeTracker.setInvestment(totalInvestment);
            logger.info("Successfully got the financial details for user with id = " + userId);
            return financeTracker;


        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + userId);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the financial details for user " + e.getMessage());
            return null;
        }
    }

    @Override
    public FinanceTracker getDetailsByYear(Long userId, int year) {
        logger.info("Getting financial details for user "+ userId + " on year " + year);

        try {
            FinancialTrackerValidation.validateYear(year);
            UserValidation.validateUser(userId);
            Double totalIncome;
            Double totalExpense;
            Double totalInvestment;
            FinanceTracker financeTracker = new FinanceTracker();

            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            SQLQuery  totalIncomeQuery = session.createSQLQuery("Select sum(i.amount) from Income i where i.user_id = "+ userId + " and YEAR(i.date) = "+year);
            totalIncome = (Double)totalIncomeQuery.uniqueResult();
            SQLQuery  totalExpenseQuery = session.createSQLQuery("Select sum(e.amount) from Expense e where e.user_id = "+ userId + " and YEAR(e.date) = "+year);
            totalExpense = (Double)totalExpenseQuery.uniqueResult();
            SQLQuery totalInvestmentQuery = session.createSQLQuery("Select sum(i.amount) from Investment i where i.user_id = "+ userId + " and YEAR(i.date) = "+year);
            totalInvestment = (Double)totalInvestmentQuery.uniqueResult();
            transaction.commit();
            session.close();

            financeTracker.setIncome(totalIncome);
            financeTracker.setExpense(totalExpense);
            financeTracker.setInvestment(totalInvestment);
            logger.info("Successfully got the financial details for user with id = " + userId);
            return financeTracker;


        } catch (UserNotFoundException e) {
            logger.error("No user is present for user id " + userId);
            return null;
        } catch (Exception e) {
            logger.error("Exception occurred while getting the financial details for user " + e.getMessage());
            return null;
        }
    }
}
