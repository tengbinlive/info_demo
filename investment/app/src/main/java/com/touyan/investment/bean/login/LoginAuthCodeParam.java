package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class LoginAuthCodeParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String phone_no;

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    @Override
	public boolean validate() {
        if (StringUtil.isBlank(this.phone_no)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(phone_no))) param.put("phone_no", phone_no);
	}

    @Override
    public String toString() {
        return "LoginAuthCodeParam{" +
                "phone_no='" + phone_no + '\'' +
                '}';
    }
}
