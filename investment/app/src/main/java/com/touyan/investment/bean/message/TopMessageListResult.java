package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class TopMessageListResult extends OpenApiSimpleResult {

    private GroupDetail groupDetail;

    private ArrayList<TopMessages> topMessages;

    public GroupDetail getGroupDetail() {
        return groupDetail;
    }

    public void setGroupDetail(GroupDetail groupDetail) {
        this.groupDetail = groupDetail;
    }

    public ArrayList<TopMessages> getTopMessages() {
        return topMessages;
    }

    public void setTopMessages(ArrayList<TopMessages> topMessages) {
        this.topMessages = topMessages;
    }

    @Override
    public String toString() {
        return "TopMessageListResult{" +
                "groupDetal=" + groupDetail +
                ", topMessages=" + topMessages +
                '}';
    }
}
