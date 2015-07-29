package com.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table UserInfo.
 */
public class UserInfoDO {

    private Long id;
    private String servno;
    private String aucode;
    private String compny;
    private String inrank;
    private String ivcode;
    private String locatn;
    private String postin;
    private String rscope;
    private String tags;
    private String teleph;
    private String ualias;
    private String uisvip;
    private String uphoto;
    private Double uavail;
    private String nameSort;

    public UserInfoDO() {
    }

    public UserInfoDO(Long id, String servno) {
        this.id = id;
        this.servno = servno;
    }

    public UserInfoDO(Long id, String servno, String aucode, String compny, String inrank, String ivcode, String locatn, String postin, String rscope, String tags, String teleph, String ualias, String uisvip, String uphoto, Double uavail, String nameSort) {
        this.id = id;
        this.servno = servno;
        this.aucode = aucode;
        this.compny = compny;
        this.inrank = inrank;
        this.ivcode = ivcode;
        this.locatn = locatn;
        this.postin = postin;
        this.rscope = rscope;
        this.tags = tags;
        this.teleph = teleph;
        this.ualias = ualias;
        this.uisvip = uisvip;
        this.uphoto = uphoto;
        this.uavail = uavail;
        this.nameSort = nameSort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getAucode() {
        return aucode;
    }

    public void setAucode(String aucode) {
        this.aucode = aucode;
    }

    public String getCompny() {
        return compny;
    }

    public void setCompny(String compny) {
        this.compny = compny;
    }

    public String getInrank() {
        return inrank;
    }

    public void setInrank(String inrank) {
        this.inrank = inrank;
    }

    public String getIvcode() {
        return ivcode;
    }

    public void setIvcode(String ivcode) {
        this.ivcode = ivcode;
    }

    public String getLocatn() {
        return locatn;
    }

    public void setLocatn(String locatn) {
        this.locatn = locatn;
    }

    public String getPostin() {
        return postin;
    }

    public void setPostin(String postin) {
        this.postin = postin;
    }

    public String getRscope() {
        return rscope;
    }

    public void setRscope(String rscope) {
        this.rscope = rscope;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTeleph() {
        return teleph;
    }

    public void setTeleph(String teleph) {
        this.teleph = teleph;
    }

    public String getUalias() {
        return ualias;
    }

    public void setUalias(String ualias) {
        this.ualias = ualias;
    }

    public String getUisvip() {
        return uisvip;
    }

    public void setUisvip(String uisvip) {
        this.uisvip = uisvip;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public Double getUavail() {
        return uavail;
    }

    public void setUavail(Double uavail) {
        this.uavail = uavail;
    }

    public String getNameSort() {
        return nameSort;
    }

    public void setNameSort(String nameSort) {
        this.nameSort = nameSort;
    }

}
