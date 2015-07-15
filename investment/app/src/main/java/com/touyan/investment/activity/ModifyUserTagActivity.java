package com.touyan.investment.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.UserTagGridViewAdapter;
import com.touyan.investment.mview.GridViewWithHeaderAndFooter;

/**
 * Created by Administrator on 2015/7/14.
 */
public class ModifyUserTagActivity extends AbsActivity implements View.OnClickListener {

    private final static int RESULTCODE_USERTAG = 0;

    private LayoutInflater mInflater;
    private GridViewWithHeaderAndFooter gridView;
    private UserTagGridViewAdapter adapter;

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        mInflater = getLayoutInflater();
        findView();
        initGridView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_modify_usertag;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarRightStrID(R.string.login_fulfil);
        setToolbarIntermediateStrID(R.string.modify_usertag_toolbar_title);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightOnClick(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.usertag_gridview);
    }

    private void initGridView() {
        LinearLayout gridHead = (LinearLayout) mInflater.inflate(R.layout.head_usertag_gridview, null);
        gridView.addHeaderView(gridHead);
        String[] userTags = getResources().getStringArray(R.array.user_tag_array);
        adapter = new UserTagGridViewAdapter(this, userTags);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_btn:
                Intent result = new Intent();
                result.putIntegerArrayListExtra(KEY, adapter.getSelectItemList());
                setResult(RESULTCODE_USERTAG, result);
                scrollToFinishActivity();
                break;
        }
    }
}
