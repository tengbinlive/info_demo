/************************************************************
 * * EaseMob CONFIDENTIAL
 * __________________
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * NOTICE: All information contained herein is, and remains
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */
package com.touyan.investment.hx;

import android.content.Context;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.EMLog;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import org.json.JSONObject;

/**
 * 环信工具类
 */
public class HXUtil {

    /**
     * 根据消息内容和消息类型获取消息内容提示
     *
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
            case LOCATION: // 位置消息
                if (message.direct == EMMessage.Direct.RECEIVE) {
                    // 从sdk中提到了ui中，使用更简单不犯错的获取string的方法
                    // digest = EasyUtils.getAppResourceString(context,
                    // "location_recv");
                    digest = getStrng(context, R.string.location_recv);
                    digest = String.format(digest, message.getFrom());
                    return digest;
                } else {
                    // digest = EasyUtils.getAppResourceString(context,
                    // "location_prefix");
                    digest = getStrng(context, R.string.location_prefix);
                }
                break;
            case IMAGE: // 图片消息
                ImageMessageBody imageBody = (ImageMessageBody) message.getBody();
                digest = getStrng(context, R.string.picture) + imageBody.getFileName();
                break;
            case VOICE:// 语音消息
                digest = getStrng(context, R.string.voice);
                break;
            case VIDEO: // 视频消息
                digest = getStrng(context, R.string.video);
                break;
            case TXT: // 文本消息
                if (isRobotMenuMessage(message)) {
                    digest = getRobotMenuMessageDigest(message);
                } else if (message.getBooleanAttribute(HXConstant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    TextMessageBody txtBody = (TextMessageBody) message.getBody();
                    digest = getStrng(context, R.string.voice_call) + txtBody.getMessage();
                } else {
                    TextMessageBody txtBody = (TextMessageBody) message.getBody();
                    digest = txtBody.getMessage();
                }
                break;
            case FILE: // 普通文件消息
                digest = getStrng(context, R.string.file);
                break;
            default:
                digest = "unknow type";
                return "";
        }

        return digest;
    }

    private static String getStrng(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static boolean isRobotMenuMessage(EMMessage message) {

        try {
            JSONObject jsonObj = message.getJSONObjectAttribute(HXConstant.MESSAGE_ATTR_ROBOT_MSGTYPE);
            if (jsonObj.has("choice")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getRobotMenuMessageDigest(EMMessage message) {
        String title = "";
        try {
            JSONObject jsonObj = message.getJSONObjectAttribute(HXConstant.MESSAGE_ATTR_ROBOT_MSGTYPE);
            if (jsonObj.has("choice")) {
                JSONObject jsonChoice = jsonObj.getJSONObject("choice");
                title = jsonChoice.getString("title");
            }
        } catch (Exception e) {
        }
        return title;
    }
}
