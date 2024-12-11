package com.fullStack.expenseTracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GoalSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Double price;
    private Double amountSaved = 0.0;
    private Boolean completed = false;

    @Temporal(TemporalType.DATE)
    private Date targetDate;
    private Double monthlySavings = 0.0;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    @JsonIgnore
    private User user;

    @PrePersist
    public void calculateMonthlySavings() {
        if (price != null && amountSaved != null && targetDate != null) {
            long remainingMonths = (targetDate.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24 * 30); // Approximate months
            if (remainingMonths > 0) {
                this.monthlySavings = (price - amountSaved) / remainingMonths;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmountSaved() {
        return amountSaved;
    }

    public void setAmountSaved(Double amountSaved) {
        this.amountSaved = amountSaved;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Double getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(Double monthlySavings) {
        this.monthlySavings = monthlySavings;
    }
}
