package com.touyan.investment.bean.user;

import java.io.Serializable;

public class User implements Serializable {

    public static String TYPE_FRIENDS = "FRIENDS";
    public static String TYPE_GROUPS = "GROUPS";

    private int unreadMsgCount;
    private String header;
    private String avatar;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "unreadMsgCount=" + unreadMsgCount +
                ", header='" + header + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
