package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

/**
 * Created by Administrator on 2015/7/14.
 */
public class ModifyUserInfoResult extends OpenApiSimpleResult {
    private UserInfo usinfo;


    public UserInfo getUsinfo() {
        return usinfo;
    }

    public void setUsinfo(UserInfo usinfo) {
        this.usinfo = usinfo;
    }

    @Override
    public String toString() {
        return "ModifyUserInfoResult{" +
                "usinfo=" + usinfo +
                '}';
    }
}
