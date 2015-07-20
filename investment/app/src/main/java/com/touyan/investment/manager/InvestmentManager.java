package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.App;
import com.touyan.investment.bean.main.*;
import com.touyan.investment.bean.user.AccountParam;
import com.touyan.investment.bean.user.AccountResult;

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
     * 获取info 列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void querySubscribe(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        InvInfoAttentionParam param = new InvInfoAttentionParam();
        param.setServno(App.getInstance().getgUserInfo().getServno());
        param.setPageno(page_size);
        param.setStatno(page_number);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_SUBSCRIBE);
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
     * 资讯详情
     *
     * @param context
     * @param infoid         资讯id
     * @param handler
     * @param handlerMsgCode
     */
    public void queryInfoDetail(Context context, String infoid, final Handler handler, final int handlerMsgCode) {

        InvActParam param = new InvActParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setInfoid(infoid);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_INFO_DETAIL);
        param.setParseTokenType(new TypeReference<InvInfoDetailResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 活动详情
     *
     * @param context
     * @param actid          活动id
     * @param handler
     * @param handlerMsgCode
     */
    public void queryActDetail(Context context, String actid, final Handler handler, final int handlerMsgCode) {

        InvActParam param = new InvActParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setActvid(actid);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_ACT_DETAIL);
        param.setParseTokenType(new TypeReference<InvActDetailResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 活动 报名人员信息
     *
     * @param context
     * @param actid          活动id
     * @param handler
     * @param handlerMsgCode
     */
    public void queryActJoinUser(Context context, String actid, final Handler handler, final int handlerMsgCode) {

        InvActParam param = new InvActParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setActvid(actid);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_ACT_JOIN_USER);
        param.setParseTokenType(new TypeReference<InvActJoinResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }


    /**
     * 获取act 列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void LoginAct(Context context, String page_number, String page_size, final Handler handler, final int handlerMsgCode) {

        InvInfoParam param = new InvInfoParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setItitle("111");
        param.setStartno(111);
        param.setPageSize(111);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_GET_CODE);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 评论资讯/回复悬赏
     *
     * @param context
     * @param mesgid         资讯/悬赏ID
     * @param rpuser         回复用户ID
     * @param contnt         回复内容
     * @param mesgtp         回复类型 1资讯，2活动，3悬赏
     * @param handler
     * @param handlerMsgCode
     */
    public void replyDiscuss(Context context, String mesgid, String rpuser, String contnt, String mesgtp, final Handler handler, final int handlerMsgCode) {

        InvReViewParam param = new InvReViewParam();

        param.setMesgid(mesgid);
        param.setContnt(contnt);
        param.setRpuser(rpuser);
        param.setMesgtp(mesgtp);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.REPLY_DISCUSS);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 收藏
     *
     * @param context
     * @param mesgid         资讯/悬赏ID
     * @param mesgtp         回复类型 1资讯，2活动，3悬赏
     * @param handler
     * @param handlerMsgCode
     */
    public void storeMsg(Context context, String mesgid, String mesgtp, final Handler handler, final int handlerMsgCode) {

        InvCollectParam param = new InvCollectParam();

        param.setMesgid(mesgid);
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setMesgtp(mesgtp);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_STORE_MSG);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 采纳
     *
     * @param context
     * @param mesgid         资讯/悬赏ID
     * @param replyid         回复id
     * @param rpuser         回复用户id
     * @param price         悬赏金额
     * @param handler
     * @param handlerMsgCode
     */
    public void loadAdoption(Context context ,String rpuser, String mesgid, Double price, String replyid, final Handler handler, final int handlerMsgCode) {

        InvAdoptionParam param = new InvAdoptionParam();

        param.setReplyid(replyid);
        param.setMesgid(mesgid);
        param.setRpuser(rpuser);
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setPrice(price);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_ADOPTION);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 购买
     *
     * @param context
     * @param infoid         资讯/悬赏ID
     * @param price          费用
     * @param handler
     * @param handlerMsgCode
     */
    public void buyInfo(Context context, String infoid, Double price, final Handler handler, final int handlerMsgCode) {

        InvBuyInfoParam param = new InvBuyInfoParam();

        param.setInfoid(infoid);
        param.setPrice(price);
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_BUY_INFO);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 账户查询
     *
     * @param context
     * @param handler
     * @param handlerMsgCode
     */
    public void queryAccount(Context context, final Handler handler, final int handlerMsgCode) {

        AccountParam param = new AccountParam();

        param.setUserid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_ACCOUNT);
        param.setParseTokenType(new TypeReference<AccountResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 打赏
     *
     * @param context
     * @param infoid         资讯ID
     * @param targetid       打赏目标id
     * @param amount         打赏金额
     * @param handler
     * @param handlerMsgCode
     */
    public void recordRewards(Context context, String infoid, String targetid, Double amount, final Handler handler, final int handlerMsgCode) {

        InvRecordRewardsParam param = new InvRecordRewardsParam();

        param.setInfoid(infoid);
        param.setTargetid(targetid);
        param.setAmount(amount);
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.RECORD_REWARDS);
        param.setParseTokenType(new TypeReference<InvRecordRewardsResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }


    /**
     * 报名
     *
     * @param context
     * @param actvid         活动ID
     * @param price          花费
     * @param handler
     * @param handlerMsgCode
     */
    public void actSign(Context context, String actvid, String price, final Handler handler, final int handlerMsgCode) {

        InvActSignParam param = new InvActSignParam();

        param.setActvid(actvid);
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setPrice(price);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_ACT_SIGN);
        param.setParseTokenType(new TypeReference<OpenApiSimpleResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 收藏
     *
     * @param context
     * @param type           活动分类 “001” 产品 ，“002”路演
     * @param page_number    分页 当前页
     * @param page_size      数据个数
     * @param handler
     * @param handlerMsgCode
     */
    public void actList(Context context, String type, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        InvActListParam param = new InvActListParam();

        param.setActvtp(type);
        param.setStatno(page_number);
        param.setPageno(page_size);
        param.setServno(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_ACT_LIST);
        param.setParseTokenType(new TypeReference<InvActListResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 获取 原创资讯 列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryMyOriginalInfos(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        MyOriInfoParam param = new MyOriInfoParam();
        //param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setPubsid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_ORIGINAL_INFO);

        param.setParseTokenType(new TypeReference<InvInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }



    /**
     * 获取 原创资讯 列表数据
     *
     * @param context
     * @param page_number    当前页数
     * @param page_size      获取个数
     * @param handler
     * @param handlerMsgCode
     */
    public void queryMyPurchaseInfos(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        MyOriInfoParam param = new MyOriInfoParam();
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_PURCHASE_INFO);

        param.setParseTokenType(new TypeReference<InvInfoResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }


    /**
     * 我发布的活动
     *
     * @param context
     * @param page_number    分页 当前页RELEASE
     * @param page_size      数据个数
     * @param handler
     * @param handlerMsgCode
     */
    public void myReleaseActList(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        MyActivityParam param = new MyActivityParam();

        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);
        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_MYRELEASE_ACT);
        param.setParseTokenType(new TypeReference<MyActListResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }


    /**
     * 我参与的活动
     *
     * @param context
     * @param page_number    分页 当前页PARTAKE
     * @param page_size      数据个数
     * @param handler
     * @param handlerMsgCode
     */
    public void myPartakeActList(Context context, int page_number, int page_size, String ckstau ,final Handler handler, final int handlerMsgCode) {

        MyActivityParam param = new MyActivityParam();

        param.setUserid(App.getInstance().getgUserInfo().getServno());
        param.setStartno(page_number);
        param.setPageSize(page_size);
        param.setCkstau(ckstau);

        // 接口参数
        param.setMethod(OpenApiMethodEnum.LOAD_MYPARTAKE_ACT);
        param.setParseTokenType(new TypeReference<InvActListResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }
    /**
     * 悬赏列表
     *
     * @param context
     * @param page_number    分页 当前页
     * @param page_size      数据个数
     * @param handler
     * @param handlerMsgCode
     */
    public void offerList(Context context, int page_number, int page_size, final Handler handler, final int handlerMsgCode) {

        InvOfferListParam param = new InvOfferListParam();

        param.setStartno(page_number);
        param.setPageSize(page_size);
        param.setUserid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QUERY_REWARDS);
        param.setParseTokenType(new TypeReference<InvOfferListResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 发布资讯
     *
     * @param context
     * @param param          资讯数据
     * @param handler
     * @param handlerMsgCode
     */
    public void releaseInfo(Context context, InvReleaseInfoParam param, final Handler handler, final int handlerMsgCode) {

        param.setPubsid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.PUBLISH_INFO);
        param.setParseTokenType(new TypeReference<InvInfoReleaseResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 发布活动
     *
     * @param context
     * @param param          资讯数据
     * @param handler
     * @param handlerMsgCode
     */
    public void releaseAct(Context context, InvReleaseActParam param, final Handler handler, final int handlerMsgCode) {

        param.setPubsid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.PUBLISH_ACT);
        param.setParseTokenType(new TypeReference<InvActDetailResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

    /**
     * 发布悬赏
     *
     * @param context
     * @param param          资讯数据
     * @param handler
     * @param handlerMsgCode
     */
    public void releaseReward(Context context, InvReleaseOfferParam param, final Handler handler, final int handlerMsgCode) {

        param.setPubsid(App.getInstance().getgUserInfo().getServno());
        // 接口参数
        param.setMethod(OpenApiMethodEnum.PUBLISH_REWARD);
        param.setParseTokenType(new TypeReference<InvReleaseOfferResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

}
