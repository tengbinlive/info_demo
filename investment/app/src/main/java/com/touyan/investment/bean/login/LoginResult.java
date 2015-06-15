package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiSimpleResult;

public class LoginResult extends OpenApiSimpleResult {

    private String userid;
    private String has_master;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHas_master() {
        return has_master;
    }

    public void setHas_master(String has_master) {
        this.has_master = has_master;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "userid='" + userid + '\'' +
                ", has_master='" + has_master + '\'' +
                '}';
    }
}
