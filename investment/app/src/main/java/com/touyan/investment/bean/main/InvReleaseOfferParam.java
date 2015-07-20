package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvReleaseOfferParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    public final static String PUBLIC_NO = "1";
    public final static String PUBLIC_YES = "0";

    private String rtitle;//标题
    private String contnt;//内容
    private int amount;//悬赏金额
    private String endtim;//结束时间
    private String isshow;//是否公共 0公开，1不公开
    private String pubsid;//发布人ID(手机号)

    public String getRtitle() {
        return rtitle;
    }

    public void setRtitle(String rtitle) {
        this.rtitle = rtitle;
    }

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEndtim() {
        return endtim;
    }

    public void setEndtim(String endtim) {
        this.endtim = endtim;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getPubsid() {
        return pubsid;
    }

    public void setPubsid(String pubsid) {
        this.pubsid = pubsid;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.contnt)) return false;
        if (StringUtil.isBlank(this.isshow)) return false;
        if (StringUtil.isBlank(this.pubsid)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr )) param.put("rtitle", rtitle);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(contnt))) param.put("contnt", contnt);
        if (includeEmptyAttr || (!includeEmptyAttr )) param.put("amount", amount);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(isshow))) param.put("endtim", endtim);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(isshow))) param.put("isshow", isshow);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pubsid))) param.put("pubsid", pubsid);
    }

    @Override
    public String toString() {
        return "InvReleaseOfferParam{" +
                "rtitle='" + rtitle + '\'' +
                ", contnt='" + contnt + '\'' +
                ", amount=" + amount +
                ", endtim='" + endtim + '\'' +
                ", isshow='" + isshow + '\'' +
                ", pubsid='" + pubsid + '\'' +
                '}';
    }
}
