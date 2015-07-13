package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class InvInfoResult extends OpenApiSimpleResult {

    private ArrayList<InvInfoBean> infos;

    public ArrayList<InvInfoBean> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<InvInfoBean> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "InvInfoResult{" +
                "infos=" + infos +
                '}';
    }
}
