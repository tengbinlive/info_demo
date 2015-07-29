package com.touyan.investment.bean.user;

import com.touyan.investment.helper.PinYinUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable {

    public static final String ISVIP_CODE = "0";

    private Long id;
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
    private double uavail;

    private String nameSort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //特殊处理
    public String getNameSort() {
        if (null == nameSort) {
            String key = PinYinUtil.getFirstSpell(ualias);
            key = key.replaceAll(" ", "");
            nameSort = String.valueOf(key.charAt(0));
        }
        return nameSort;
    }

    public void setNameSort(String nameSort) {
        this.nameSort = nameSort;
    }

    public double getUavail() {
        return uavail;
    }

    public void setUavail(double uavail) {
        this.uavail = uavail;
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
                "id=" + id +
                ", servno='" + servno + '\'' +
                ", aucode='" + aucode + '\'' +
                ", compny='" + compny + '\'' +
                ", inrank='" + inrank + '\'' +
                ", ivcode='" + ivcode + '\'' +
                ", locatn='" + locatn + '\'' +
                ", passwd='" + passwd + '\'' +
                ", postin='" + postin + '\'' +
                ", rscope='" + rscope + '\'' +
                ", tags='" + tags + '\'' +
                ", teleph='" + teleph + '\'' +
                ", ualias='" + ualias + '\'' +
                ", uisvip='" + uisvip + '\'' +
                ", uphoto='" + uphoto + '\'' +
                ", uavail=" + uavail +
                ", nameSort='" + nameSort + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (servno != null ? !servno.equals(userInfo.servno) : userInfo.servno != null) return false;

        return true;

    }


}
