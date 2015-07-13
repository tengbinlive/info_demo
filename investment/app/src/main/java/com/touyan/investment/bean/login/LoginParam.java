package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class LoginParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    public final static String OWN = "01";
    public final static String TENCENT = "02";
    public final static String SINA = "03";

    private String servno;
    private String passwd;
    private String uphoto;
    private String thrdid;
    private String lgtype;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public String getThrdid() {
        return thrdid;
    }

    public void setThrdid(String thrdid) {
        this.thrdid = thrdid;
    }

    public String getLgtype() {
        return lgtype;
    }

    public void setLgtype(String lgtype) {
        this.lgtype = lgtype;
    }

    @Override
	public boolean validate() {
        if (StringUtil.isBlank(this.lgtype)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(passwd))) param.put("passwd", passwd);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(uphoto))) param.put("uphoto", uphoto);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(thrdid))) param.put("thrdid", thrdid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(lgtype))) param.put("lgtype", lgtype);
	}

    @Override
    public String toString() {
        return "LoginParam{" +
                "servno='" + servno + '\'' +
                ", passwd='" + passwd + '\'' +
                ", uphoto='" + uphoto + '\'' +
                ", thrdid='" + thrdid + '\'' +
                ", lgtype='" + lgtype + '\'' +
                '}';
    }
}
