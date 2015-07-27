package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.message.GroupDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public class BatchInfoResult extends OpenApiSimpleResult {

    private ArrayList<UserInfo> userinfo;
    private ArrayList<GroupDetail> groupinfo;

    public ArrayList<UserInfo> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ArrayList<UserInfo> userinfo) {
        this.userinfo = userinfo;
    }

    public ArrayList<GroupDetail> getGroupinfo() {
        return groupinfo;
    }

    public void setGroupinfo(ArrayList<GroupDetail> groupinfo) {
        this.groupinfo = groupinfo;
    }

    @Override
    public String toString() {
        return "BatchInfoResult{" +
                "userinfo=" + userinfo +
                ", groupinfo=" + groupinfo +
                '}';
    }
}
