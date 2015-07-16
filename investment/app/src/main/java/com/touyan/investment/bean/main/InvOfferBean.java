package com.touyan.investment.bean.main;

import com.touyan.investment.bean.user.UserInfo;

import java.io.Serializable;
import java.sql.Date;

public class InvOfferBean implements Serializable {

    private Date pubstm;
    private int replyCount;
    private String contnt;
    private String isorder;
    private int adoptcount;
    private Date endtim;
    private String h5url;
    private String istrans;
    private Double amount;
    private String rtitle;
    private String origid;
    private String isshow;
    private String pubsid;
    private String tranmsg;
    private String rewdid;
    private String buldid;

    private UserInfo user;

    public Date getPubstm() {
        return pubstm;
    }

    public void setPubstm(Date pubstm) {
        this.pubstm = pubstm;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public String getIsorder() {
        return isorder;
    }

    public void setIsorder(String isorder) {
        this.isorder = isorder;
    }

    public int getAdoptcount() {
        return adoptcount;
    }

    public void setAdoptcount(int adoptcount) {
        this.adoptcount = adoptcount;
    }

    public Date getEndtim() {
        return endtim;
    }

    public void setEndtim(Date endtim) {
        this.endtim = endtim;
    }

    public String getH5url() {
        return h5url;
    }

    public void setH5url(String h5url) {
        this.h5url = h5url;
    }

    public String getIstrans() {
        return istrans;
    }

    public void setIstrans(String istrans) {
        this.istrans = istrans;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRtitle() {
        return rtitle;
    }

    public void setRtitle(String rtitle) {
        this.rtitle = rtitle;
    }

    public String getOrigid() {
        return origid;
    }

    public void setOrigid(String origid) {
        this.origid = origid;
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

    public String getTranmsg() {
        return tranmsg;
    }

    public void setTranmsg(String tranmsg) {
        this.tranmsg = tranmsg;
    }

    public String getRewdid() {
        return rewdid;
    }

    public void setRewdid(String rewdid) {
        this.rewdid = rewdid;
    }

    public String getBuldid() {
        return buldid;
    }

    public void setBuldid(String buldid) {
        this.buldid = buldid;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvOfferBean{" +
                "pubstm=" + pubstm +
                ", replyCount=" + replyCount +
                ", contnt='" + contnt + '\'' +
                ", isorder='" + isorder + '\'' +
                ", adoptcount=" + adoptcount +
                ", endtim=" + endtim +
                ", h5url='" + h5url + '\'' +
                ", istrans='" + istrans + '\'' +
                ", amount='" + amount + '\'' +
                ", rtitle='" + rtitle + '\'' +
                ", origid='" + origid + '\'' +
                ", isshow='" + isshow + '\'' +
                ", pubsid='" + pubsid + '\'' +
                ", tranmsg='" + tranmsg + '\'' +
                ", rewdid='" + rewdid + '\'' +
                ", buldid='" + buldid + '\'' +
                ", user=" + user +
                '}';
    }
}
