package com.finance.FinanceTraker.service;

import com.finance.FinanceTraker.database.tables.*;
import com.finance.FinanceTraker.model.FinanceTracker;
import com.finance.FinanceTraker.request.*;
import com.finance.FinanceTraker.response.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface FinanceTrackerService {

    public UserResponse registration(UserRequest request);

    public User getUser(Long id);

    public IncomeResponse addIncome(IncomeRequest request);

    public List<Income> getIncome(Long id);

    public Income modifyIncome(IncomeRequest request);
    public IncomeResponse deleteIncome(Long id);
    public ExpenseResponse addExpense(ExpenseRequest request);

    public List<Expense> getExpense(Long id);

    public Expense updateExpense(ExpenseRequest request);
    public ExpenseResponse deleteExpense(Long id);
    public InvestmentResponse addInvestment(InvestmentRequest request);

    public List<Investment> getInvestment(Long id);

    public Investment updateInvestment(InvestmentRequest request);
    public InvestmentResponse deleteInvestment(Long id);

    public FinancialGoalResponse addFinancialGoal(FinancialGoalRequest request);

    public List<FinancialGoal> getFinancialGoal(Long id);

    public FinancialGoal updateFinancialGoal(FinancialGoalRequest request);
    public FinancialGoalResponse deleteFinancialGoal(Long id);

    public ReminderResponse addReminder(ReminderRequest request);

    public List<Reminder> getReminder(Long id);

    public Reminder updateReminder(ReminderRequest request);
    public ReminderResponse deleteReminder(Long id);
    public LendMoneyResponse addLendMoney(LendMoneyRequest request);

    public List<LendMoney> getLendMoney(Long id);

    public LendMoney updateLendMoney(LendMoneyRequest request);
    public LendMoneyResponse deleteLendMoney(Long id);


    public FinanceTracker getDetailsByDate(Long userId, Date date);

    public FinanceTracker getDetailsLessThanByDate(Long userId, Date date);

    public FinanceTracker getDetailsByYear(Long userId, int year);
}
