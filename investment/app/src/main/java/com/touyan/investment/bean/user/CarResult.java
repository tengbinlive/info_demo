package com.touyan.investment.bean.user;

import java.io.Serializable;

public class CarResult implements Serializable {

    private String start_date;
    private String end_date;
    private String car_status;
    private String car_cost;
    private String car_discount;
    private String car_outtime;
    private String plate_number;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    public String getCar_cost() {
        return car_cost;
    }

    public void setCar_cost(String car_cost) {
        this.car_cost = car_cost;
    }

    public String getCar_discount() {
        return car_discount;
    }

    public void setCar_discount(String car_discount) {
        this.car_discount = car_discount;
    }

    public String getCar_outtime() {
        return car_outtime;
    }

    public void setCar_outtime(String car_outtime) {
        this.car_outtime = car_outtime;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    @Override
    public String toString() {
        return "CarResult{" +
                "start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", car_status='" + car_status + '\'' +
                ", car_cost='" + car_cost + '\'' +
                ", car_discount='" + car_discount + '\'' +
                ", car_outtime='" + car_outtime + '\'' +
                ", plate_number='" + plate_number + '\'' +
                '}';
    }
}
