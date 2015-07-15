package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/14.
 */
public class ModifyUserInfoParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String servno;
    private String ualias;
    private String uphoto;
    private String locatn;
    private String inrank;
    private String compny;
    private String postin;
    private String teleph;
    private String rscope;
    private String uisvip;
    private String tagstr;

    private String[] instags;
    private String[] deltags;

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public String getUalias() {
        return ualias;
    }

    public void setUalias(String ualias) {
        this.ualias = ualias;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public String getLocatn() {
        return locatn;
    }

    public void setLocatn(String locatn) {
        this.locatn = locatn;
    }

    public String getInrank() {
        return inrank;
    }

    public void setInrank(String inrank) {
        this.inrank = inrank;
    }

    public String getCompny() {
        return compny;
    }

    public void setCompny(String compny) {
        this.compny = compny;
    }

    public String getPostin() {
        return postin;
    }

    public void setPostin(String postin) {
        this.postin = postin;
    }

    public String getTeleph() {
        return teleph;
    }

    public void setTeleph(String teleph) {
        this.teleph = teleph;
    }

    public String getRscope() {
        return rscope;
    }

    public void setRscope(String rscope) {
        this.rscope = rscope;
    }

    public String getUisvip() {
        return uisvip;
    }

    public void setUisvip(String uisvip) {
        this.uisvip = uisvip;
    }

    public String getTagstr() {
        return tagstr;
    }

    public void setTagstr(String tagstr) {
        this.tagstr = tagstr;
    }

    public String[] getInstags() {
        return instags;
    }

    public void setInstags(String[] instags) {
        this.instags = instags;
    }

    public String[] getDeltags() {
        return deltags;
    }

    public void setDeltags(String[] deltags) {
        this.deltags = deltags;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.servno)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("ualias", ualias);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("uphoto", uphoto);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("locatn", locatn);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("inrank", inrank);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("compny", compny);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("postin", postin);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("teleph", teleph);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("rscope", rscope);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("uisvip", uisvip);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("tagstr", tagstr);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("instags", instags);
        if (includeEmptyAttr || (!includeEmptyAttr)) param.put("deltags", deltags);
    }

    @Override
    public String toString() {
        return "ModifyUserInfoParam{" +
                "servno='" + servno + '\'' +
                ", ualias='" + ualias + '\'' +
                ", uphoto='" + uphoto + '\'' +
                ", locatn='" + locatn + '\'' +
                ", inrank='" + inrank + '\'' +
                ", compny='" + compny + '\'' +
                ", postin='" + postin + '\'' +
                ", teleph='" + teleph + '\'' +
                ", rscope='" + rscope + '\'' +
                ", uisvip='" + uisvip + '\'' +
                ", tagstr='" + tagstr + '\'' +
                ", instags=" + Arrays.toString(instags) +
                ", deltags=" + Arrays.toString(deltags) +
                '}';
    }
}
