package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class MyActivityParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String userid;
    private String ckstau;
    private int startno;
    private int pageSize;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getStartno() {
        return startno;
    }

    public void setStartno(int startno) {
        this.startno = startno;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCkstau() {
        return ckstau;
    }

    public void setCkstau(String ckstau) {
        this.ckstau = ckstau;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.startno)) return false;
        if (StringUtil.isBlank(this.pageSize)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(startno))) param.put("startno", startno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pageSize))) param.put("pageSize", pageSize);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ckstau))) param.put("ckstau", ckstau);
    }

    @Override
    public String toString() {
        return "MyActivityParam{" +
                "userid='" + userid + '\'' +
                ", ckstau='" + ckstau + '\'' +
                ", startno=" + startno +
                ", pageSize=" + pageSize +
                '}';
    }
}
