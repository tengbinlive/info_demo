package com.touyan.investment.bean.user;

import java.io.Serializable;

public class TagBean implements Serializable {

    private String servno;
    private String tgname;
    private String utagid;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getTgname() {
        return tgname;
    }

    public void setTgname(String tgname) {
        this.tgname = tgname;
    }

    public String getUtagid() {
        return utagid;
    }

    public void setUtagid(String utagid) {
        this.utagid = utagid;
    }

    @Override
    public String toString() {
        return "TagBean{" +
                "servno='" + servno + '\'' +
                ", tgname='" + tgname + '\'' +
                ", utagid='" + utagid + '\'' +
                '}';
    }
}
