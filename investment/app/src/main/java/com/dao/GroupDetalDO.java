package com.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table GroupDetal.
 */
public class GroupDetalDO {

    private Boolean approval;
    private String canadd;
    private String desc;
    private String gphoto;
    private String groupid;
    private String groupname;
    private Integer maxusers;
    private Integer memnum;
    private String owner;
    private Integer payfor;
    private String validt;
    private String visble;
    private String istoll;

    public GroupDetalDO() {
    }

    public GroupDetalDO(String groupid) {
        this.groupid = groupid;
    }

    public GroupDetalDO(Boolean approval, String canadd, String desc, String gphoto, String groupid, String groupname, Integer maxusers, Integer memnum, String owner, Integer payfor, String validt, String visble, String istoll) {
        this.approval = approval;
        this.canadd = canadd;
        this.desc = desc;
        this.gphoto = gphoto;
        this.groupid = groupid;
        this.groupname = groupname;
        this.maxusers = maxusers;
        this.memnum = memnum;
        this.owner = owner;
        this.payfor = payfor;
        this.validt = validt;
        this.visble = visble;
        this.istoll = istoll;
    }

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

}
