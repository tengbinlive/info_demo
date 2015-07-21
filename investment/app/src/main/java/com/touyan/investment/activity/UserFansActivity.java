package com.touyan.investment.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.UserFansGridViewAdapter;
import com.touyan.investment.bean.user.QueryUserFansResult;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.GridViewWithHeaderAndFooter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/15.
 */
public class UserFansActivity extends AbsActivity implements AdapterView.OnItemClickListener {

    private static final int USERFANS_DATA = 0;

    private GridViewWithHeaderAndFooter gridView;

    private UserFansGridViewAdapter adapter;

    private ArrayList<Subscriber> subscribers;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case USERFANS_DATA:
                    loadUserFansData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadUserFansData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            QueryUserFansResult result = (QueryUserFansResult) resposne.getData();
            subscribers = result.getSubscribers();
            adapter = new UserFansGridViewAdapter(this, subscribers);
            gridView.setAdapter(adapter);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();
        initGridView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_fans;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.user_fans);
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.usertag_gridview);
    }

    private void initGridView() {
        UserInfo userInfo = App.getInstance().getgUserInfo();
        UserManager userManager = new UserManager();
        userManager.queryUserFansInfo(this, userInfo.getServno(), activityHandler, USERFANS_DATA);
        dialogShow(R.string.data_downloading);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(UserFansActivity.this, UserFansDetailsActivity.class);
        intent.putExtra("userid", subscribers.get(position).getScrino());
        intent.putExtra("otherid", subscribers.get(position).getServno());
        startActivity(intent);
        UserFansActivity.this.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }
}
