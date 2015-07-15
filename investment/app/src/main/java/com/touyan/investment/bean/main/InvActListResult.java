package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class InvActListResult extends OpenApiSimpleResult {

    private ArrayList<InvActBean> actives;

    public ArrayList<InvActBean> getActives() {
        return actives;
    }

    public void setActives(ArrayList<InvActBean> actives) {
        this.actives = actives;
    }

    @Override
    public String toString() {
        return "InvActListResult{" +
                "actives=" + actives +
                '}';
    }
}
