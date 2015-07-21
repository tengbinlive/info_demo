package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2015/7/15.
 */
public class QueryUserFansResult extends OpenApiSimpleResult {

    private ArrayList<Subscriber> subscribers;

    public ArrayList<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(ArrayList<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String toString() {
        return "QueryUserFansResult{" +
                "subscribers=" + subscribers +
                '}';
    }
}
