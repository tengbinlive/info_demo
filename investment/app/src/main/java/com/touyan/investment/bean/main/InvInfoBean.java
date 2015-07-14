package com.touyan.investment.bean.main;

import java.io.Serializable;
import java.sql.Date;

public class InvInfoBean implements Serializable {

    public final static String PUBL_NO = "1";
    public final static String PUBL_YES = "0";

    private String infoid; //资讯ID

    private Date pubstm; //发布时间

    private String pubsid; //发布者ID 用户

    private double charge; //费用

    private String ispubl;//是否公开  0公开，1不公开

    private String contnt; //资讯内容

    private String ititle; //资讯标题

    private String isorder;//我是否订阅了该发布者

    private String pictue;//资讯图片

    private String isbuyer; //是否购买过这条资讯

    private String transNum; //转发次数

    private String replyNum; //评论数

    private String rewardsAmount; //打赏总金额

    private String h5url;//详情

    private InvInfoUserInfo user;//发布者用户信息

    public String getH5url() {
        return h5url;
    }

    public void setH5url(String h5url) {
        this.h5url = h5url;
    }

    public InvInfoUserInfo getUser() {
        return user;
    }

    public void setUser(InvInfoUserInfo user) {
        this.user = user;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    public Date getPubstm() {
        return pubstm;
    }

    public void setPubstm(Date pubstm) {
        this.pubstm = pubstm;
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

    public String getIspubl() {
        return ispubl;
    }

    public void setIspubl(String ispubl) {
        this.ispubl = ispubl;
    }

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public String getItitle() {
        return ititle;
    }

    public void setItitle(String ititle) {
        this.ititle = ititle;
    }

    public String getIsorder() {
        return isorder;
    }

    public void setIsorder(String isorder) {
        this.isorder = isorder;
    }

    public String getPictue() {
        return pictue;
    }

    public void setPictue(String pictue) {
        this.pictue = pictue;
    }

    public String getIsbuyer() {
        return isbuyer;
    }

    public void setIsbuyer(String isbuyer) {
        this.isbuyer = isbuyer;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getRewardsAmount() {
        return rewardsAmount;
    }

    public void setRewardsAmount(String rewardsAmount) {
        this.rewardsAmount = rewardsAmount;
    }

    @Override
    public String toString() {
        return "InvInfoBean{" +
                "infoid='" + infoid + '\'' +
                ", pubstm=" + pubstm +
                ", pubsid='" + pubsid + '\'' +
                ", charge=" + charge +
                ", ispubl='" + ispubl + '\'' +
                ", contnt='" + contnt + '\'' +
                ", ititle='" + ititle + '\'' +
                ", isorder='" + isorder + '\'' +
                ", pictue='" + pictue + '\'' +
                ", isbuyer='" + isbuyer + '\'' +
                ", transNum='" + transNum + '\'' +
                ", replyNum='" + replyNum + '\'' +
                ", rewardsAmount='" + rewardsAmount + '\'' +
                ", h5url='" + h5url + '\'' +
                ", user=" + user +
                '}';
    }
}
