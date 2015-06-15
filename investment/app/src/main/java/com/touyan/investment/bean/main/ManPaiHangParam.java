package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class ManPaiHangParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String pageNo;

    private String type;

    private String labelId;

    private String userId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
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
        if (StringUtil.isBlank(this.pageNo)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userId))) param.put("userId", userId);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pageNo))) param.put("pageNo", pageNo);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(type))) param.put("type", type);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(labelId))) param.put("labelId", labelId);
	}

    @Override
    public String toString() {
        return "ManPaiHangParam{" +
                "pageNo='" + pageNo + '\'' +
                ", type='" + type + '\'' +
                ", labelId='" + labelId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
