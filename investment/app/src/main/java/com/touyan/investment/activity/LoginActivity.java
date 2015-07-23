package com.touyan.investment.activity;

import android.content.Intent;
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
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.helper.SharedPreferencesHelper;
import com.touyan.investment.manager.LoginManager;
import com.touyan.investment.mview.EditTextWithDelete;

public class LoginActivity extends AbsActivity implements OnClickListener {

    private final static int LOGIN_DATA = 0;

    private TextView register_tv;

    private TextView recover_tv;

    private EditTextWithDelete phone_et;

    private EditTextWithDelete password_et;

    private String phone;

    private String password;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case LOGIN_DATA:
                    loadLoginData((CommonResponse) msg.obj);
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
            SharedPreferencesHelper.setString(this, Constant.LoginUser.SHARED_PREFERENCES_PHONE, phone);
            SharedPreferencesHelper.setString(this, Constant.LoginUser.SHARED_PREFERENCES_PASSWORD, password);
            LoginResult result = (LoginResult) resposne.getData();
            UserInfo userInfo = result.getUsinfo();
            String userJson = JSON.toJSONString(userInfo);
            SharedPreferencesHelper.setString(this, Constant.LoginUser.SHARED_PREFERENCES_USER,userJson );
            App.getInstance().setgUserInfo(result.getUsinfo());
            toMainActivity();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.register_tv) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (id == R.id.recover_tv) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.login_btn) {
            OWNLogin();
        }
    }

    private void findView() {
        Button login_btn = (Button) findViewById(R.id.login_btn);
        phone_et = (EditTextWithDelete) findViewById(R.id.phone_et);
        password_et = (EditTextWithDelete) findViewById(R.id.password_et);
        register_tv = (TextView) findViewById(R.id.register_tv);
        recover_tv = (TextView) findViewById(R.id.recover_tv);
        register_tv.setOnClickListener(this);
        recover_tv.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        String phone = SharedPreferencesHelper.getString(this, Constant.LoginUser.SHARED_PREFERENCES_PHONE, "");
        String password = SharedPreferencesHelper.getString(this, Constant.LoginUser.SHARED_PREFERENCES_PASSWORD, "");
        phone_et.setText(phone);
        password_et.setText(password);
        if(StringUtil.isNotBlank(phone)&&StringUtil.isNotBlank(password)){
            OWNLogin();
        }
    }

    /**
     * 登录
     */
    private void Login(String phone, String password) {
        LoginManager manager = new LoginManager();
        manager.login(this, phone, password, null, null, LoginParam.OWN, activityHandler, LOGIN_DATA);
    }

    /**
     * 自己服务器登陆
     */
    private void OWNLogin() {
        if (isCheck()) {
            dialogShow(R.string.login_prompt);
            Login(phone_et.getText().toString(), password_et.getText().toString());
        }
    }

    /**
     * 登录检测
     *
     * @return
     */
    private boolean isCheck() {
        phone = phone_et.getText().toString();
        if (StringUtil.isBlank(phone)) {
            CommonUtil.showToast("请输入您的手机号码");
            return false;
        }

        if (!StringUtil.checkMobile(phone.replace(" ", ""))) {
            CommonUtil.showToast("手机号码输入错误啦");
            return false;
        }

        password = password_et.getText().toString();
        if (StringUtil.isBlank(password)) {
            CommonUtil.showToast("请输入您的密码");
            return false;
        }
        return true;
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

}
