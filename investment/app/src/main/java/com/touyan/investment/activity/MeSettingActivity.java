package com.touyan.investment.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class MeSettingActivity extends AbsActivity implements OnClickListener {
    private Context context;
    private Button loginout_btn ;
    private RelativeLayout password_rl,about_rl;

    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_meset;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.setting_str);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginout_btn) {

        }
        else if(v.getId() == R.id.password_rl){
            Intent intent = new Intent(context, ResetPasswordActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.about_rl){
            Intent intent = new Intent(context, AboutInvActivity.class);
            startActivity(intent);
        }




    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        context = MeSettingActivity.this;
        loginout_btn = (Button) findViewById(R.id.loginout_btn);
        password_rl = (RelativeLayout)findViewById(R.id.password_rl);
        about_rl = (RelativeLayout)findViewById(R.id.about_rl);

        loginout_btn.setOnClickListener(this);
        password_rl.setOnClickListener(this);
        about_rl.setOnClickListener(this);
    }

}
