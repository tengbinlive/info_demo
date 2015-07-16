package com.touyan.investment.bean.user;

import java.io.Serializable;

public class Account implements Serializable {

    private Double amount;
    private Double frozen;
    private Double income;
    private Double losted;
    private Double uavail;
    private String userid;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFrozen() {
        return frozen;
    }

    public void setFrozen(Double frozen) {
        this.frozen = frozen;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getLosted() {
        return losted;
    }

    public void setLosted(Double losted) {
        this.losted = losted;
    }

    public Double getUavail() {
        return uavail;
    }

    public void setUavail(Double uavail) {
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
