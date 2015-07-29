package com.touyan.investment.event;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29.
 */
public class OnContactAddedEvent {
    private ArrayList<String> usernameList;

    public OnContactAddedEvent(ArrayList<String> usernameList) {
        // TODO Auto-generated constructor stub
        this.usernameList = usernameList;
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }
}
