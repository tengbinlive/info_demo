package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvActSignParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String userid;
    private String actvid;
    private String price;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getActvid() {
        return actvid;
    }

    public void setActvid(String actvid) {
        this.actvid = actvid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.actvid)) return false;
        if (StringUtil.isBlank(this.price)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(actvid))) param.put("actvid", actvid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(price))) param.put("price", price);
    }

    @Override
    public String toString() {
        return "InvActSignParam{" +
                "userid='" + userid + '\'' +
                ", actvid='" + actvid + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
