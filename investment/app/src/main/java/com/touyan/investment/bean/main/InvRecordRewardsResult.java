package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

public class InvRecordRewardsResult extends OpenApiSimpleResult {

    private Double rewardsAmount = 0.0;//打赏总金额

    public Double getRewardsAmount() {
        return rewardsAmount;
    }

    public void setRewardsAmount(Double rewardsAmount) {
        this.rewardsAmount = rewardsAmount;
    }

    @Override
    public String toString() {
        return "InvRecordRewardsResult{" +
                "rewardsAmount=" + rewardsAmount +
                '}';
    }
}
