package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

public class TopMessageListResult extends OpenApiSimpleResult {

    private GroupDetal groupDetal;

    private ArrayList<TopMessages> topMessages;

    public GroupDetal getGroupDetal() {
        return groupDetal;
    }

    public void setGroupDetal(GroupDetal groupDetal) {
        this.groupDetal = groupDetal;
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
                "groupDetal=" + groupDetal +
                ", topMessages=" + topMessages +
                '}';
    }
}
