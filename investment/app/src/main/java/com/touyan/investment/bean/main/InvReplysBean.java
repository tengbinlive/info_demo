package com.touyan.investment.bean.main;

import java.io.Serializable;
import java.sql.Date;

public class InvReplysBean implements Serializable {

    public final static String SHOW_NO = "1";
    public final static String SHOW_YES = "0";

    private String contnt; //内容

    private String iadopt; //是否被采纳，资讯不用理这个字段

    private String isShow; //是否显示（悬赏）

    private String mesgid; //资讯或悬赏ID

    private String mesgtp;

    private String repyid;//回复ID

    private Date rptime; //回复时间

    private String rpuser; //回复者ID

    private InvInfoUserInfo user;// 回复者用户信息

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public String getIadopt() {
        return iadopt;
    }

    public void setIadopt(String iadopt) {
        this.iadopt = iadopt;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getMesgid() {
        return mesgid;
    }

    public void setMesgid(String mesgid) {
        this.mesgid = mesgid;
    }

    public String getMesgtp() {
        return mesgtp;
    }

    public void setMesgtp(String mesgtp) {
        this.mesgtp = mesgtp;
    }

    public String getRepyid() {
        return repyid;
    }

    public void setRepyid(String repyid) {
        this.repyid = repyid;
    }

    public Date getRptime() {
        return rptime;
    }

    public void setRptime(Date rptime) {
        this.rptime = rptime;
    }

    public String getRpuser() {
        return rpuser;
    }

    public void setRpuser(String rpuser) {
        this.rpuser = rpuser;
    }

    public InvInfoUserInfo getUser() {
        return user;
    }

    public void setUser(InvInfoUserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvReplysBean{" +
                "contnt='" + contnt + '\'' +
                ", iadopt='" + iadopt + '\'' +
                ", isShow='" + isShow + '\'' +
                ", mesgid='" + mesgid + '\'' +
                ", mesgtp='" + mesgtp + '\'' +
                ", repyid='" + repyid + '\'' +
                ", rptime=" + rptime +
                ", rpuser='" + rpuser + '\'' +
                ", user=" + user +
                '}';
    }
}
