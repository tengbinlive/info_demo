package com.touyan.investment.bean.qiniu;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.Account;

public class QiniuUploadResult extends OpenApiSimpleResult {

    private String uptoken;

    public String getUptoken() {
        return uptoken;
    }

    public void setUptoken(String uptoken) {
        this.uptoken = uptoken;
    }

    @Override
    public String toString() {
        return "QiniuUploadResult{" +
                "uptoken='" + uptoken + '\'' +
                '}';
    }
}
