package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.message.GroupDetal;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/15.
 */
public class QueryUserGroupsResult extends OpenApiSimpleResult {

    private ArrayList<GroupDetal> myJoinedGroups;
    private ArrayList<GroupDetal> myCreateGroups;

    public ArrayList<GroupDetal> getMyJoinedGroups() {
        return myJoinedGroups;
    }

    public void setMyJoinedGroups(ArrayList<GroupDetal> myJoinedGroups) {
        this.myJoinedGroups = myJoinedGroups;
    }

    public ArrayList<GroupDetal> getMyCreateGroups() {
        return myCreateGroups;
    }

    public void setMyCreateGroups(ArrayList<GroupDetal> myCreateGroups) {
        this.myCreateGroups = myCreateGroups;
    }

    @Override
    public String toString() {
        return "QueryUserGroupsResult{" +
                "myJoinedGroups=" + myJoinedGroups +
                ", myCreateGroups=" + myCreateGroups +
                '}';
    }
}
