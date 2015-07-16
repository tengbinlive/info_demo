package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvBuyInfoParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String userid;
    private String infoid;
    private double price;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.infoid)) return false;
        if (this.price <=0) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(infoid))) param.put("infoid", infoid);
        if (includeEmptyAttr || (!includeEmptyAttr && this.price >0)) param.put("price", price);
    }

    @Override
    public String toString() {
        return "InvBuyInfoParam{" +
                "userid='" + userid + '\'' +
                ", infoid='" + infoid + '\'' +
                ", price=" + price +
                '}';
    }
}
