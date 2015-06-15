package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class LoginParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String phone_no;
    private String pwd;

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
	public boolean validate() {
        if (StringUtil.isBlank(this.phone_no)) return false;
        if (StringUtil.isBlank(this.pwd)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(phone_no))) param.put("phone_no", phone_no);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pwd))) param.put("pwd", pwd);
	}

    @Override
    public String toString() {
        return "LoginParam{" +
                "phone_no='" + phone_no + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
