package com.touyan.investment.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.helper.SharedPreferencesHelper;
import com.touyan.investment.mview.BottomView;

public class CreateGroupActivity extends AbsActivity implements OnClickListener {
    private Context context;

    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_creategroup;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.create_group);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.create);
        setToolbarRightOnClick(this);

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        context = CreateGroupActivity.this;
    }

}
