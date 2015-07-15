package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvActParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String infoid;
    private String userid;
    private String actvid;

    public String getActvid() {
        return actvid;
    }

    public void setActvid(String actvid) {
        this.actvid = actvid;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userid)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(actvid))) param.put("actvid", actvid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(infoid))) param.put("infoid", infoid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
    }

    @Override
    public String toString() {
        return "InvInfoDetailParam{" +
                "infoid='" + infoid + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
