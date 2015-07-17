package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvReleaseInfoParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    public final static String PUBLIC_NO = "1";
    public final static String PUBLIC_YES = "0";

    private String ititle;//资讯标题
    private String contnt;//内容
    private String pictue;//图片组（之间有逗号隔开）
    private Double charge;//费用
    private String pubsid;//发布者ID
    private String ispubl;//是否公共 0公开，1不公开

    public String getItitle() {
        return ititle;
    }

    public void setItitle(String ititle) {
        this.ititle = ititle;
    }

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public String getPictue() {
        return pictue;
    }

    public void setPictue(String pictue) {
        this.pictue = pictue;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getPubsid() {
        return pubsid;
    }

    public void setPubsid(String pubsid) {
        this.pubsid = pubsid;
    }

    public String getIspubl() {
        return ispubl;
    }

    public void setIspubl(String ispubl) {
        this.ispubl = ispubl;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.ititle)) return false;
        if (StringUtil.isBlank(this.contnt)) return false;
        if (StringUtil.isBlank(this.pubsid)) return false;
        if (StringUtil.isBlank(this.ispubl)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ititle))) param.put("ititle", ititle);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(contnt))) param.put("contnt", contnt);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(contnt))) param.put("contnt", contnt);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pictue))) param.put("pictue", pictue);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pubsid))) param.put("pubsid", pubsid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ispubl))) param.put("ispubl", ispubl);
        if (includeEmptyAttr || (!includeEmptyAttr && null!=charge&&charge>0)) param.put("charge", charge);
    }

    @Override
    public String toString() {
        return "InvReleaseInfoParam{" +
                "ititle='" + ititle + '\'' +
                ", contnt='" + contnt + '\'' +
                ", pictue='" + pictue + '\'' +
                ", charge=" + charge +
                ", pubsid='" + pubsid + '\'' +
                ", ispubl='" + ispubl + '\'' +
                '}';
    }
}
