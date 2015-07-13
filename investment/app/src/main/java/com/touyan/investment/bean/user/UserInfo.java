package com.touyan.investment.bean.user;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String inrank;
    private String passwd;
    private String servno;
    private String ualias;
    private String uisvip;

    public String getInrank() {
        return inrank;
    }

    public void setInrank(String inrank) {
        this.inrank = inrank;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "inrank='" + inrank + '\'' +
                ", passwd='" + passwd + '\'' +
                ", servno='" + servno + '\'' +
                ", ualias='" + ualias + '\'' +
                ", uisvip='" + uisvip + '\'' +
                '}';
    }
}
