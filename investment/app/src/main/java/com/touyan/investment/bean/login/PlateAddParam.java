package com.touyan.investment.bean.login;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class PlateAddParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String new_plate;
    private String is_master;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIs_master() {
        return is_master;
    }

    public void setIs_master(String is_master) {
        this.is_master = is_master;
    }

    public String getNew_plate() {
        return new_plate;
    }

    public void setNew_plate(String new_plate) {
        this.new_plate = new_plate;
    }

    @Override
	public boolean validate() {
        if (StringUtil.isBlank(this.new_plate)) return false;
        if (StringUtil.isBlank(this.is_master)) return false;
        if (StringUtil.isBlank(this.userid)) return false;
		return true;
	}

	@Override
	public void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(new_plate))) param.put("new_plate", new_plate);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(is_master))) param.put("is_master", is_master);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
	}

    @Override
    public String toString() {
        return "PlateAddParam{" +
                "new_plate='" + new_plate + '\'' +
                ", is_master='" + is_master + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
