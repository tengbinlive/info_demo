package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

public class ManPaiHangResult extends OpenApiSimpleResult {

    private int id;
    private String nickName;
    private String clearBackgrundImgName;//清晰图片访问名称
    private String fuzzyBackgrundImgName;//模糊图片访问名称
    private int popularity;//人气
    private int colorValue;//颜值
    private int unlockMethods;//等于-1表示未解锁，其他解锁
    private int unlockValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getClearBackgrundImgName() {
        return clearBackgrundImgName == null ? "" : clearBackgrundImgName.replaceAll(" ", "");
    }

    public void setClearBackgrundImgName(String clearBackgrundImgName) {
        this.clearBackgrundImgName = clearBackgrundImgName;
    }

    public String getFuzzyBackgrundImgName() {
        return fuzzyBackgrundImgName == null ? "" : fuzzyBackgrundImgName.replaceAll(" ", "");
    }

    public void setFuzzyBackgrundImgName(String fuzzyBackgrundImgName) {
        this.fuzzyBackgrundImgName = fuzzyBackgrundImgName;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }

    public int getUnlockMethods() {
        return unlockMethods;
    }

    public void setUnlockMethods(int unlockMethods) {
        this.unlockMethods = unlockMethods;
    }

    public int getUnlockValue() {
        return unlockValue;
    }

    public void setUnlockValue(int unlockValue) {
        this.unlockValue = unlockValue;
    }

    @Override
    public String toString() {
        return "ManPaiHangResult{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", clearBackgrundImgName='" + clearBackgrundImgName + '\'' +
                ", fuzzyBackgrundImgName='" + fuzzyBackgrundImgName + '\'' +
                ", popularity=" + popularity +
                ", colorValue=" + colorValue +
                ", unlockMethods=" + unlockMethods +
                ", unlockValue=" + unlockValue +
                '}';
    }
}
