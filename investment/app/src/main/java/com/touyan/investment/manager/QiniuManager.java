package com.touyan.investment.manager;

import android.content.Context;
import android.os.Handler;
import com.alibaba.fastjson.TypeReference;
import com.core.CommonDataLoader;
import com.core.CommonRequest;
import com.core.openapi.OpenApiMethodEnum;
import com.touyan.investment.bean.qiniu.QiniuUploadParam;
import com.touyan.investment.bean.qiniu.QiniuUploadResult;
import com.touyan.investment.bean.user.*;

/**
 * 用户中心业务类.
 *
 * @author HuangChao
 */
public class QiniuManager {

    private static final String TAG = QiniuManager.class.getSimpleName();

    /**
     * 七牛上传token获取
     *
     * @param context        上下文
     * @param handler        在Activity中处理返回结果的Handler
     * @param handlerMsgCode 返回结果的Handler的Msg代码
     */
    public void qiniuUpload(Context context, final Handler handler, final int handlerMsgCode) {
        QiniuUploadParam param = new QiniuUploadParam();
        param.setBucket("investor");
        // 接口参数
        param.setMethod(OpenApiMethodEnum.QINIU_UPLOAD);
        param.setParseTokenType(new TypeReference<QiniuUploadResult>() {
        });
        // 请求对象
        CommonRequest request = new CommonRequest(param, handler, handlerMsgCode);
        // 开始执行加载
        CommonDataLoader.getInstance(context).load(request);
    }

}
