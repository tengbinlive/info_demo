package com.touyan.investment.helper;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import com.core.util.StringUtil;

public class SMSContentObserver extends ContentObserver {
    private Context mContext;
    private Handler mHandler;

    public SMSContentObserver(Context context, Handler handler, EditText smsInput) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(uri, null, null, null, "date desc");
        if (c != null) {
            while (c.moveToNext()) {
                String smsBody = c.getString(c.getColumnIndex("body"));
                setSms(smsBody);
            }
            c.close();
        }
    }

    private void setSms(String smsBody) {
        if (StringUtil.isNotBlank(smsBody)) {
//            int index = smsBody.indexOf("coolpark");
//            if (index != -1) {
//                index = smsBody.indexOf("验证码是：");
//                int end = smsBody.indexOf("。");
//                String securityCde = smsBody.substring(index + 1, end);
//                Message message = new Message();
//                message.what = LoginActivity.LOAD_AUTHCODE_FILL;
//                message.obj = securityCde;
//                mHandler.sendMessage(message);
//            }
        }
    }
}