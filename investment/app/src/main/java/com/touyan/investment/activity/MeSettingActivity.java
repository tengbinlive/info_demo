package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class MeSettingActivity extends AbsActivity implements OnClickListener {
    private Button loginout_btn ;
    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
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
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        loginout_btn = (Button) findViewById(R.id.loginout_btn);
        loginout_btn.setOnClickListener(this);
    }

}
