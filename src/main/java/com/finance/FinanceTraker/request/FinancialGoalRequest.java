package com.finance.FinanceTraker.request;

import java.util.Date;

public class FinancialGoalRequest {
    private Long id;
    private String description;
    private double amount;
    private boolean isCompleted;
    private Date acheiveDate;
    private long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getAcheiveDate() {
        return acheiveDate;
    }

    public void setAcheiveDate(Date acheiveDate) {
        this.acheiveDate = acheiveDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
