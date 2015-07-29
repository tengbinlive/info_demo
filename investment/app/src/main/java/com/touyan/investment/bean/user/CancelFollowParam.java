package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class CancelFollowParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String servno;

    private String scrino;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getScrino() {
        return scrino;
    }

    public void setScrino(String scrino) {
        this.scrino = scrino;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        if (StringUtil.isBlank(this.scrino)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(scrino))) param.put("scrino", scrino);

    }

    @Override
    public String toString() {
        return "CancelFollowParam{" +
                "servno='" + servno + '\'' +
                ", scrino='" + scrino + '\'' +
                '}';
    }
}