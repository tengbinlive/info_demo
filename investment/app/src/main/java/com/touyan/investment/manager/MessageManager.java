package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.touyan.investment.bean.user.OtherInfoParam;
import com.touyan.investment.bean.user.OtherInfoResult;

/**
 * 通信&消息相关业务类.
 *
 * @author HuangChao
 */
public class MessageManager {

    private static final String TAG = MessageManager.class.getSimpleName();

    /**
     * 他人详情
     *
     * @param context        上下文
     * @param otherId        他人用户ID
     * @param userId         用户ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void topMessage(Context context, String otherId, String userId, final Handler handler, final int handlerMsgCode) {
        OtherInfoParam param = new OtherInfoParam();
        param.setServno(otherId);
        param.setUserid(userId);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.TOP_MESSAGE);
        param.setParseTokenType(new TypeReference<OtherInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

}
