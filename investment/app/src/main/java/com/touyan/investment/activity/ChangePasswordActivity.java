package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class ChangePasswordActivity extends AbsActivity implements OnClickListener {
    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_changpassword;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.change_pw);
        setToolbarRightVisbility(View.VISIBLE,View.VISIBLE);
        setToolbarRightStrID(R.string.confirm);
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
