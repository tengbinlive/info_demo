package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class AboutInvActivity extends AbsActivity implements OnClickListener {


    @Override
    public void EInit() {
        super.EInit();
        findView();
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
    @Override
    public void onClick(View v) {

    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {

    }

}
