package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class UserMsgParam extends OpenApiBaseRequest implements OpenApiRequestInterface {


    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userId)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userId))) param.put("userId", userId);
    }

    @Override
    public String toString() {
        return "UserParam{" +
                ", userId='" + userId + '\'' +
                '}';
    }
}
