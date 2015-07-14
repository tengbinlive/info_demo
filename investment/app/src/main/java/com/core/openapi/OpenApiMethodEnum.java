package com.core.openapi;

/**
 * OpenAPI方法名称枚举类.
 * 
 * 说明:
 * 对以前的OpenAPI几个类进行解耦, 一般只需要在该类中添加和修改接口的方法名称即可.
 * 
 * @author bin.teng
 */
public enum OpenApiMethodEnum {

	/** 获取验证码 */
	LOAD_GET_CODE("index.php/get_cod", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 回复查询 */
	QUERY_REPLYS("/reply/queryReplys.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 资讯查询 */
	QUERY_INFOS("/info/queryInfos.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 登陆 */
	LOAD_LOGIN("/user/login.action", "openapi_json",OpenApi.URL_TYPE_DATA);


	private String code;
	private String format;

	private String type;

	private OpenApiMethodEnum(String code, String format,String type) {
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
