package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class InvOfferListResult extends OpenApiSimpleResult {

    private ArrayList<InvOfferBean> rewards;

    public ArrayList<InvOfferBean> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<InvOfferBean> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "InvOfferListResult{" +
                "rewards=" + rewards +
                '}';
    }
}
