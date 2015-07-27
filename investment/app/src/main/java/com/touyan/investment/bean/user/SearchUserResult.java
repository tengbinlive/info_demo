package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/27.
 */
public class SearchUserResult extends OpenApiSimpleResult {
    private ArrayList<UserInfo> users;

    public ArrayList<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserInfo> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SearchUserResult{" +
                "users=" + users +
                '}';
    }
}
