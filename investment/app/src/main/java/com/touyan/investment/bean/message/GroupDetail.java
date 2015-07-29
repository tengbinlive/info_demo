package com.touyan.investment.bean.message;

import java.io.Serializable;

public class GroupDetail implements Serializable {

    private Boolean approval;
    private String canadd;
    private String desc;
    private String gphoto;
    private String groupid;
    private String groupname;
    private String  istoll ;//是否收费 0不收费，1收费
    private Integer maxusers;
    private Integer memnum;
    private String owner;
    private Integer payfor;
    private String validt;
    private String visble;

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public String getCanadd() {
        return canadd;
    }

    public void setCanadd(String canadd) {
        this.canadd = canadd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGphoto() {
        return gphoto;
    }

    public void setGphoto(String gphoto) {
        this.gphoto = gphoto;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public Integer getMemnum() {
        return memnum;
    }

    public void setMemnum(Integer memnum) {
        this.memnum = memnum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getPayfor() {
        return payfor;
    }

    public void setPayfor(Integer payfor) {
        this.payfor = payfor;
    }

    public String getValidt() {
        return validt;
    }

    public void setValidt(String validt) {
        this.validt = validt;
    }

    public String getVisble() {
        return visble;
    }

    public void setVisble(String visble) {
        this.visble = visble;
    }

    public String getIstoll() {
        return istoll;
    }

    public void setIstoll(String istoll) {
        this.istoll = istoll;
    }

    @Override
    public String toString() {
        return "GroupDetail{" +
                "approval=" + approval +
                ", canadd='" + canadd + '\'' +
                ", desc='" + desc + '\'' +
                ", gphoto='" + gphoto + '\'' +
                ", groupid='" + groupid + '\'' +
                ", groupname='" + groupname + '\'' +
                ", istoll='" + istoll + '\'' +
                ", maxusers=" + maxusers +
                ", memnum=" + memnum +
                ", owner='" + owner + '\'' +
                ", payfor=" + payfor +
                ", validt='" + validt + '\'' +
                ", visble='" + visble + '\'' +
                '}';
    }
}
