package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvReplysParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String mesgid;
    private int startno;
    private int pageSize;

    public String getMesgid() {
        return mesgid;
    }

    public void setMesgid(String mesgid) {
        this.mesgid = mesgid;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartno() {
        return startno;
    }

    public void setStartno(int startno) {
        this.startno = startno;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.mesgid)) return false;
        if (StringUtil.isBlank(this.startno)) return false;
        if (StringUtil.isBlank(this.pageSize)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(mesgid))) param.put("mesgid", mesgid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(startno))) param.put("startno", startno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pageSize))) param.put("pageSize", pageSize);
    }

    @Override
    public String toString() {
        return "InvReplysParam{" +
                "mesgid='" + mesgid + '\'' +
                ", startno=" + startno +
                ", pageSize=" + pageSize +
                '}';
    }
}
