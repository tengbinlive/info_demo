package com.touyan.investment.bean.user;

import java.io.Serializable;

public class ParkBean implements Serializable {

    private String park_id;
    private String park_name;

    public String getPark_id() {
        return park_id;
    }

    public void setPark_id(String park_id) {
        this.park_id = park_id;
    }

    public String getPark_name() {
        return park_name!=null&park_name.equals("-1")?"全停车场通用":park_name;
    }

    public void setPark_name(String park_name) {
        this.park_name = park_name;
    }

    @Override
    public String toString() {
        return "ParkBean{" +
                "park_id='" + park_id + '\'' +
                ", park_name='" + park_name + '\'' +
                '}';
    }
}
