package com.touyan.investment.activity;

import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class AboutInvActivity extends AbsActivity {


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
