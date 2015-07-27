package com.touyan.investment.hx;

import com.touyan.investment.bean.user.User;

import java.util.HashMap;

public class HXUserUtils {

    private static HXUserUtils instance;

    public static HXUserUtils getInstance() {
        if (null == instance) {
            instance = new HXUserUtils();
        }
        return instance;
    }

    private HashMap<String, User> friendsHashMap = new HashMap<>();

    private HashMap<String, User> groupsHashMap = new HashMap<>();

    public HashMap<String, User> getGroupsHashMap() {
        return groupsHashMap;
    }

    public void setGroupsHashMap(HashMap<String, User> groupsHashMap) {
        this.groupsHashMap = groupsHashMap;
    }

    public HashMap<String, User> getFriendsHashMap() {
        return friendsHashMap;
    }

    public void setFriendsHashMap(HashMap<String, User> friendsHashMap) {
        this.friendsHashMap = friendsHashMap;
    }

    @Override
    public String toString() {
        return "HXUserUtils{" +
                "friendsHashMap=" + friendsHashMap +
                ", groupsHashMap=" + groupsHashMap +
                '}';
    }
}