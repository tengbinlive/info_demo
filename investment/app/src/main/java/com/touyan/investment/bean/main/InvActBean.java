package com.touyan.investment.bean.main;

import com.touyan.investment.bean.user.UserInfo;

import java.io.Serializable;
import java.sql.Date;

public class InvActBean implements Serializable {

    //活动类型
    public final static String TYPE_ACT = "001";
    public final static String TYPE_ROADSHOW = "002";

    //我是否参加了该活动 四个状态：1未参加。002审核中，001未通过，000通过
    public final static String STATUS_AUDIT = "002";
    public final static String STATUS_NOT_PARTICIPATE = "1";
    public final static String STATUS_NO_BY = "001";
    public final static String STATUS_BY = "000";

    private String ndchck; //是否需要审核
    private String acount;
    private Boolean issbsc;
    private String paytyp;
    private Integer aplyno;
    private String h5url;
    private String ckstau;
    private String atitle;
    private String origid;
    private String ispubl;
    private String byloct;
    private String tranmsg;
    private Boolean buyer;
    private Date pubstm;
    private String contnt;
    private Date startm;
    private Date endtim;
    private String istrans;
    private String adress;
    private Double charge;
    private String pubsid;
    private String msgsta;
    private Integer pintno;
    private String actvtp;
    private String buldid;
    private String actvid;
    private String isJoin;
    private String isStore;
    private int replyCount;
    private UserInfo user;

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public String getNdchck() {
        return ndchck;
    }

    public void setNdchck(String ndchck) {
        this.ndchck = ndchck;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public Boolean getIssbsc() {
        return issbsc;
    }

    public void setIssbsc(Boolean issbsc) {
        this.issbsc = issbsc;
    }

    public String getPaytyp() {
        return paytyp;
    }

    public void setPaytyp(String paytyp) {
        this.paytyp = paytyp;
    }

    public Integer getAplyno() {
        return aplyno;
    }

    public void setAplyno(Integer aplyno) {
        this.aplyno = aplyno;
    }

    public String getH5url() {
        return h5url;
    }

    public void setH5url(String h5url) {
        this.h5url = h5url;
    }

    public String getCkstau() {
        return ckstau;
    }

    public void setCkstau(String ckstau) {
        this.ckstau = ckstau;
    }

    public String getAtitle() {
        return atitle;
    }

    public void setAtitle(String atitle) {
        this.atitle = atitle;
    }

    public String getOrigid() {
        return origid;
    }

    public void setOrigid(String origid) {
        this.origid = origid;
    }

    public String getIspubl() {
        return ispubl;
    }

    public void setIspubl(String ispubl) {
        this.ispubl = ispubl;
    }

    public String getByloct() {
        return byloct;
    }

    public void setByloct(String byloct) {
        this.byloct = byloct;
    }

    public String getTranmsg() {
        return tranmsg;
    }

    public void setTranmsg(String tranmsg) {
        this.tranmsg = tranmsg;
    }

    public Boolean getBuyer() {
        return buyer;
    }

    public void setBuyer(Boolean buyer) {
        this.buyer = buyer;
    }

    public Date getPubstm() {
        return pubstm;
    }

    public void setPubstm(Date pubstm) {
        this.pubstm = pubstm;
    }

    public String getContnt() {
        return contnt;
    }

    public void setContnt(String contnt) {
        this.contnt = contnt;
    }

    public Date getStartm() {
        return startm;
    }

    public void setStartm(Date startm) {
        this.startm = startm;
    }

    public Date getEndtim() {
        return endtim;
    }

    public void setEndtim(Date endtim) {
        this.endtim = endtim;
    }

    public String getIstrans() {
        return istrans;
    }

    public void setIstrans(String istrans) {
        this.istrans = istrans;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public String getMsgsta() {
        return msgsta;
    }

    public void setMsgsta(String msgsta) {
        this.msgsta = msgsta;
    }

    public Integer getPintno() {
        return pintno;
    }

    public void setPintno(Integer pintno) {
        this.pintno = pintno;
    }

    public String getActvtp() {
        return actvtp;
    }

    public void setActvtp(String actvtp) {
        this.actvtp = actvtp;
    }

    public String getBuldid() {
        return buldid;
    }

    public void setBuldid(String buldid) {
        this.buldid = buldid;
    }

    public String getActvid() {
        return actvid;
    }

    public void setActvid(String actvid) {
        this.actvid = actvid;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvActBean{" +
                "ndchck='" + ndchck + '\'' +
                ", acount='" + acount + '\'' +
                ", issbsc=" + issbsc +
                ", paytyp='" + paytyp + '\'' +
                ", aplyno=" + aplyno +
                ", h5url='" + h5url + '\'' +
                ", ckstau='" + ckstau + '\'' +
                ", atitle='" + atitle + '\'' +
                ", origid='" + origid + '\'' +
                ", ispubl='" + ispubl + '\'' +
                ", byloct='" + byloct + '\'' +
                ", tranmsg='" + tranmsg + '\'' +
                ", buyer=" + buyer +
                ", pubstm=" + pubstm +
                ", contnt='" + contnt + '\'' +
                ", startm=" + startm +
                ", endtim=" + endtim +
                ", istrans='" + istrans + '\'' +
                ", adress='" + adress + '\'' +
                ", charge=" + charge +
                ", pubsid='" + pubsid + '\'' +
                ", msgsta='" + msgsta + '\'' +
                ", pintno=" + pintno +
                ", actvtp='" + actvtp + '\'' +
                ", buldid='" + buldid + '\'' +
                ", actvid='" + actvid + '\'' +
                ", isJoin='" + isJoin + '\'' +
                ", isStore='" + isStore + '\'' +
                ", replyCount='" + replyCount + '\'' +
                ", user=" + user +
                '}';
    }
}
