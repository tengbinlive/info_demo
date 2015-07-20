package com.touyan.investment.bean.main;

import com.touyan.investment.bean.user.UserInfo;

import java.io.Serializable;
import java.sql.Date;

public class MyPartakeInvActBean implements Serializable {
    //活动类型
    public final static String TYPE_ACT = "001";
    public final static String TYPE_ROADSHOW = "002";

    //我是否参加了该活动 四个状态：1未参加。002审核中，001未通过，000通过
    public final static String STATUS_AUDIT = "002";
    public final static String STATUS_NOT_PARTICIPATE = "1";
    public final static String STATUS_NO_BY = "001";
    public final static String STATUS_BY = "000";
    private String ckstau; //是否需要审核
    private String aptime;
    private String isorder;
    private String actvid;
    private InvActBean activity;

    public String getCkstau() {
        return ckstau;
    }

    public void setCkstau(String ckstau) {
        this.ckstau = ckstau;
    }

    public String getAptime() {
        return aptime;
    }

    public void setAptime(String aptime) {
        this.aptime = aptime;
    }

    public String getIsorder() {
        return isorder;
    }

    public void setIsorder(String isorder) {
        this.isorder = isorder;
    }

    public String getActvid() {
        return actvid;
    }

    public void setActvid(String actvid) {
        this.actvid = actvid;
    }

    public InvActBean getActivity() {
        return activity;
    }

    public void setActivity(InvActBean activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "MyPartakeInvActBean{" +
                "ckstau='" + ckstau + '\'' +
                ", aptime='" + aptime + '\'' +
                ", isorder='" + isorder + '\'' +
                ", actvid='" + actvid + '\'' +
                ", activity=" + activity +
                '}';
    }
}
