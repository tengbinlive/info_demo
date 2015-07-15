package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.touyan.investment.bean.user.ModifyUserInfoParam;
import com.touyan.investment.bean.user.ModifyUserInfoResult;

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
}
