package com.touyan.investment.bean.user;

import java.io.Serializable;

public class UserOrderListResult implements Serializable {

    public final static String RESERVATION = "预约订单";
    public final static String FARE = "车费订单";

    public final static String NOT_PAY = "未支付";
    public final static String SUCCESS_PAY = "已支付";
    private String order_ID;
    private String order_status;
    private String order_type;
    private String order_date;
    private String park_name;
    private String park_charge;
    private String order_totalamount;
    private CarResult car;

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getPark_name() {
        return park_name;
    }

    public void setPark_name(String park_name) {
        this.park_name = park_name;
    }

    public String getPark_charge() {
        return park_charge;
    }

    public void setPark_charge(String park_charge) {
        this.park_charge = park_charge;
    }

    public String getOrder_totalamount() {
        return order_totalamount;
    }

    public void setOrder_totalamount(String order_totalamount) {
        this.order_totalamount = order_totalamount;
    }

    public CarResult getCar() {
        return car;
    }

    public void setCar(CarResult car) {
        this.car = car;
    }

    ///////////////////扩展///////////////////////

    public boolean isPay(){
        return !order_status.equals("未支付");
    }

    public boolean isFareOrder(){
        return order_type.equals(FARE);
    }

    @Override
    public String toString() {
        return "UserOrderListResult{" +
                "order_ID='" + order_ID + '\'' +
                ", order_status='" + order_status + '\'' +
                ", order_type='" + order_type + '\'' +
                ", order_date='" + order_date + '\'' +
                ", park_name='" + park_name + '\'' +
                ", park_charge='" + park_charge + '\'' +
                ", order_totalamount='" + order_totalamount + '\'' +
                ", car=" + car +
                '}';
    }
}
