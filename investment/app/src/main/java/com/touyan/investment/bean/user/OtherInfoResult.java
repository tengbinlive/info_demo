package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

/**
 * Created by Administrator on 2015/7/15.
 */
public class OtherInfoResult extends OpenApiSimpleResult {

    private int isfriends;
    private int submynum;
    private int isorder;
    private int mysubnum;
    private UserInfo info;

    public int getIsfriends() {
        return isfriends;
    }

    public void setIsfriends(int isfriends) {
        this.isfriends = isfriends;
    }

    public int getSubmynum() {
        return submynum;
    }

    public void setSubmynum(int submynum) {
        this.submynum = submynum;
    }

    public int getIsorder() {
        return isorder;
    }

    public void setIsorder(int isorder) {
        this.isorder = isorder;
    }

    public int getMysubnum() {
        return mysubnum;
    }

    public void setMysubnum(int mysubnum) {
        this.mysubnum = mysubnum;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "OtherInfoResult{" +
                "isfriends=" + isfriends +
                ", submynum=" + submynum +
                ", isorder=" + isorder +
                ", mysubnum=" + mysubnum +
                ", info=" + info +
                '}';
    }
}
