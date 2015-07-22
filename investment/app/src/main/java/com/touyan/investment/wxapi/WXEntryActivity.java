package com.touyan.investment.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.core.util.CommonUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.touyan.investment.App;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = WXEntryActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getWXApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        App.getWXApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq arg0) {
        CommonUtil.showToast("onReq " + arg0);
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        CommonUtil.showToast("onResp");
        if (resp == null) {
            finish();
            return;
        }
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            CommonUtil.showToast("支付");
            if (resp.errCode == 0) {
                toPaySuccessActivity();
            } else if (resp.errCode == -1) {
                CommonUtil.showToast("支付失败");
            } else if (resp.errCode == -2) {
                CommonUtil.showToast("支付取消");
            }
        } else
            // 如果是分享
            if (resp instanceof SendMessageToWX.Resp) {
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "分享成功";
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "分享取消";
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "认证失败";
                        break;
                    default:
                        result = "未知错误";
                        break;
                }
            }
            // 如果是获取授权信息
//            else if (resp instanceof Resp) {
//                switch (resp.errCode) {
//                    case BaseResp.ErrCode.ERR_OK:
//                        Resp authResp = (Resp) resp;
//                        Intent intent = new Intent(Constant.WeiXin.AUTH_SUCCESS_FROM_WX);
//                        intent.putExtra(Constant.WeiXin.AUTH_RESPONSE_CODE, authResp.code);
//                        intent.putExtra(Constant.WeiXin.AUTH_RESPONSE_URL, authResp.url);
//                        intent.putExtra(Constant.WeiXin.AUTH_RESPONSE_STATE, authResp.state);
//                        sendBroadcast(intent);
//                        break;
//                    case BaseResp.ErrCode.ERR_USER_CANCEL:
//                        result = "登录取消";
//                        break;
//                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                        result = "认证失败";
//                        break;
//                    default:
//                        result = "未知错误";
//                        break;
//                }
//            }
            else {
                result = "类型错误";
            }
        CommonUtil.showToast(result);
        finish();
    }

    private void toPaySuccessActivity() {
        finish();
//        Intent intent = new Intent(this, PaySuccessActivity.class);
//        intent.putExtra(PaySuccessActivity.KEY, App.orderArray);
//        startActivity(intent);
    }

}
