package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

public class MainInvActResult extends OpenApiSimpleResult {

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MainInvActResult{" +
                "state='" + state + '\'' +
                '}';
    }
}
