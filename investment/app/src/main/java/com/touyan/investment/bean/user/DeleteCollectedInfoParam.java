package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/20.
 */
public class DeleteCollectedInfoParam extends OpenApiBaseRequest implements OpenApiRequestInterface {
    private ArrayList<String> ids;

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    @Override
    public boolean validate() {
        if (ids == null) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("ids", ids);
    }

    @Override
    public String toString() {
        return "DeleteCollectedInfoParam{" +
                "ids=" + ids +
                '}';
    }
}
