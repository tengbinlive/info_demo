package com.touyan.investment.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.bean.login.LoginParam;
import com.touyan.investment.bean.login.LoginResult;
import com.touyan.investment.bean.login.RegistParam;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.helper.SMSContentObserver;
import com.touyan.investment.helper.SharedPreferencesHelper;
import com.touyan.investment.manager.LoginManager;
import com.touyan.investment.mview.BottomView;
import com.touyan.investment.mview.EditTextWithDelete;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterNextActivity extends AbsActivity implements OnClickListener {

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
    private BottomView mBottomView;
    private Button register_btn;
    private Button sendcode_btn;
    private String phoneNum;
    private String password;
    private EditTextWithDelete register_et;
    private EditTextWithDelete password_et;
    private EditTextWithDelete invitecode_et;
    private EditTextWithDelete name_et;
    private TextView role_tv;

    private final static int AUTH_CODE = 0; //获取验证码

    private final static int REGISTER = 1; //用户注册

    private final static int LOGIN_DATA = 2; //登录

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case AUTH_CODE:
                    loadAuthCode((CommonResponse) msg.obj);
                    break;
                case REGISTER:
                    loadRegister((CommonResponse) msg.obj);
                    break;
                case LOGIN_DATA:
                    loadLoginData((CommonResponse) msg.obj);
                    break;
                case LOAD_AUTHCODE_FILL:
                    String securityCode = msg.obj.toString();
                    register_et.setText(securityCode);
                    register_et.setSelection(securityCode.length());
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 处理登陆数据
     *
     * @param resposne
     */
    private void loadLoginData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            SharedPreferencesHelper.setString(this, Constant.LoginUser.SHARED_PREFERENCES_PHONE, phoneNum);
            LoginResult result = (LoginResult) resposne.getData();
            UserInfo userInfo = result.getUsinfo();
            String userJson = JSON.toJSONString(userInfo);
            SharedPreferencesHelper.setString(this, Constant.LoginUser.SHARED_PREFERENCES_USER, userJson);
            App.getInstance().setgUserInfo(result.getUsinfo());
            toMainActivity();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadAuthCode(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            DKEY_START_TIME = System.currentTimeMillis();
            CommonUtil.showToast("验证码已发送，请注意查收。");
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadRegister(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            Login(phoneNum, password);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        phoneNum = getIntent().getStringExtra(KEY);
        super.EInit();
        findView();
        whiteColos = getResources().getColor(R.color.white);
        grayColos = getResources().getColor(R.color.white);
        StartThread();
        initSMSContentObserver();
        getAuthCode();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_register_next;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.register);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.register_btn) {
            register();
        } else if (id == R.id.sendcode_btn) {
            getAuthCode();
        } else if (id == R.id.role_et) {
            selectPostin();
        }
    }

    private void findView() {
        sendcode_btn = (Button) findViewById(R.id.sendcode_btn);
        register_et = (EditTextWithDelete) findViewById(R.id.register_et);
        password_et = (EditTextWithDelete) findViewById(R.id.password_et);
        invitecode_et = (EditTextWithDelete) findViewById(R.id.invitecode_et);
        name_et = (EditTextWithDelete) findViewById(R.id.name_et);
        role_tv = (TextView) findViewById(R.id.role_et);
        role_tv.setOnClickListener(this);
        sendcode_btn.setOnClickListener(this);
        TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
        phone_tv.setText(phoneNum);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
    }

    /**
     * 登录
     */
    private void Login(String phone, String password) {
        dialogShow("正在登录");
        LoginManager manager = new LoginManager();
        manager.login(this, phone, password, null, null, LoginParam.OWN, activityHandler, LOGIN_DATA);
    }

    private void getAuthCode() {
        if (!isVer && System.currentTimeMillis() - DKEY_START_TIME > DKEY_TIME) {
            isVer = true;
            manager.authCode(this, phoneNum, activityHandler, AUTH_CODE);
        }
    }

    private void register() {
        String verification = register_et.getText().toString();
        if (StringUtil.isBlank(verification)) {
            register_et.requestFocus();
            CommonUtil.showToast("请输入收到的验证码");
            return;
        }
        password = password_et.getText().toString();
        if (StringUtil.isBlank(password)) {
            password_et.requestFocus();
            CommonUtil.showToast("请输入您的密码");
            return;
        }
        String invitecode = invitecode_et.getText().toString();
        if (StringUtil.isBlank(invitecode)) {
            invitecode_et.requestFocus();
            CommonUtil.showToast("请输入您的邀请码");
            return;
        }
        String name = name_et.getText().toString();
        if (StringUtil.isBlank(name)) {
            name_et.requestFocus();
            CommonUtil.showToast("请输入您的姓名");
            return;
        }
        String role = role_tv.getText().toString();
        if (StringUtil.isBlank(name)) {
            selectPostin();
            return;
        }
        dialogShow("正在注册");
        RegistParam param = new RegistParam();
        param.setUalias(name);
        param.setServno(phoneNum);
        param.setAucode(verification);
        param.setIvcode(invitecode);
        param.setPasswd(password);
        param.setInrank(role);
        manager.register(this, param, activityHandler, REGISTER);
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void selectPostin() {
        if (mBottomView != null) {
            mBottomView.showBottomView(true);
            return;
        }
        mBottomView = new BottomView(this, R.style.BottomViewTheme_Defalut, R.layout.bottom_role_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);

        PostinButtonOnClickListener listener = new PostinButtonOnClickListener();
        mBottomView.getView().findViewById(R.id.bottom_tv_2).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_3).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_4).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_5).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_cancel).setOnClickListener(listener);

        mBottomView.showBottomView(true);
    }

    class PostinButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_tv_2:
                case R.id.bottom_tv_3:
                case R.id.bottom_tv_4:
                case R.id.bottom_tv_5:
                    String roleStr = ((TextView) v).getText().toString();
                    role_tv.setText(roleStr);
                    mBottomView.dismissBottomView();
                    break;
                case R.id.bottom_tv_cancel:
                    mBottomView.dismissBottomView();
                    break;
            }
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
