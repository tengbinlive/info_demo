package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/23.
 */
public class QueryUserFriendsResult extends OpenApiSimpleResult {

    private ArrayList<UserInfo> friends;

    public ArrayList<UserInfo> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<UserInfo> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "QueryUserFriendsResult{" +
                "friends=" + friends +
                '}';
    }
}
