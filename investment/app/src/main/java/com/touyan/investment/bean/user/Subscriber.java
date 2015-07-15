package com.touyan.investment.bean.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/15.
 */
public class Subscriber implements Serializable {

    private String isorder;
    private String scrino;
    private String servno;
    private UserInfo user;

    public String getIsorder() {
        return isorder;
    }

    public void setIsorder(String isorder) {
        this.isorder = isorder;
    }

    public String getScrino() {
        return scrino;
    }

    public void setScrino(String scrino) {
        this.scrino = scrino;
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "isorder='" + isorder + '\'' +
                ", scrino='" + scrino + '\'' +
                ", servno='" + servno + '\'' +
                ", user=" + user +
                '}';
    }
}
