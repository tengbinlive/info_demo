package com.touyan.investment.bean.main;

import java.io.Serializable;

public class InvActJoinUsersBean implements Serializable {

    private String compny;
    private String inrank;
    private String locatn;
    private String servno;
    private String ualias;
    private String uisvip;
    private String uphoto;

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

    public String getLocatn() {
        return locatn;
    }

    public void setLocatn(String locatn) {
        this.locatn = locatn;
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
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
        return "InvActJoinUsersBean{" +
                "compny='" + compny + '\'' +
                ", inrank='" + inrank + '\'' +
                ", locatn='" + locatn + '\'' +
                ", servno='" + servno + '\'' +
                ", ualias='" + ualias + '\'' +
                ", uisvip='" + uisvip + '\'' +
                ", uphoto='" + uphoto + '\'' +
                '}';
    }
}
