package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.UserInfo;

import java.util.ArrayList;

public class InvActJoinResult extends OpenApiSimpleResult {

    private ArrayList<InvActJoinUsersBean> joinUsers;

    private int jionNum;

    public ArrayList<InvActJoinUsersBean> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(ArrayList<InvActJoinUsersBean> joinUsers) {
        this.joinUsers = joinUsers;
    }

    public int getJionNum() {
        return jionNum;
    }

    public void setJionNum(int jionNum) {
        this.jionNum = jionNum;
    }

    @Override
    public String toString() {
        return "InvActJoinResult{" +
                "joinUsers=" + joinUsers +
                ", jionNum='" + jionNum + '\'' +
                '}';
    }
}
