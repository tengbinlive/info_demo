package com.touyan.investment.event;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/23.
 */
public class OnContactDeletedEvent {
    private ArrayList<String> usernameList;

    public OnContactDeletedEvent(ArrayList<String> usernameList) {
        // TODO Auto-generated constructor stub
        this.usernameList = usernameList;
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }
}
