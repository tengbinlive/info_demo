package com.touyan.investment.activity;

import android.widget.TextView;

import com.touyan.investment.AbsActivity;
import com.touyan.investment.BuildConfig;
import com.touyan.investment.R;

public class AboutInvActivity extends AbsActivity {

    @Override
    public void EInit() {
        super.EInit();
        TextView version = (TextView) findViewById(R.id.version_tv);
        version.setText(getString(R.string.app_name)+" v"+ BuildConfig.VERSION_NAME);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_aboutinv;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.about_inv);
    }

}
