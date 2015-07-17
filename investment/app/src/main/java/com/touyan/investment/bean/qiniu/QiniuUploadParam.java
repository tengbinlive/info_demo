package com.touyan.investment.bean.qiniu;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class QiniuUploadParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String bucket;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.bucket)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(bucket))) param.put("bucket", bucket);
    }

    @Override
    public String toString() {
        return "QiniuUploadParam{" +
                "bucket='" + bucket + '\'' +
                '}';
    }
}
