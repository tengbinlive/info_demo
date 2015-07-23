package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class TopMessageListParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String mesgid;
    private String groupid;

    public String getMesgid() {
        return mesgid;
    }

    public void setMesgid(String mesgid) {
        this.mesgid = mesgid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.mesgid)) return false;
        if (StringUtil.isBlank(this.groupid)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(mesgid))) param.put("mesgid", mesgid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(groupid))) param.put("groupid", groupid);
    }

    @Override
    public String toString() {
        return "TopMessageParam{" +
                "mesgid='" + mesgid + '\'' +
                ", groupid='" + groupid + '\'' +
                '}';
    }
}
