package com.touyan.investment.event;

import java.util.List;

/**
 * Created by bin on 15/7/23.
 */
public class ContactsListEventType {

    public ContactsListEventType(List<String> usernames) {
        this.usernames = usernames;
    }

    private List<String> usernames;

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    @Override
    public String toString() {
        return "FriendsListEventType{" +
                "usernames=" + usernames +
                '}';
    }
}
