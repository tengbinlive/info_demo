package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

/**
 * Created by Administrator on 2015/7/14.
 */
public class ModifyUserInfoResult extends OpenApiSimpleResult {

    private UserInfo user;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ModifyUserInfoResult{" +
                "user=" + user +
                '}';
    }
}
