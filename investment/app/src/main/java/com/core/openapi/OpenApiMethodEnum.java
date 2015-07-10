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

	/** 登陆 */
	LOAD_LOGIN("index.php/Login", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 添加车牌 */
	LOAD_ADDPLATE("index.php/User/addPlate", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 添加照片评分或评论 */
	LOAD_PHOTOS_SCORE_CMT("appMan/addPhotosCmt.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取照片评论列表 */
	LOAD_PHOTOS_CMT_LIST("appMan/manPhotosCmtList.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取照片详情 */
	LOAD_PHOTOS_DETAIL("appMan/manPhotosCmtDetail.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取签到状态 */
	LOAD_IS_SIGN_IN("appUser/findSignIn.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 签到 */
	LOAD_ADD_SIGN_IN("appUser/addSignIn.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取 recommend man list */
	LOAD_RECOMMENDMANLIST("appMan/recommendManList.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取 puzzle game list */
	LOAD_GAMELIST("appMan/recommendManPhotosList.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取game url */
	LOAD_GAMEURL("appCommon/jigsawUrl.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取man详情 */
	LOAD_MANDETAIL("appMan/manDetail.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 点赞 */
	LOAD_ADDPRAISE("appUserAndMan/pointPraise.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取F币 */
	LOAD_FCOIN("appUser/queryUserFCoin.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取man 照片列表 */
	LOAD_MANLIST_PHOTOS("appMan/manPhotosList.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 根据标签获取 man list */
	LOAD_MANLIST_LABEL("appMan/manListByLabel.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 根据条件获取 man list */
	LOAD_MANLIST_TYPE("appMan/manList.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 解锁man photos */
	LOAD_UNLOCKMAN_PHOTOS("appMan/unlockManPhotos.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 解锁man */
	LOAD_UNLOCKMAN("appMan/unlockMan.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 获取主页列表标签 */
	LOAD_LEBLE("appCommon/sysLebelList.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 登录 */
	LOGIN("appUser/regUser.action", "openapi_json",OpenApi.URL_TYPE_DATA),

	/** 评论详情 */
	PHOTOSCMTDETAIL("appMan/manPhotosCmtDetail.action", "openapi_json",OpenApi.URL_TYPE_IMAGE);

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
