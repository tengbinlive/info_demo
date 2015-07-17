package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/17.
 */
public class CollectedParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String userid;
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
    }

    @Override
    public String toString() {
        return "CollectedParam{" +
                "userid='" + userid + '\'' +
                ", startno=" + startno +
                ", pageSize=" + pageSize +
                '}';
    }
}
