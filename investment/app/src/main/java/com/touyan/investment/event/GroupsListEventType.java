package com.touyan.investment.event;

import com.easemob.chat.EMGroup;

import java.util.List;

/**
 * Created by bin on 15/7/23.
 */
public class GroupsListEventType {
    public GroupsListEventType(List<EMGroup> groups) {
        this.groups = groups;
    }

    private List<EMGroup> groups;

    public List<EMGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<EMGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "GroupsListEventType{" +
                "groups=" + groups +
                '}';
    }
}
