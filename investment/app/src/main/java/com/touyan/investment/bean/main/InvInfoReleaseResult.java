package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class InvInfoReleaseResult extends OpenApiSimpleResult {

    private InvInfoBean info;

    public InvInfoBean getInfo() {
        return info;
    }

    public void setInfo(InvInfoBean info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "InvInfoReleaseResult{" +
                "info=" + info +
                '}';
    }
}
