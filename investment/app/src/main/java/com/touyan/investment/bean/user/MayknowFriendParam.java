package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/20.
 */
public class MayknowFriendParam extends OpenApiBaseRequest implements OpenApiRequestInterface {
    private ArrayList<String> servnos;

    public ArrayList<String> getServnos() {
        return servnos;
    }

    public void setServnos(ArrayList<String> servnos) {
        this.servnos = servnos;
    }

    @Override

    public boolean validate() {
        if (servnos == null) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("servnos", servnos);
    }

    @Override
    public String toString() {
        return "MayknowFriendParam{" +
                "servnos=" + servnos +
                '}';
    }
}
