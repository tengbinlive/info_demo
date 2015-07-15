package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.UserInfo;

public class InvActDetailResult extends OpenApiSimpleResult {

    private InvActBean detail;

    private UserInfo user;

    public InvActBean getDetail() {
        return detail;
    }

    public void setDetail(InvActBean detail) {
        this.detail = detail;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvActDetailResult{" +
                "detail=" + detail +
                ", user=" + user +
                '}';
    }
}
