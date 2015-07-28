package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class TopMessageListResult extends OpenApiSimpleResult {

    private ArrayList<TopMessageList> allTopMessage;


    public ArrayList<TopMessageList> getAllTopMessage() {
        return allTopMessage;
    }

    public void setAllTopMessage(ArrayList<TopMessageList> allTopMessage) {
        this.allTopMessage = allTopMessage;
    }

    @Override
    public String toString() {
        return "TopMessageListResult{" +
                "allTopMessage=" + allTopMessage +
                '}';
    }
}
