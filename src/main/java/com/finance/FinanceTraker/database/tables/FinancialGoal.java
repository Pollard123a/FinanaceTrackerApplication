package com.finance.FinanceTraker.database.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="financial_goal")
public class FinancialGoal {

    @Id
    private Long id;
    private String description;
    private double amount;
    private Date currentDate;
    private Date acheiveDate;
    private boolean isCompleted;
    @ManyToOne(fetch= FetchType.EAGER)
    @JsonIgnore
    private User user;

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

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getAcheiveDate() {
        return acheiveDate;
    }

    public void setAcheiveDate(Date acheiveDate) {
        this.acheiveDate = acheiveDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FinancialGoal{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", currentDate=" + currentDate +
                ", acheiveDate=" + acheiveDate +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
