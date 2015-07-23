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
import com.touyan.investment.App;
import com.touyan.investment.bean.BeanParam;
import com.touyan.investment.bean.message.TopMessageListParam;
import com.touyan.investment.bean.message.TopMessageListResult;
import com.touyan.investment.bean.user.OtherInfoResult;

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

    /**
     * 置顶消息详情查询
     *
     * @param context        上下文
     * @param groupid        群ID
     * @param mesgid         消息ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */

    public void topMessageDetail(Context context, String groupid, String mesgid, final Handler handler, final int handlerMsgCode) {
        TopMessageListParam param = new TopMessageListParam();
        param.setMesgid(mesgid);
        param.setGroupid(groupid);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.TOP_MESSAGE_DEATAIL);
        param.setParseTokenType(new TypeReference<OtherInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 用户所有群置顶消息查询
     *
     * @param context        上下文
     * @param handler        在Activity中处理返回结果的Handlers
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void topMessageList(Context context, final Handler handler, final int handlerMsgCode) {
        BeanParam param = new BeanParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());

        // 接口参数
        param.setMethod(OpenApiMethodEnum.TOP_MESSAGE_LIST);
        param.setParseTokenType(new TypeReference<TopMessageListResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

}
