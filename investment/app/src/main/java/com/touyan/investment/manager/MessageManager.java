package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.App;
import com.touyan.investment.bean.BeanParam;
import com.touyan.investment.bean.message.*;
import com.touyan.investment.bean.user.DeleteCollectedInfoParam;
import com.touyan.investment.bean.user.MayknowFriendParam;
import com.touyan.investment.bean.user.MayknowFriendResult;
import com.touyan.investment.bean.user.OtherInfoResult;

import java.util.ArrayList;

/**
 * 通信&消息相关业务类.
 *
 * @author HuangChao
 */
public class MessageManager {

    private static final String TAG = MessageManager.class.getSimpleName();


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

    /**
     * 获取热门群组
     *
     * @param context
     * @param handler
     * @param handlerMsgCode
     */
    public void queryHotgroups(Context context, final Handler handler, final int handlerMsgCode) {

        BeanParam param = new BeanParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_HOTGROUP);
        param.setParseTokenType(new TypeReference<QueryHotGroupsResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 创建群组群组
     *
     * @param context
     * @param handler
     * @param handlerMsgCode
     */
    public void createGroups(Context context,CreateGroupParam param, final Handler handler, final int handlerMsgCode) {

        // 接口参数
        param.setMethod(OpenApiMethodEnum.CREATE_GROUP);
        param.setParseTokenType(new TypeReference<QueryHotGroupsResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }


    /**
     * 获取通讯录好友
     *
     * @param context
     * @param cntact
     * @param handler
     * @param handlerMsgCode
     */
    public void queryContactFriends(Context context, ArrayList<String> cntact, final Handler handler, final int handlerMsgCode) {

        QueryContactFriendsParam param = new QueryContactFriendsParam();
        param.setServno(App.getInstance().getgUserInfo().getServno());
        param.setCntact(cntact);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_CONTACT_FRIENDS);
        param.setParseTokenType(new TypeReference<QueryContactFriendsResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 邀请通讯录好友
     *
     * @param context
     * @param handler
     * @param handlerMsgCode
     */
    public void inviteContactFriends(Context context,  String userid, final Handler handler, final int handlerMsgCode) {

        InviteContactFriendsParam param = new InviteContactFriendsParam();
        param.setServno(App.getInstance().getgUserInfo().getServno());
        param.setUserid(userid);
        param.setTemplateID("" + 12847);
        param.setBynativ("" + 1);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.INVITE_CONTACT_FRIENDS);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

}
