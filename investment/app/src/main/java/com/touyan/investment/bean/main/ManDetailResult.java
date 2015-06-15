package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;

public class ManDetailResult extends OpenApiSimpleResult {

    private String state;
    private int id;
    private String nickName;
    private int colorValue;//颜值
    private String unlockValue;

    private int vermicelli;//粉丝
    private int popularity;//人气
    private int labelNum;
    private int photoNum;
    private String signature;
    private double height;
    private double weight;
    private String birthday;
    private String constellation;
    private String measurements;
    private String bloodType;
    private String clearBackgrundImgName;//清晰图片访问名称
    private String selfIntroductionName;
    private int isnoAttent;//1标示已关注 ； -1标示未关注

    public String getState()
    {
        return state;
    }
    public void setState( String state )
    {
        this.state = state;
    }
    public int getId()
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    public String getNickName()
    {
        return nickName;
    }
    public void setNickName( String nickName )
    {
        this.nickName = nickName;
    }
    public int getColorValue()
    {
        return colorValue;
    }
    public void setColorValue( int colorValue )
    {
        this.colorValue = colorValue;
    }
    public String getUnlockValue()
    {
        return unlockValue;
    }
    public void setUnlockValue( String unlockValue )
    {
        this.unlockValue = unlockValue;
    }
    public int getVermicelli()
    {
        return vermicelli;
    }
    public void setVermicelli( int vermicelli )
    {
        this.vermicelli = vermicelli;
    }
    public int getPopularity()
    {
        return popularity;
    }
    public void setPopularity( int popularity )
    {
        this.popularity = popularity;
    }
    public int getLabelNum()
    {
        return labelNum;
    }
    public void setLabelNum( int labelNum )
    {
        this.labelNum = labelNum;
    }
    public int getPhotoNum()
    {
        return photoNum;
    }
    public void setPhotoNum( int photoNum )
    {
        this.photoNum = photoNum;
    }
    public String getSignature()
    {
        return signature;
    }
    public void setSignature( String signature )
    {
        this.signature = signature;
    }
    public double getHeight()
    {
        return height;
    }
    public void setHeight( double height )
    {
        this.height = height;
    }
    public double getWeight()
    {
        return weight;
    }
    public void setWeight( double weight )
    {
        this.weight = weight;
    }
    public String getBirthday()
    {
        return birthday;
    }
    public void setBirthday( String birthday )
    {
        this.birthday = birthday;
    }
    public String getConstellation()
    {
        return constellation;
    }
    public void setConstellation( String constellation )
    {
        this.constellation = constellation;
    }
    public String getMeasurements()
    {
        return measurements;
    }
    public void setMeasurements( String measurements )
    {
        this.measurements = measurements;
    }
    public String getBloodType()
    {
        return bloodType;
    }
    public void setBloodType( String bloodType )
    {
        this.bloodType = bloodType;
    }
    public String getClearBackgrundImgName()
    {
        return clearBackgrundImgName.replaceAll(" ","");
    }
    public void setClearBackgrundImgName( String clearBackgrundImgName )
    {
        this.clearBackgrundImgName = clearBackgrundImgName;
    }
    public String getSelfIntroductionName()
    {
        return selfIntroductionName;
    }
    public void setSelfIntroductionName( String selfIntroductionName )
    {
        this.selfIntroductionName = selfIntroductionName;
    }
    public int getIsnoAttent()
    {
        return isnoAttent;
    }
    public void setIsnoAttent( int isnoAttent )
    {
        this.isnoAttent = isnoAttent;
    }

    @Override
    public String toString() {
        return "ManDetailResult{" +
                "state='" + state + '\'' +
                ", id=" + id +
                ", nickName='" + nickName + '\'' +
                ", colorValue=" + colorValue +
                ", unlockValue='" + unlockValue + '\'' +
                ", vermicelli=" + vermicelli +
                ", popularity=" + popularity +
                ", labelNum=" + labelNum +
                ", photoNum=" + photoNum +
                ", signature='" + signature + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", birthday='" + birthday + '\'' +
                ", constellation='" + constellation + '\'' +
                ", measurements='" + measurements + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", clearBackgrundImgName='" + clearBackgrundImgName + '\'' +
                ", selfIntroductionName='" + selfIntroductionName + '\'' +
                ", isnoAttent=" + isnoAttent +
                '}';
    }
}
