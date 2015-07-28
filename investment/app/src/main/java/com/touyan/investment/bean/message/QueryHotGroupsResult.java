package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/28.
 */
public class QueryHotGroupsResult extends OpenApiSimpleResult {

    private ArrayList<GroupDetail> groups;

    public ArrayList<GroupDetail> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<GroupDetail> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "QueryHotGroupsResult{" +
                "groups=" + groups +
                '}';
    }
}
