package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.UserInfo;

public class LoginResult extends OpenApiSimpleResult {

    private UserInfo usinfo;

    private String appStoreUrl;

    public String getAppStoreUrl() {
        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {
        this.appStoreUrl = appStoreUrl;
    }

    public UserInfo getUsinfo() {
        return usinfo;
    }

    public void setUsinfo(UserInfo usinfo) {
        this.usinfo = usinfo;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "usinfo=" + usinfo +
                ", appStoreUrl='" + appStoreUrl + '\'' +
                '}';
    }
}
