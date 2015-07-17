package com.touyan.investment.bean.qiniu;

import java.io.Serializable;

public class QiniuUploadBean implements Serializable {

    public final static String QINIU_URL = "http://7vihp3.com1.z0.glb.clouddn.com/";

    private String name; //名称

    private String path; //路径

    private boolean isUpload = false; //true 已上传成功

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    @Override
    public String toString() {
        return "QiniuUploadBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", isUpload=" + isUpload +
                '}';
    }
}
