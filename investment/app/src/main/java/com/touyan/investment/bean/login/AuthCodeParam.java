package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class AuthCodeParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String servno;

    private String templateID;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    @Override
	public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        if (StringUtil.isBlank(this.templateID)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(templateID))) param.put("templateID", templateID);
	}

    @Override
    public String toString() {
        return "LoginAuthCodeParam{" +
                "servno='" + servno + '\'' +
                ", templateID='" + templateID + '\'' +
                '}';
    }
}
