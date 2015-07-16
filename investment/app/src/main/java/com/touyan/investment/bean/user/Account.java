package com.touyan.investment.bean.user;

import java.io.Serializable;

public class Account implements Serializable {

    private double amount;
    private double frozen;
    private double income;
    private double losted;
    private double uavail;
    private String userid;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(double frozen) {
        this.frozen = frozen;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getLosted() {
        return losted;
    }

    public void setLosted(double losted) {
        this.losted = losted;
    }

    public double getUavail() {
        return uavail;
    }

    public void setUavail(double uavail) {
        this.uavail = uavail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Account{" +
                "amount=" + amount +
                ", frozen=" + frozen +
                ", income=" + income +
                ", losted=" + losted +
                ", uavail=" + uavail +
                ", userid='" + userid + '\'' +
                '}';
    }
}
