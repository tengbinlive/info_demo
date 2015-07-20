package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class RegistParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String servno;
    private String aucode;
    private String passwd;
    private String ivcode;
    private String inrank;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getAucode() {
        return aucode;
    }

    public void setAucode(String aucode) {
        this.aucode = aucode;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getIvcode() {
        return ivcode;
    }

    public void setIvcode(String ivcode) {
        this.ivcode = ivcode;
    }

    public String getInrank() {
        return inrank;
    }

    public void setInrank(String inrank) {
        this.inrank = inrank;
    }

    @Override
	public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        if (StringUtil.isBlank(this.aucode)) return false;
        if (StringUtil.isBlank(this.passwd)) return false;
        if (StringUtil.isBlank(this.ivcode)) return false;
        if (StringUtil.isBlank(this.inrank)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(aucode))) param.put("aucode", aucode);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(passwd))) param.put("passwd", passwd);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(ivcode))) param.put("ivcode", ivcode);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(inrank))) param.put("inrank", inrank);
	}

    @Override
    public String toString() {
        return "RegistParam{" +
                "servno='" + servno + '\'' +
                ", aucode='" + aucode + '\'' +
                ", passwd='" + passwd + '\'' +
                ", ivcode='" + ivcode + '\'' +
                ", inrank='" + inrank + '\'' +
                '}';
    }
}
