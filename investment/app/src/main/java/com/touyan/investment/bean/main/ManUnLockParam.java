package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class ManUnLockParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String manId;

    private String photosId;

    private String userId;

    public String getPhotosId() {
        return photosId;
    }

    public void setPhotosId(String photosId) {
        this.photosId = photosId;
    }

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
        if (StringUtil.isBlank(this.userId)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userId))) param.put("userId", userId);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(manId))) param.put("manId", manId);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(photosId))) param.put("photosId", photosId);
	}

    @Override
    public String toString() {
        return "ManUnLockParam{" +
                "manId='" + manId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
