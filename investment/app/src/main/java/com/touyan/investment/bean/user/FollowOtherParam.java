package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/21.
 */
public class FollowOtherParam extends OpenApiBaseRequest implements OpenApiRequestInterface {
    private String scrino;
    private String userid;

    public String getScrino() {
        return scrino;
    }

    public void setScrino(String scrino) {
        this.scrino = scrino;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.scrino)) return false;
        if (StringUtil.isBlank(this.userid)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(scrino))) param.put("scrino", scrino);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
    }
    @Override
    public String toString() {
        return "FollowOtherParam{" +
                "scrino='" + scrino + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
