package com.touyan.investment.event;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/23.
 */
public class OnGroupsUpdataEvent {
    private ArrayList<String> groupsList;

    public OnGroupsUpdataEvent(ArrayList<String> groupsList) {
        // TODO Auto-generated constructor stub
        this.groupsList = groupsList;
    }

    public ArrayList<String> getGroupsList() {
        return groupsList;
    }
}
