package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiBaseRequest;
import com.core.openapi.OpenApiRequestInterface;
import com.core.util.StringUtil;

import java.util.HashMap;

public class InvActListParam extends OpenApiBaseRequest implements OpenApiRequestInterface {

    private String actvtp;
    private int statno;
    private int pageno;
    private String servno;

    public String getActvtp() {
        return actvtp;
    }

    public void setActvtp(String actvtp) {
        this.actvtp = actvtp;
    }

    public String getServno() {
        return servno;
    }

    public void setServno(String servno) {
        this.servno = servno;
    }

    public int getStatno() {
        return statno;
    }

    public void setStatno(int statno) {
        this.statno = statno;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isBlank(this.statno)) return false;
        if (StringUtil.isBlank(this.pageno)) return false;
        if (StringUtil.isBlank(this.servno)) return false;
        return true;
    }

    @Override
    public void fill2Map(HashMap<String, Object> param, boolean includeEmptyAttr) {
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(actvtp))) param.put("actvtp", actvtp);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(servno))) param.put("servno", servno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(statno))) param.put("statno", statno);
        if (includeEmptyAttr || (!includeEmptyAttr && StringUtil.isNotBlank(pageno))) param.put("pageno", pageno);
    }

    @Override
    public String toString() {
        return "InvActListParam{" +
                "actvtp='" + actvtp + '\'' +
                ", statno=" + statno +
                ", pageno=" + pageno +
                ", servno='" + servno + '\'' +
                '}';
    }
}
