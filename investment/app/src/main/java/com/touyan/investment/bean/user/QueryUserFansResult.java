package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

import java.util.Arrays;

/**
 * Created by Administrator on 2015/7/15.
 */
public class QueryUserFansResult extends OpenApiSimpleResult {

    private Subscriber[] subscribers;

    public Subscriber[] getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Subscriber[] subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String toString() {
        return "QueryUserFansResult{" +
                "subscribers=" + Arrays.toString(subscribers) +
                '}';
    }
}
