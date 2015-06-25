package com.touyan.investment.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class LoginActivity extends AbsActivity implements OnClickListener {
    private  TextView register_tv ,recover_tv;
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
        if (v.getId() == R.id.register_tv) {

            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.recover_tv ){
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        register_tv = (TextView) findViewById(R.id.register_tv);
        recover_tv = (TextView) findViewById(R.id.recover_tv);
        register_tv.setOnClickListener(this);
        recover_tv.setOnClickListener(this);
    }

}
