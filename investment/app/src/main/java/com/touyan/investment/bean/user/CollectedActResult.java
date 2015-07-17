package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.main.InvActBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public class CollectedActResult extends OpenApiSimpleResult {

    private ArrayList<InvActBean> retActivitys;

    public ArrayList<InvActBean> getRetActivitys() {
        return retActivitys;
    }

    public void setRetActivitys(ArrayList<InvActBean> retActivitys) {
        this.retActivitys = retActivitys;
    }

    @Override
    public String toString() {
        return "CollectedActResult{" +
                "retActivitys=" + retActivitys +
                '}';
    }
}
