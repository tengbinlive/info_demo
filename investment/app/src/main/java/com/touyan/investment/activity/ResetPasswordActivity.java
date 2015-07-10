package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class ResetPasswordActivity extends AbsActivity implements OnClickListener {
    private Button submit_btn ;
    private boolean isAgree = false;
    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();
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
        if (v.getId() == R.id.submit_btn) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
    }

}
