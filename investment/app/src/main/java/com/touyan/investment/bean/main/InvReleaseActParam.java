package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvReleaseActParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    //活动类型
    public final static String TYPE_ACT = "007";
    public final static String TYPE_ROADSHOW = "002";

    public final static String PUBLIC_NO = "1";
    public final static String PUBLIC_YES = "0";

    private String actvtp;//类型
    private String atitle;//标题
    private String contnt;//内容
    private String startm;//开始时间
    private String endtim;//结束时间
    private String byloct;//所在地
    private String adress;//详细地址
    private String pubsid;//发布人ID(手机号)
    private double charge;//活动花费
    private int pintno;//参加人数
    private String ndchck;//是否需要审核(0-不需要,1-需要)
    private String ispubl;//是否公共 0公开，1不公开
    private String ckstau;//审核状态
    private double minpay;//最少支付
    private double maxpay;//最高支付
    private String paytyp;//支付类型
    private String acount;//账号

    public String getActvtp() {
        return actvtp;
    }

    public void setActvtp(String actvtp) {
        this.actvtp = actvtp;
    }

    public String getAtitle() {
        return atitle;
    }

    public void setAtitle(String atitle) {
        this.atitle = atitle;
    }

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public String getStartm() {
        return startm;
    }

    public void setStartm(String startm) {
        this.startm = startm;
    }

    public String getEndtim() {
        return endtim;
    }

    public void setEndtim(String endtim) {
        this.endtim = endtim;
    }

    public String getByloct() {
        return byloct;
    }

    public void setByloct(String byloct) {
        this.byloct = byloct;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPubsid() {
        return pubsid;
    }

    public void setPubsid(String pubsid) {
        this.pubsid = pubsid;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public int getPintno() {
        return pintno;
    }

    public void setPintno(int pintno) {
        this.pintno = pintno;
    }

    public String getNdchck() {
        return ndchck;
    }

    public void setNdchck(String ndchck) {
        this.ndchck = ndchck;
    }

    public String getIspubl() {
        return ispubl;
    }

    public void setIspubl(String ispubl) {
        this.ispubl = ispubl;
    }

    public String getCkstau() {
        return ckstau;
    }

    public void setCkstau(String ckstau) {
        this.ckstau = ckstau;
    }

    public double getMinpay() {
        return minpay;
    }

    public void setMinpay(double minpay) {
        this.minpay = minpay;
    }

    public double getMaxpay() {
        return maxpay;
    }

    public void setMaxpay(double maxpay) {
        this.maxpay = maxpay;
    }

    public String getPaytyp() {
        return paytyp;
    }

    public void setPaytyp(String paytyp) {
        this.paytyp = paytyp;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.actvtp)) return false;
        if (StringUtil.isBlank(this.atitle)) return false;
        if (StringUtil.isBlank(this.contnt)) return false;
        if (StringUtil.isBlank(this.pubsid)) return false;
        if (StringUtil.isBlank(this.ispubl)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(actvtp))) param.put("actvtp", actvtp);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(atitle))) param.put("atitle", atitle);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(contnt))) param.put("contnt", contnt);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(startm))) param.put("startm", startm);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(endtim))) param.put("endtim", endtim);
        if (includeEmptyAttr || (!includeEmptyAttr )) param.put("byloct", byloct);
        if (includeEmptyAttr || (!includeEmptyAttr )) param.put("adress", adress);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pubsid))) param.put("pubsid", pubsid);
        if (includeEmptyAttr || (!includeEmptyAttr && charge>0)) param.put("charge", charge);
        if (includeEmptyAttr || (!includeEmptyAttr && pintno>0)) param.put("pintno", pintno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ndchck))) param.put("ndchck", ndchck);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ispubl))) param.put("ispubl", ispubl);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ckstau))) param.put("ckstau", ckstau);
        if (includeEmptyAttr || (!includeEmptyAttr && minpay>0)) param.put("minpay", minpay);
        if (includeEmptyAttr || (!includeEmptyAttr && maxpay>0)) param.put("maxpay", maxpay);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(paytyp))) param.put("paytyp", paytyp);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(acount))) param.put("acount", acount);
    }

    @Override
    public String toString() {
        return "InvReleaseActParam{" +
                "actvtp='" + actvtp + '\'' +
                ", atitle='" + atitle + '\'' +
                ", contnt='" + contnt + '\'' +
                ", startm='" + startm + '\'' +
                ", endtim='" + endtim + '\'' +
                ", byloct='" + byloct + '\'' +
                ", adress='" + adress + '\'' +
                ", pubsid='" + pubsid + '\'' +
                ", charge=" + charge +
                ", pintno=" + pintno +
                ", ndchck='" + ndchck + '\'' +
                ", ispubl='" + ispubl + '\'' +
                ", ckstau='" + ckstau + '\'' +
                ", minpay=" + minpay +
                ", maxpay=" + maxpay +
                ", paytyp='" + paytyp + '\'' +
                ", acount='" + acount + '\'' +
                '}';
    }
}
