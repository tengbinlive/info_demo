package com.touyan.investment.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.core.util.CommonUtil;
import com.joooonho.SelectableRoundedImageView;
import com.rey.material.widget.CheckBox;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.helper.SharedPreferencesHelper;
import com.touyan.investment.mview.BottomView;

public class CreateGroupActivity extends AbsActivity implements OnClickListener {
    private Context context;
    private SelectableRoundedImageView head;
    private EditText groupname_et;
    private CheckBox visible_cb;
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
        head.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.head) {
            CommonUtil.showToast("点击");
        } else if (id == R.id.toolbar_right_btn) {
            CommonUtil.showToast("点击");
        }
    }

    private void findView() {
        context = CreateGroupActivity.this;
        head = (SelectableRoundedImageView)findViewById(R.id.head);
        groupname_et = (EditText)findViewById(R.id.groupname_et);
        visible_cb = (CheckBox)findViewById(R.id.visible_cb);
    }

}
