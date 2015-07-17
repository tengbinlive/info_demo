package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class MyOriInfoParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String ititle;
    private String userid;
    private int startno;
    private int pageSize;
    private String pubsid;


    public String getPubsid() {
        return pubsid;
    }

    public void setPubsid(String pubsid) {
        this.pubsid = pubsid;
    }

    public String getItitle() {
        return ititle;
    }

    public void setItitle(String ititle) {
        this.ititle = ititle;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
        //if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.startno)) return false;
        if (StringUtil.isBlank(this.pageSize)) return false;
        //if (StringUtil.isBlank(this.pubsid)) return false;
        return true;
	}

	@Override
	public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ititle))) param.put("ititle", ititle);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(startno))) param.put("startno", startno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pageSize))) param.put("pageSize", pageSize);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pageSize))) param.put("pubsid", pubsid);
    }
    @Override
    public String toString() {
        return "InvInfoParam{" +
                "ititle='" + ititle + '\'' +
                ", userid='" + userid + '\'' +
                ", startno=" + startno +
                ", pageSize=" + pageSize +
                ", pubsid='" + pubsid + '\'' +
                '}';
    }
}
