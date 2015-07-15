package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class InvReplysResult extends OpenApiSimpleResult {

    private ArrayList<InvReplysBean> replys;

    public ArrayList<InvReplysBean> getReplys() {
        return replys;
    }

    public void setReplys(ArrayList<InvReplysBean> replys) {
        this.replys = replys;
    }

    @Override
    public String toString() {
        return "InvReplysResult{" +
                "replys=" + replys +
                '}';
    }
}
