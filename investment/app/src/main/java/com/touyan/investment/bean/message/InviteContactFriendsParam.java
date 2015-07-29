package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/29.
 */
public class InviteContactFriendsParam extends OpenApiBaseRequest implements OpenApiRequestInterface {
    private String bynativ;
    private String userid;
    private String templateID;
    private String servno;


    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        if (StringUtil.isBlank(this.userid)) return false;
        if (StringUtil.isBlank(this.templateID)) return false;
        if (StringUtil.isBlank(this.bynativ)) return false;

        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(bynativ))) param.put("bynativ", bynativ);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(userid))) param.put("userid", userid);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(templateID)))
            param.put("templateID", templateID);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);

    }

    public String getBynativ() {
        return bynativ;
    }

    public void setBynativ(String bynativ) {
        this.bynativ = bynativ;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }


    @Override
    public String toString() {
        return "InviteContactFriendsParam{" +
                "bynativ='" + bynativ + '\'' +
                ", userid='" + userid + '\'' +
                ", templateID='" + templateID + '\'' +
                ", servno='" + servno + '\'' +
                '}';
    }
}
