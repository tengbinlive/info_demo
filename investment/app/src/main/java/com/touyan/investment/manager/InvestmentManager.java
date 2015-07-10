package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.login.LoginAuthCodeParam;
import com.touyan.investment.bean.login.LoginParam;
import com.touyan.investment.bean.login.LoginResult;
import com.touyan.investment.bean.main.MainInvActParam;

/**
 * 投研社业务类.
 *
 * @author bin.teng
 */
public class InvestmentManager {

    private static final String TAG = InvestmentManager.class.getSimpleName();

    /**
     * 获取act 列表数据
     * @param context
     * @param page_number 当前页数
     * @param page_size  获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void LoginAct(Context context,String page_number,String page_size, final Handler handler, final int handlerMsgCode) {

        MainInvActParam param = new MainInvActParam();
        param.setUserId("");
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_GET_CODE);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }



}
