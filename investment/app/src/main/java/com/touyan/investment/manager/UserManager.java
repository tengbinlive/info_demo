package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.CommonResponse;
import com.core.openapi.OpenApiMethodEnum;
import com.core.openapi.OpenApiSimpleResult;
import com.core.util.CommonUtil;
import com.core.util.Log;
import com.dao.*;
import com.touyan.investment.App;
import com.touyan.investment.bean.main.InvInfoResult;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.user.*;
import com.touyan.investment.helper.BeanCopyHelper;
import de.greenrobot.dao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

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
     * 上传头像
     *
     * @param context        上下文
     * @param uphoto         手机号(用户ID)
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void uploadHead(Context context, String uphoto, final Handler handler, final int handlerMsgCode) {
        HeadUploadParam param = new HeadUploadParam();
        param.setServno(App.getInstance().getgUserInfo().getServno());
        param.setUphoto(uphoto);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.UPLOAD_HEAD);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 修改用户信息
     *
     * @param context        上下文
     * @param phone          手机号码
     * @param ualias         昵称
     * @param uphoto         照片
     * @param locatn         地区
     * @param inrank         平台身份
     * @param compny         公司名
     * @param postin         职位
     * @param teleph         联系方式
     * @param rscope         02
     * @param uisvip         0
     * @param tagstr         金融、保险
     * @param instags        [{"tgname":"程序猿"}]
     * @param deltags        ["12123111"]
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void modifyUserInfo(Context context, String phone, String ualias, String uphoto, String locatn, String inrank, String compny, String postin, String teleph, String rscope, String uisvip, String tagstr, String[] instags, String[] deltags, final Handler handler, final int handlerMsgCode) {
        ModifyUserInfoParam param = new ModifyUserInfoParam();
        param.setServno(phone);
        param.setUalias(ualias);
        param.setUphoto(uphoto);
        param.setLocatn(locatn);
        param.setInrank(inrank);
        param.setCompny(compny);
        param.setPostin(postin);
        param.setTeleph(teleph);
        param.setRscope(rscope);
        param.setUisvip(uisvip);
        param.setTagstr(tagstr);
        param.setInstags(instags);
        param.setDeltags(deltags);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.MODIFY_USER_INFO);
        param.setParseTokenType(new TypeReference<ModifyUserInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 用户粉丝
     *
     * @param context        上下文
     * @param userId         用户ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void queryUserFansInfo(Context context, String userId, final Handler handler, final int handlerMsgCode) {
        QueryUserFansParam param = new QueryUserFansParam();
        param.setUserid(userId);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_USERFANS);
        param.setParseTokenType(new TypeReference<QueryUserFansResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 我的关注
     *
     * @param context        上下文
     * @param userId         用户ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void queryUserFollow(Context context, String userId, final Handler handler, final int handlerMsgCode) {
        QueryUserFansParam param = new QueryUserFansParam();
        param.setUserid(userId);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_FOLLOW);
        param.setParseTokenType(new TypeReference<QueryUserFansResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 我的关注
     *
     * @param context        上下文
     * @param scrino         被关注人id
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void cancelUserFollow(Context context, String scrino, final Handler handler, final int handlerMsgCode) {
        CancelFollowParam param = new CancelFollowParam();
        param.setServno(App.getInstance().getgUserInfo().getServno());
        param.setScrino(scrino);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.CANCEL_FOLLOW);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 他人详情
     *
     * @param context        上下文
     * @param otherId        他人用户ID
     * @param userId         用户ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void queryOtherInfo(Context context, String otherId, String userId, final Handler handler, final int handlerMsgCode) {
        OtherInfoParam param = new OtherInfoParam();
        param.setServno(otherId);
        param.setUserid(userId);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_OTHERINFO);
        param.setParseTokenType(new TypeReference<OtherInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 获取用户收藏资讯列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryCollectedInfos(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        CollectedParam param = new CollectedParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_COLLECTED_INFOS);
        param.setParseTokenType(new TypeReference<InvInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 获取用户收藏活动列表
     *
     * @param context
     * @param page_number    分页 当前页
     * @param page_size      数据个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryCollectedActs(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        CollectedParam param = new CollectedParam();

        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_COLLECTED_ACTS);
        param.setParseTokenType(new TypeReference<CollectedActResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 获取用户收藏悬赏列表
     *
     * @param context
     * @param page_number    分页 当前页
     * @param page_size      数据个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryCollectedOffers(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        CollectedParam param = new CollectedParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_COLLECTED_REWARDS);
        param.setParseTokenType(new TypeReference<CollectedOfferResult>() {
        });

        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 获取用户收藏悬赏列表
     *
     * @param context
     * @param ids
     * @param handler
     * @param handlerMsgCode
     */
    public void deleteCollectedInfos(Context context, ArrayList<String> ids, final Handler handler, final int handlerMsgCode) {

        DeleteCollectedInfoParam param = new DeleteCollectedInfoParam();
        param.setIds(ids);


        // 接口参数
        param.setMethod(OpenApiMethodEnum.DELETE_COLLECTED_INFOS);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });

        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 关注他人
     *
     * @param context        上下文
     * @param otherId        他人用户ID
     * @param userId         用户ID
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void followOtherInfo(Context context, String otherId, String userId, final Handler handler, final int handlerMsgCode) {
        FollowOtherParam param = new FollowOtherParam();
        param.setScrino(otherId);
        param.setUserid(userId);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.FOLLOW_OTHERINFO);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 批量获取用户信息群信息
     *
     * @param context        上下文
     * @param userids        用户ID
     * @param groupids       群组id
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public BatchInfoResult batchInfo(Context context, ArrayList<String> userids, ArrayList<String> groupids, final Handler handler, final int handlerMsgCode) {
        // ------------开始 数据库缓存处理部分-----------//

        ArrayList<String> useridsTemp = new ArrayList<>(userids);
        ArrayList<String> groupidsTemp = new ArrayList<>(groupids);

        // 从数据库中查询
        UserInfoDao userInfoDao = App.getDaoSession().getUserInfoDao();
        GroupDetalDao groupDetalDao = App.getDaoSession().getGroupDetalDao();

        WhereCondition wc = UserInfoDao.Properties.Servno.in(userids);
        WhereCondition wc1 = GroupDetalDao.Properties.Groupid.in(groupids);

        List<UserInfoDO> usernos = userInfoDao.queryBuilder().where(wc).list();
        List<GroupDetalDO> groups = groupDetalDao.queryBuilder().where(wc1).list();

        BatchInfoResult result = new BatchInfoResult();
        if (usernos != null && usernos.size() > 0) {
            ArrayList<UserInfo> users = new ArrayList<>();
            // 数据转换
            for (UserInfoDO item : usernos) {
                UserInfo user = BeanCopyHelper.cast2UserInfo(item);
                String servno = user.getServno();
                useridsTemp.remove(servno);
                users.add(user);
            }
            result.setUserinfo(users);
        }

        if (groups != null && groups.size() > 0) {
            ArrayList<GroupDetail> groupDetails = new ArrayList<>();
            // 数据转换
            for (GroupDetalDO item : groups) {
                GroupDetail gd = BeanCopyHelper.cast2GroupDetal(item);
                String id = gd.getGroupid();
                groupidsTemp.remove(id);
                groupDetails.add(gd);
            }
            result.setGroupinfo(groupDetails);
        }

        if (useridsTemp.size() <= 0 && groupidsTemp.size() <= 0) {
            return result;
        }

        // 自定义处理Listener
        Response.Listener<CommonResponse> listener = new Response.Listener<CommonResponse>() {
            @Override
            public void onResponse(CommonResponse response) {
                CommonUtil.delivery2Handler(handler, handlerMsgCode, response);
                // 将结果存入数据库
                updateBatchInfoResult(response);
            }
        };

        BatchInfoParam param = new BatchInfoParam();
        param.setUsernos(useridsTemp);
        param.setGroups(groupidsTemp);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.BATCH_INFO);
        param.setParseTokenType(new TypeReference<BatchInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 自定义处理Listener
        request.setListener(listener);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
        return null;
    }

    private void updateBatchInfoResult(CommonResponse response) {
        final DaoSession daoSession = App.getDaoSession();
        // 如果查询结果正确才保存
        if (response != null && response.isSuccess()) {
            BatchInfoResult result = (BatchInfoResult) response.getData();
            ArrayList<GroupDetail> groupinfo = result.getGroupinfo();
            ArrayList<UserInfo> userinfo = result.getUserinfo();
            final List<GroupDetalDO> groupDOs = new ArrayList<>();
            final List<UserInfoDO> usernoDOs = new ArrayList<>();
            if (null != groupinfo) {
                for (GroupDetail item : groupinfo) {
                    GroupDetalDO groupDetalDO = BeanCopyHelper.cast2GroupDetalDO(item);
                    groupDOs.add(groupDetalDO);
                }
            }
            if (null != userinfo) {
                for (UserInfo item : userinfo) {
                    UserInfoDO userInfoDO = BeanCopyHelper.cast2UserInfoDO(item);
                    usernoDOs.add(userInfoDO);
                }
            }
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    GroupDetalDao groupDetalDao = daoSession.getGroupDetalDao();
                    UserInfoDao userInfoDao = daoSession.getUserInfoDao();
                    if (null != groupDOs && groupDOs.size() > 0) {
                        groupDetalDao.insertInTx(groupDOs);
                    }
                    Log.i(TAG, "同步群组信息成功");
                    if (null != usernoDOs && usernoDOs.size() > 0) {
                        int count = 0;
                        userInfoDao.insertInTx(usernoDOs);
                        Log.i(TAG, "同步用户信息成功");
                    }
                }
            });
        }
    }
}
