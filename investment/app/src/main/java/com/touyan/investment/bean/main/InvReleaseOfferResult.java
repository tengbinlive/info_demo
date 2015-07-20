package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.UserInfo;

import java.util.ArrayList;

public class InvReleaseOfferResult extends OpenApiSimpleResult {

    private InvOfferBean reward;

    public InvOfferBean getReward() {
        return reward;
    }

    public void setReward(InvOfferBean reward) {
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "InvReleaseOfferResult{" +
                "reward=" + reward +
                '}';
    }
}
