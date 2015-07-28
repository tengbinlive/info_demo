package com.touyan.investment.hx;

import com.touyan.investment.bean.message.InviteMessage;
import com.touyan.investment.bean.user.User;

import java.util.HashMap;

public class HXCacheUtils {

    private static HXCacheUtils instance;

    public static HXCacheUtils getInstance() {
        if (null == instance) {
            instance = new HXCacheUtils();
        }
        return instance;
    }

    //重置好友列表数据
    //重置群组列表数据
    public void resetData(){
        friendsHashMap = new HashMap<>();
        groupsHashMap = new HashMap<>();
    }

    private HashMap<String, User> friendsHashMap = new HashMap<>();

    private HashMap<String, User> groupsHashMap = new HashMap<>();

    private HashMap<String, InviteMessage> inviteMessageHashMap = new HashMap<>();

    public HashMap<String, InviteMessage> getInviteMessageHashMap() {
        return inviteMessageHashMap;
    }

    public void setInviteMessageHashMap(HashMap<String, InviteMessage> inviteMessageHashMap) {
        this.inviteMessageHashMap = inviteMessageHashMap;
    }

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
                ", inviteMessageHashMap=" + inviteMessageHashMap +
                '}';
    }
}