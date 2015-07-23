package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/21.
 */
public class BatchInfoParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private ArrayList<String> usernos;
    private ArrayList<String> groups;

    public ArrayList<String> getUsernos() {
        return usernos;
    }

    public void setUsernos(ArrayList<String> usernos) {
        this.usernos = usernos;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && usernos!=null)) param.put("usernos", usernos);
        if (includeEmptyAttr || (!includeEmptyAttr && groups!=null)) param.put("groups", groups);
    }

    @Override
    public String toString() {
        return "BatchInfoParam{" +
                "usernos=" + usernos +
                ", groups=" + groups +
                '}';
    }
}
