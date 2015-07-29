package com.touyan.investment.bean.message;

import java.io.Serializable;
import java.util.ArrayList;

public class TopMessageList implements Serializable {

    private GroupDetail groupDetal;

    private ArrayList<TopMessages> topMessages;

    public GroupDetail getGroupDetal() {
        return groupDetal;
    }

    public void setGroupDetal(GroupDetail groupDetal) {
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
