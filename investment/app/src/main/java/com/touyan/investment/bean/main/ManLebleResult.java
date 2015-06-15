package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

public class ManLebleResult extends OpenApiSimpleResult {
    private String id;
    private String labelName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        return "MainLebleResult{" +
                "id='" + id + '\'' +
                ", labelName='" + labelName + '\'' +
                '}';
    }
}
