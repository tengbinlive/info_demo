package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class MyActListResult extends OpenApiSimpleResult {

    private ArrayList<InvActBean> activitys;

    public ArrayList<InvActBean> getActivitys() {
        return activitys;
    }

    public void setActivitys(ArrayList<InvActBean> activitys) {
        this.activitys = activitys;
    }

    @Override
    public String toString() {
        return "MyActListResult{" +
                "activitys=" + activitys +
                '}';
    }
}
