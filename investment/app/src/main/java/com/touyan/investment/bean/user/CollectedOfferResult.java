package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.main.InvActBean;
import com.touyan.investment.bean.main.InvOfferBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public class CollectedOfferResult extends OpenApiSimpleResult {

    private ArrayList<InvOfferBean> retRewards;

    public ArrayList<InvOfferBean> getRetRewards() {
        return retRewards;
    }

    public void setRetRewards(ArrayList<InvOfferBean> retRewards) {
        this.retRewards = retRewards;
    }

    @Override
    public String toString() {
        return "CollectedOfferResult{" +
                "retRewards=" + retRewards +
                '}';
    }
}
