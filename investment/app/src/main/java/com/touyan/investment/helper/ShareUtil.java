package com.touyan.investment.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.touyan.investment.R;
import com.touyan.investment.enums.PlatformEnum;
import com.touyan.investment.mview.BottomView;

public class ShareUtil {

    private static ShareUtil instance;


    public static ShareUtil getInstance() {
        if (instance == null) instance = new ShareUtil();
        return instance;
    }

    public static Platform.ShareParams getParams(String title, String contents, String url, Bitmap icon) {
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(title);
        shareParams.setText(contents);
        shareParams.setImageData(icon);
        shareParams.setUrl(url);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        return shareParams;
    }

    public static void showShareView(Context context, final Platform.ShareParams module) {
        final BottomView mBottomView = new BottomView(context, R.style.BottomViewTheme_Defalut, R.layout.bottom_share_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);
        ImageView share_weixin_friend = (ImageView) mBottomView.getView().findViewById(R.id.share_weixin_friend);
        ImageView share_weixin = (ImageView) mBottomView.getView().findViewById(R.id.share_weixin);
        TextView cancel = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_cancel);

        share_weixin_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomView.dismissBottomView();
                sendShare(PlatformEnum.WEIXIN_TIMELINE, module);
            }
        });
        share_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomView.dismissBottomView();
                ShareUtil.sendShare(PlatformEnum.WEIXIN, module);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomView.dismissBottomView();
            }
        });

        mBottomView.showBottomView(true);
    }


    /**
     * 分享到第三方
     *
     * @param type
     */
    public static void sendShare(PlatformEnum type, ShareParams module) {
        if (module == null) {
            return;
        }
        switch (type) {
            case WEIXIN:
                Platform weixin = ShareSDK.getPlatform(type.getCode());
                weixin.share(module);
                break;
            case WEIXIN_TIMELINE:
                Platform weixinmoments = ShareSDK.getPlatform(type.getCode());
                weixinmoments.share(module);
                break;
            default:
                break;
        }

    }

}
