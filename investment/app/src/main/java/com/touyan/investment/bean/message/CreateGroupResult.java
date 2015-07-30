package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/28.
 */
public class CreateGroupResult extends OpenApiSimpleResult {

    private String groupid;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String toString() {
        return "CreateGroupResult{" +
                "groupid='" + groupid + '\'' +
                '}';
    }
}
