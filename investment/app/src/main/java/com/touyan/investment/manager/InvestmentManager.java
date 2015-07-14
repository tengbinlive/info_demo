package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.App;
import com.touyan.investment.bean.main.InvInfoParam;
import com.touyan.investment.bean.main.InvInfoResult;
import com.touyan.investment.bean.main.InvReplysParam;
import com.touyan.investment.bean.main.InvReplysResult;

/**
 * 投研社业务类.
 *
 * @author bin.teng
 */
public class InvestmentManager {

    private static final String TAG = InvestmentManager.class.getSimpleName();

    /**
     * 获取info 列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryInfos(Context context, String ititle, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        InvInfoParam param = new InvInfoParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setItitle(ititle);
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_INFOS);
        param.setParseTokenType(new TypeReference<InvInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 获取回复 列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryReplys(Context context, String mesgid, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        InvReplysParam param = new InvReplysParam();
        param.setMesgid(mesgid);
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_REPLYS);
        param.setParseTokenType(new TypeReference<InvReplysResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }


    /**
     * 获取act 列表数据
     * @param context
     * @param page_number 当前页数
     * @param page_size  获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void LoginAct(Context context,String page_number,String page_size, final Handler handler, final int handlerMsgCode) {

        InvInfoParam param = new InvInfoParam();
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
