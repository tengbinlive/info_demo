package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/28.
 */
public class QueryContactFriendsResult extends OpenApiSimpleResult {

    private ArrayList<ContactFriend> relton;

    public ArrayList<ContactFriend> getRelton() {
        return relton;
    }

    public void setRelton(ArrayList<ContactFriend> relton) {
        this.relton = relton;
    }

    @Override
    public String toString() {
        return "QueryContactFriendsResult{" +
                "relton=" + relton +
                '}';
    }
}
