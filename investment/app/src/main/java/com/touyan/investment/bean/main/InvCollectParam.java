package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvCollectParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    public final static String TYPE_INFO = "1";
    public final static String TYPE_ACT = "2";
    public final static String TYPE_OFFER = "3";

    private String mesgid;
    private String mesgtp;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMesgid() {
        return mesgid;
    }

    public void setMesgid(String mesgid) {
        this.mesgid = mesgid;
    }

    public String getMesgtp() {
        return mesgtp;
    }

    public void setMesgtp(String mesgtp) {
        this.mesgtp = mesgtp;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.mesgid)) return false;
        if (StringUtil.isBlank(this.mesgtp)) return false;
        if (StringUtil.isBlank(this.userid)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(mesgid))) param.put("mesgid", mesgid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(mesgtp))) param.put("mesgtp", mesgtp);
    }

    @Override
    public String toString() {
        return "InvReViewParam{" +
                "mesgid='" + mesgid + '\'' +
                ", userid='" + userid + '\'' +
                ", mesgtp='" + mesgtp + '\'' +
                '}';
    }
}
