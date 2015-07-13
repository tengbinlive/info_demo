package com.touyan.investment.bean.main;

import java.io.Serializable;

public class InvInfoUserInfo implements Serializable {

    private String servno;
    private String aucode;
    private String compny;
    private String inrank;
    private String ivcode;
    private String locatn;
    private String passwd;
    private String postin;
    private String rscope;
    private String tags;
    private String teleph;
    private String ualias;
    private String uisvip;
    private String uphoto;

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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "servno='" + servno + '\'' +
                "aucode='" + aucode + '\'' +
                ", compny='" + compny + '\'' +
                ", inrank='" + inrank + '\'' +
                ", ivcode='" + ivcode + '\'' +
                ", locatn='" + locatn + '\'' +
                ", passwd='" + passwd + '\'' +
                ", postin='" + postin + '\'' +
                ", rscope='" + rscope + '\'' +
                ", tags=" + tags +
                ", teleph='" + teleph + '\'' +
                ", ualias='" + ualias + '\'' +
                ", uisvip='" + uisvip + '\'' +
                ", uphoto='" + uphoto + '\'' +
                '}';
    }
}
