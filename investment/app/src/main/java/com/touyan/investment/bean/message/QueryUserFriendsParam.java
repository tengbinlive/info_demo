package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/23.
 */
public class QueryUserFriendsParam extends OpenApiBaseRequest implements OpenApiRequestInterface {
    private String servno;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);

    }

    @Override
    public String toString() {
        return "QueryUserFriendsParam{" +
                "servno='" + servno + '\'' +
                '}';
    }
}
