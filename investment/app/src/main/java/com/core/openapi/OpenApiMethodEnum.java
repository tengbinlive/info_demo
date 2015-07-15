package com.core.openapi;

/**
 * OpenAPI方法名称枚举类.
 * <p/>
 * 说明:
 * 对以前的OpenAPI几个类进行解耦, 一般只需要在该类中添加和修改接口的方法名称即可.
 *
 * @author bin.teng
 */
public enum OpenApiMethodEnum {

    /**
     * 广场活动列表
     */
    LOAD_ACT_LIST("/active/list.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 获取验证码
     */
    LOAD_GET_CODE("index.php/get_cod", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 收藏
     */
    LOAD_STORE_MSG("/user/storeMsg.action", "openapi_json", OpenApi.URL_TYPE_DATA),

   /**
     * 打赏
     */
    LOAD_BUY_INFO("/info/buyInfo.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 活动报名
     */
    LOAD_ACT_SIGN("/active/apply.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 资讯查询
     */
    QUERY_INFOS("/info/queryInfos.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 修改用户信息
     */
    MODIFY_USER_INFO("/user/update.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 评论资讯/回复悬赏
     */
    REPLY_DISCUSS("/reply/discuss.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 回复查询
     */
    QUERY_REPLYS("/reply/queryReplys.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 资讯详情
     */
    QUERY_INFO_DETAIL("/info/queryById.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 活动详情
     */
    QUERY_ACT_DETAIL("/active/detail.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 活动 参与人员信息
     */
    QUERY_ACT_JOIN_USER("/active/jionUserList.action", "openapi_json", OpenApi.URL_TYPE_DATA),

    /**
     * 登陆
     */
    LOAD_LOGIN("/user/login.action", "openapi_json", OpenApi.URL_TYPE_DATA);


    private String code;
    private String format;

    private String type;

    private OpenApiMethodEnum(String code, String format, String type) {
        this.code = code;
        this.format = format;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 根据方法代码获得对应枚举对象.
     *
     * @param code 方法代码,例如:
     * @return 对应枚举对象
     */
    public static OpenApiMethodEnum getEnumByCode(String code) {
        for (OpenApiMethodEnum item : values()) {
            if (item.code.equals(code)) return item;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(super.toString());
        buf.append(",code=").append(this.getCode());
        buf.append(",format=").append(this.getFormat());
        return buf.toString();
    }
}
