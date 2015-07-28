package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/28.
 */
public class QueryContactFriendsParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String servno;
    private ArrayList<String> cntact;

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;

        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("cntact", cntact);
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public ArrayList<String> getCntact() {
        return cntact;
    }

    public void setCntact(ArrayList<String> cntact) {
        this.cntact = cntact;
    }

    @Override
    public String toString() {
        return "QueryContactFriendsParam{" +
                "servno='" + servno + '\'' +
                ", cntact=" + cntact +
                '}';
    }
}
