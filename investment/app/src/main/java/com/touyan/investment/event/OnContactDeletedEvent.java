package com.touyan.investment.event;

import java.util.List;

/**
 * Created by Administrator on 2015/7/23.
 */
public class OnContactDeletedEvent {
    private List<String> usernameList;

    public OnContactDeletedEvent(List<String> usernameList) {
        // TODO Auto-generated constructor stub
        this.usernameList = usernameList;
    }

    public List<String> getUsernameList() {
        return usernameList;
    }
}
