package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

public class ManPhotosResult extends OpenApiSimpleResult {

    private int id;
    private String clearPhotoName;
    private String fuzzyPhotoName;
    private int status;
    private int unlockMethods;
    private int cmtNum;
    private double score;
    private int unlockValue;
    private int isnoScore; //1 是，0 否

    public boolean getIsnoScore() {
        return isnoScore == 1;
    }

    public void setIsnoScore(int isnoScore) {
        this.isnoScore = isnoScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClearPhotoName() {
        return clearPhotoName == null ? "" : clearPhotoName.replaceAll(" ", "");
    }

    public void setClearPhotoName(String clearPhotoName) {
        this.clearPhotoName = clearPhotoName;
    }

    public String getFuzzyPhotoName() {
        return fuzzyPhotoName == null ? "" : fuzzyPhotoName.replaceAll(" ", "");
    }

    public void setFuzzyPhotoName(String fuzzyPhotoName) {
        this.fuzzyPhotoName = fuzzyPhotoName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUnlockMethods() {
        return unlockMethods;
    }

    public void setUnlockMethods(int unlockMethods) {
        this.unlockMethods = unlockMethods;
    }

    public int getCmtNum() {
        return cmtNum;
    }

    public void setCmtNum(int cmtNum) {
        this.cmtNum = cmtNum;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getUnlockValue() {
        return unlockValue;
    }

    public void setUnlockValue(int unlockValue) {
        this.unlockValue = unlockValue;
    }

    @Override
    public String toString() {
        return "ManPhotosResult{" +
                "id=" + id +
                ", clearPhotoName='" + clearPhotoName + '\'' +
                ", fuzzyPhotoName='" + fuzzyPhotoName + '\'' +
                ", status=" + status +
                ", unlockMethods=" + unlockMethods +
                ", cmtNum=" + cmtNum +
                ", score=" + score +
                ", unlockValue=" + unlockValue +
                ", isnoScore=" + isnoScore +
                '}';
    }
}
