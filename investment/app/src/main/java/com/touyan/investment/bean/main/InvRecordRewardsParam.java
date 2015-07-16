package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvRecordRewardsParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String userid;
    private String infoid;
    private String targetid;
    private double amount;

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

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.infoid)) return false;
        if (StringUtil.isBlank(this.targetid)) return false;
        if (this.amount <=0) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(infoid))) param.put("infoid", infoid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(targetid))) param.put("targetid", targetid);
        if (includeEmptyAttr || (!includeEmptyAttr && this.amount >0)) param.put("amount", amount);
    }

    @Override
    public String toString() {
        return "InvRecordRewardsParam{" +
                "userid='" + userid + '\'' +
                ", infoid='" + infoid + '\'' +
                ", targetid='" + targetid + '\'' +
                ", amount=" + amount +
                '}';
    }
}
