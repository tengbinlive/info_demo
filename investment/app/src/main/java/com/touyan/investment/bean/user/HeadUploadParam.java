package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class HeadUploadParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String servno;

    private String uphoto;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        if (StringUtil.isBlank(this.uphoto)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(uphoto))) param.put("uphoto", uphoto);

    }

    @Override
    public String toString() {
        return "HeadUploadParam{" +
                "servno='" + servno + '\'' +
                ", uphoto='" + uphoto + '\'' +
                '}';
    }
}
