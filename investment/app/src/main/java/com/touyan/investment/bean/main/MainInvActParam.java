package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class MainInvActParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String manId;

    private String userId;

    public String getManId() {
        return manId;
    }

    public void setManId(String manId) {
        this.manId = manId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
	public boolean validate() {
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userId))) param.put("userId", userId);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(manId))) param.put("manId", manId);
	}

    @Override
    public String toString() {
        return "ManDetailParam{" +
                "manId='" + manId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
