package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.touyan.investment.bean.login.LoginParam;
import com.touyan.investment.bean.login.LoginResult;

/**
 * 用户中心业务类.
 * 
 * @author HuangChao
 */
public class UserManager {

	private static final String TAG = UserManager.class.getSimpleName();

	/**
	 * 判断是否登录
	 *
	 * @return 已经登录返回true, 没有登录返回false
	 */
	public static boolean isLogin() {
		return true;
	}

	/**
	 * 修改用户信息
	 *
	 * @param context        上下文
	 * @param phone          手机号码
	 * @param pwd            密码
	 * @param uphoto         第三方头像
	 * @param thrdid         第三方ID:()
	 * @param lgtype         登录类型:(01-自有用户，02-腾讯，03-新浪微博)
	 * @param handler        在Activity中处理返回结果的Handler
	 * @param handlerMsgCode 返回结果的Handler的Msg代码
	 */
	public void modifyUserInfo(Context context, String phone, String pwd, String uphoto, String thrdid, String lgtype, final Handler handler, final int handlerMsgCode) {

		LoginParam param = new LoginParam();
		param.setServno(phone);
		param.setPasswd(pwd);
		param.setUphoto(uphoto);
		param.setThrdid(thrdid);
		param.setLgtype(lgtype);
		// 接口参数
		param.setMethod(OpenApiMethodEnum.LOAD_LOGIN);
		param.setParseTokenType(new TypeReference<LoginResult>() {
		});
		// 请求对象
		CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
		// 开始执行加载
		CommonDataLoader.getInstance(context).load(request);
	}
}
