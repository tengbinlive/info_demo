package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.touyan.investment.bean.message.QueryUserFriendsParam;
import com.touyan.investment.bean.message.QueryUserFriendsResult;
import com.touyan.investment.bean.user.QueryUserFansParam;
import com.touyan.investment.bean.user.QueryUserFansResult;

/**
 * 通信&消息相关业务类.
 *
 * @author HuangChao
 */
public class MessageManager {

    private static final String TAG = MessageManager.class.getSimpleName();

    /**
     * 我的关注
     *
     * @param context        上下文
     * @param userId         用户ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void queryUserFriends(Context context, String userId, final Handler handler, final int handlerMsgCode) {
        QueryUserFriendsParam param = new QueryUserFriendsParam();
        param.setServno(userId);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_USER_FRIENDS);
        param.setParseTokenType(new TypeReference<QueryUserFriendsResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

}
