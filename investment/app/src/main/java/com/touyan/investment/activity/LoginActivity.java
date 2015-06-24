package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class LoginActivity extends AbsActivity implements OnClickListener {

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
        if (v.getId() == R.id.login_ly) {







        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        RelativeLayout launcher_ly = (RelativeLayout) findViewById(R.id.login_ly);
        launcher_ly.setOnClickListener(this);
    }

}
