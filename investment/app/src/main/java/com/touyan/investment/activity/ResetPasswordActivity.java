package com.touyan.investment.activity;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.helper.SMSContentObserver;
import com.touyan.investment.manager.LoginManager;
import com.touyan.investment.mview.EditTextWithDelete;

import java.util.Timer;
import java.util.TimerTask;

public class ResetPasswordActivity extends AbsActivity implements OnClickListener {

    public static final long DKEY_TIME = 60 * 1000;// 获取动态验证码的时间间隔 60 秒

    private final Timer timer = new Timer();

    private long DKEY_START_TIME = 0;

    private SMSContentObserver smsContentObserver;

    private boolean isVer = false;

    private int whiteColos;

    private int grayColos;

    /**
     * 填写验证码
     */
    public static final int LOAD_AUTHCODE_FILL = 3;

    private LoginManager manager = new LoginManager();

    private final static int AUTH_CODE = 0; //获取验证码

    private final static int RESET_PASSWORD = 1; //重置密码

    private EditTextWithDelete code_et;
    private EditTextWithDelete phone_et;
    private EditTextWithDelete restpassword_et;
    private Button submit_btn ;
    private Button sendcode_btn ;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case LOAD_AUTHCODE_FILL:
                    String securityCode = msg.obj.toString();
                    code_et.setText(securityCode);
                    code_et.setSelection(securityCode.length());
                    break;
                case AUTH_CODE:
                    loadAuthCode((CommonResponse) msg.obj);
                    break;
                case RESET_PASSWORD:
                    loadResetPassword((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadAuthCode(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            DKEY_START_TIME = System.currentTimeMillis();
            CommonUtil.showToast("验证码已发送，请注意查收。");
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadResetPassword(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            CommonUtil.showToast("密码重置成功拉");
            finish();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }


    @Override
    public void EInit() {
        super.EInit();
        findView();
        whiteColos = getResources().getColor(R.color.white);
        grayColos = getResources().getColor(R.color.white);
        StartThread();
        initSMSContentObserver();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.resetpassword);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submit_btn) {
            submit();
        } else if (id == R.id.sendcode_btn) {
            String phone = phone_et.getText().toString();
            if(StringUtil.isNotBlank(phone)) {
                getAuthCode(phone);
            }else{
                CommonUtil.showToast("请输入您的手机号码");
            }
        }
    }

    private void findView() {
        code_et = (EditTextWithDelete) findViewById(R.id.code_et);
        phone_et = (EditTextWithDelete) findViewById(R.id.phone_et);
        restpassword_et = (EditTextWithDelete) findViewById(R.id.restpassword_et);
        sendcode_btn = (Button) findViewById(R.id.sendcode_btn);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        sendcode_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }

    /**
     * 提交重置密码
     */
    private void submit(){
        String phone = phone_et.getText().toString();
        if(StringUtil.isBlank(phone)){
            CommonUtil.showToast("请输入您的手机号码");
            return;
        }
        String code = code_et.getText().toString();
        if(StringUtil.isBlank(code)){
            CommonUtil.showToast("请输入您的验证码");
            return;
        }
        String restpassword = restpassword_et.getText().toString();
        if(StringUtil.isBlank(restpassword)){
            CommonUtil.showToast("请输入您的新密码");
            return;
        }
        dialogShow(R.string.carrying);
        manager.resetPassword(this,phone,code,restpassword,activityHandler,RESET_PASSWORD);
    }

    private void getAuthCode(String phoneNum) {
        if (!isVer && System.currentTimeMillis() - DKEY_START_TIME > DKEY_TIME) {
            isVer = true;
            manager.authCode(this, phoneNum, activityHandler, AUTH_CODE);
        }
    }

    private void initSMSContentObserver() {
        smsContentObserver = new SMSContentObserver(this, activityHandler);
        //注册短信变化监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContentObserver);
    }

    private void StartThread() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                myHander.sendEmptyMessage(0);
            }
        }, 0, 1000);
    }

    Handler myHander = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                long TimeUsed = System.currentTimeMillis() - DKEY_START_TIME;
                setDKeyBtnText(TimeUsed);
            }
        }

        ;
    };

    private void setDKeyBtnText(long TimeUsed) {
        int TimeSeconds = (59) - (int) TimeUsed / 1000;
        if (TimeUsed < DKEY_TIME) {
            sendcode_btn.setText("重新获取" + TimeSeconds + "'");
            sendcode_btn.setTextColor(whiteColos);
        } else {
            isVer = false;
            sendcode_btn.setText(getString(R.string.login_verification_title));
            sendcode_btn.setTextColor(grayColos);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (smsContentObserver != null) {
            this.getContentResolver().unregisterContentObserver(smsContentObserver);
            smsContentObserver = null;
        }
    }

}
