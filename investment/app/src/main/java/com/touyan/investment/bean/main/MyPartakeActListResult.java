package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class MyPartakeActListResult extends OpenApiSimpleResult {

    private ArrayList<MyPartakeInvActBean> retActivitys;

    public ArrayList<MyPartakeInvActBean> getRetActivitys() {
        return retActivitys;
    }

    public void setRetActivitys(ArrayList<MyPartakeInvActBean> retActivitys) {
        this.retActivitys = retActivitys;
    }

    @Override
    public String toString() {
        return "MyPartakeActListResult{" +
                "retActivitys=" + retActivitys +
                '}';
    }
}
