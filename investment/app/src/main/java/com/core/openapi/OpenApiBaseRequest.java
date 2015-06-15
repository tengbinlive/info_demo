package com.core.openapi;


import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;

/**
 * OpenApi基本请求类.
 * 
 * @author bin.teng
 */
public abstract class OpenApiBaseRequest implements OpenApiRequestInterface {

	/** OpenAPI接口方法枚举 */
	public OpenApiMethodEnum method;

	/** OpenAPI接口返回数据解析目标TypeToken */
	public TypeReference<?> parseTokenType;

	public OpenApiMethodEnum getMethod() {
		return method;
	}

	public void setMethod(OpenApiMethodEnum method) {
		this.method = method;
	}

	public TypeReference<?> getParseTypeToken() {
		return parseTokenType;
	}

	public void setParseTokenType(TypeReference<?> typeToken) {
		this.parseTokenType = typeToken;
	}

	/**
	 * 需要重写的把属性填充到Map中
	 * 
	 * @param param 属性HashMap对象
	 * @param includeEmptyAttr 包含空值的属性
	 */
	public abstract void fill2Map(HashMap<String, String> param, boolean includeEmptyAttr);

	/**
	 * 获得属性HashMap对象, 不包含空值的属性
	 * 
	 * @return 属性HashMap对象
	 */
	public HashMap<String, String> getParamMap() {
		return getParamMap(null);
	}

	/**
	 * 获得属性HashMap对象, 不包含空值的属性
	 * 
	 * @param param 属性HashMap对象
	 * @return 属性HashMap对象
	 */
	public HashMap<String, String> getParamMap(HashMap<String, String> param) {
		if (param == null) param = new HashMap<String, String>();
		fill2Map(param, false);
		return param;
	}

	@Override
	public String toString() {
		HashMap<String, String> map = getParamMap();
		map.put("method", method.toString());
		map.put("parseTokenType", parseTokenType.toString());
		return map.toString();
	}
}
