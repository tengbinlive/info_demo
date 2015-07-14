package com.core.openapi;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * OpenApi参数辅助类.
 * 
 * @author bin.teng
 */
public class OpenApiParamHelper {

	/** OpenAPI参数名 */
	public static class Key {
		/** OpenAPI参数名: OpenAPI 数据 */
		public static final String OPEN_API_VERSION = "jsonData";
	}

	/**
	 * 调用前进行参数处理
	 * 
	 * @param paramObj
	 * @return 处理后参数
	 */
	public static HashMap<String, String> PrepareParam2API(OpenApiRequestInterface paramObj) {
		// 将参数对象转换为MAP
		HashMap<String, Object> param = paramObj.getParamMap();

		HashMap<String, Object> returnMap = new HashMap<String, Object>();

		// 遍历传入的参数, 去掉空值的参数
		for (Map.Entry<String, Object> item : param.entrySet()) {
			if (item.getValue() != null) {
				returnMap.put(item.getKey(), item.getValue());
			}
		}

		// 生成签名
		String sign = "";
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(Key.OPEN_API_VERSION, JSON.toJSONString(returnMap));
		return jsonMap;
	}
}
