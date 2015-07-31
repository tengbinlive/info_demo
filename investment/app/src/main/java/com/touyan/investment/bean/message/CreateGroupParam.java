package com.touyan.investment.bean.message;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/29.
 */
public class CreateGroupParam extends OpenApiBaseRequest implements OpenApiRequestInterface {
    private String groupname;
    private String desc;
    private Boolean isPublic;//public
    private String maxusers;
    private Boolean approval;
    private String owner;
    private String visble;
    private Double payfor;
    private String istoll;
    private String canadd;
    private String validt;
    private String gphoto;

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.groupname)) return false;
        if (StringUtil.isBlank(this.owner)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(groupname))) param.put("groupname", groupname);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(desc))) param.put("desc", desc);
        if (isPublic!=null) param.put("public", isPublic);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(maxusers))) param.put("maxusers", maxusers);
        if (approval!=null) param.put("approval", approval);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(owner))) param.put("owner", owner);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(visble))) param.put("visble", visble);
        if (payfor!=null) param.put("payfor", payfor);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(istoll))) param.put("istoll", istoll);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(canadd))) param.put("canadd", canadd);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(validt))) param.put("validt", validt);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(gphoto))) param.put("gphoto", gphoto);
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(String maxusers) {
        this.maxusers = maxusers;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVisble() {
        return visble;
    }

    public void setVisble(String visble) {
        this.visble = visble;
    }

    public Double getPayfor() {
        return payfor;
    }

    public void setPayfor(Double payfor) {
        this.payfor = payfor;
    }

    public String getIstoll() {
        return istoll;
    }

    public void setIstoll(String istoll) {
        this.istoll = istoll;
    }

    public String getCanadd() {
        return canadd;
    }

    public void setCanadd(String canadd) {
        this.canadd = canadd;
    }

    public String getValidt() {
        return validt;
    }

    public void setValidt(String validt) {
        this.validt = validt;
    }

    public String getGphoto() {
        return gphoto;
    }

    public void setGphoto(String gphoto) {
        this.gphoto = gphoto;
    }

    @Override
    public String toString() {
        return "CreateGroupParam{" +
                "groupname='" + groupname + '\'' +
                ", desc='" + desc + '\'' +
                ", isPublic=" + isPublic +
                ", maxusers='" + maxusers + '\'' +
                ", approval=" + approval +
                ", owner='" + owner + '\'' +
                ", visble='" + visble + '\'' +
                ", payfor=" + payfor +
                ", istoll='" + istoll + '\'' +
                ", canadd='" + canadd + '\'' +
                ", validt='" + validt + '\'' +
                ", gphoto='" + gphoto + '\'' +
                '}';
    }
}
