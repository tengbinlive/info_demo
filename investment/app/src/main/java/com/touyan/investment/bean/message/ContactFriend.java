package com.touyan.investment.bean.message;

import com.touyan.investment.bean.user.UserInfo;

/**
 * Created by Administrator on 2015/7/28.
 */
public class ContactFriend {

    private String servno;
    private int role;
    private UserInfo userinfo;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public String toString() {
        return "ContactFriend{" +
                "servno='" + servno + '\'' +
                ", role=" + role +
                ", userinfo=" + userinfo +
                '}';
    }
}
