package com.touyan.investment.bean.message;

import java.io.Serializable;

public class TopMessages implements Serializable {

    private String contnt;
    private String groupid;
    private String mesgid;
    private String mesgtp;
    private String mtitle;
    private String servno;
    private String toptim;

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
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

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getToptim() {
        return toptim;
    }

    public void setToptim(String toptim) {
        this.toptim = toptim;
    }

    @Override
    public String toString() {
        return "TopMessages{" +
                "contnt='" + contnt + '\'' +
                ", groupid='" + groupid + '\'' +
                ", mesgid='" + mesgid + '\'' +
                ", mesgtp='" + mesgtp + '\'' +
                ", mtitle='" + mtitle + '\'' +
                ", servno='" + servno + '\'' +
                ", toptim='" + toptim + '\'' +
                '}';
    }
}
