package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.bean.login.LoginParam;
import com.touyan.investment.manager.LoginManager;
import com.touyan.investment.mview.EditTextWithDelete;

public class LoginActivity extends AbsActivity implements OnClickListener {

    private final static int LOGIN_DATA = 0;

    private TextView register_tv, recover_tv;

    private EditTextWithDelete phone_et;

    private EditTextWithDelete password_et;


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
            Login();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    /**
     * 登录
     */
    private void Login() {
        if (isCheck()) {
            LoginManager manager = new LoginManager();
            manager.Login(this, ""+phone_et.getText(), ""+password_et.getText(), LoginParam.OWN, activityHandler, LOGIN_DATA);
        }
    }

    /**
     * 登录检测
     *
     * @return
     */
    private boolean isCheck() {
        return true;
    }

}
