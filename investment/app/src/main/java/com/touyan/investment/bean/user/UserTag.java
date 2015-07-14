package com.touyan.investment.bean.user;

/**
 * Created by Administrator on 2015/7/14.
 */
public class UserTag {

    private String tgname;

    public String getTgname() {
        return tgname;
    }

    public void setTgname(String tgname) {
        this.tgname = tgname;
    }

    @Override
    public String toString() {
        return "UserTag{" +
                "tgname='" + tgname + '\'' +
                '}';
    }
}
