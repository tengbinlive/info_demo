package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class MyPartakeOfferListResult extends OpenApiSimpleResult {

    private ArrayList<InvOfferBean> retRewards;

    public ArrayList<InvOfferBean> getRetRewards() {
        return retRewards;
    }

    public void setRetRewards(ArrayList<InvOfferBean> retRewards) {
        this.retRewards = retRewards;
    }

    @Override
    public String toString() {
        return "MyPartakeOfferListResult{" +
                "retRewards=" + retRewards +
                '}';
    }
}
