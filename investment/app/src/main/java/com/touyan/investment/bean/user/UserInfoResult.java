package com.touyan.investment.bean.user;

import java.io.Serializable;

public class UserInfoResult implements Serializable {

    private String coupon_id;
    private String coupon_starttime;
    private String coupon_endtime;
    private ParkBean coupon_park;
    private String money;
    private String coupon_status;
    private String coupon_url;
    private String coupon_size;

    public String getCoupon_status() {
        return coupon_status;
    }

    public void setCoupon_status(String coupon_status) {
        this.coupon_status = coupon_status;
    }

    public String getCoupon_url() {
        return coupon_url;
    }

    public void setCoupon_url(String coupon_url) {
        this.coupon_url = coupon_url;
    }

    public String getCoupon_size() {
        return coupon_size;
    }

    public void setCoupon_size(String coupon_size) {
        this.coupon_size = coupon_size;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_starttime() {
        return coupon_starttime;
    }

    public void setCoupon_starttime(String coupon_starttime) {
        this.coupon_starttime = coupon_starttime;
    }

    public String getCoupon_endtime() {
        return coupon_endtime;
    }

    public void setCoupon_endtime(String coupon_endtime) {
        this.coupon_endtime = coupon_endtime;
    }

    public ParkBean getCoupon_park() {
        return coupon_park;
    }

    public void setCoupon_park(ParkBean coupon_park) {
        this.coupon_park = coupon_park;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isExpired(){
        return coupon_status.equals("1");
    }
    @Override
    public String toString() {
        return "UserCouponListResult{" +
                "coupon_id='" + coupon_id + '\'' +
                ", coupon_starttime='" + coupon_starttime + '\'' +
                ", coupon_endtime='" + coupon_endtime + '\'' +
                ", coupon_park=" + coupon_park +
                ", money='" + money + '\'' +
                ", coupon_status='" + coupon_status + '\'' +
                ", coupon_url='" + coupon_url + '\'' +
                ", coupon_size='" + coupon_size + '\'' +
                '}';
    }
}
