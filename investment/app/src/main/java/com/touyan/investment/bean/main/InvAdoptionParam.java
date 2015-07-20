package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvAdoptionParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String userid;
    private String rpuser;
    private String mesgid;
    private Double price;
    private String replyid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRpuser() {
        return rpuser;
    }

    public void setRpuser(String rpuser) {
        this.rpuser = rpuser;
    }

    public String getMesgid() {
        return mesgid;
    }

    public void setMesgid(String mesgid) {
        this.mesgid = mesgid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.rpuser)) return false;
        if (StringUtil.isBlank(this.mesgid)) return false;
        if (StringUtil.isBlank(this.replyid)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(rpuser))) param.put("rpuser", rpuser);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(mesgid))) param.put("mesgid", mesgid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(replyid))) param.put("replyid", replyid);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("price", price);
    }

    @Override
    public String toString() {
        return "InvAdoptionParam{" +
                "userid='" + userid + '\'' +
                ", rpuser='" + rpuser + '\'' +
                ", mesgid='" + mesgid + '\'' +
                ", price=" + price +
                ", replyid='" + replyid + '\'' +
                '}';
    }
}
