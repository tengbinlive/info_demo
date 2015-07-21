package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.FriendListHeadersAdapter;
import com.touyan.investment.bean.user.QueryUserFansResult;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.helper.HanziComp;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.IndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 2015/7/20.
 */
public class UserFollowActivity extends AbsActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private Comparator cmp = new HanziComp();

    private IndexableListView listView;

    private ArrayList<Subscriber> subscribers;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            QueryUserFansResult result = (QueryUserFansResult) resposne.getData();
            subscribers = result.getSubscribers();
            hanziSequence();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void hanziSequence() {
        if (subscribers == null) {
            return;
        }
        Collections.sort(subscribers, cmp);
        initListView();
    }


    @Override
    public void EInit() {
        super.EInit();
        findView();
        init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_follow;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediate("关注");
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        listView = (IndexableListView) findViewById(R.id.listview);
        listView.setFastScrollEnabled(true);
        View ll_listEmpty = findViewById(R.id.ll_listEmpty);
        listView.setEmptyView(ll_listEmpty);
    }

    // 初始化资源
    private void init() {
        dialogShow();
        getDataList();
    }

    private void initListView() {

        FriendListHeadersAdapter mAdapter = new FriendListHeadersAdapter(this, subscribers);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listView));

        assert animationAdapter.getViewAnimator() != null;
        animationAdapter.getViewAnimator().setInitialDelayMillis(500);

        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);

        listView.setAdapter(stickyListHeadersAdapterDecorator);
    }

    private void getDataList() {
        UserManager manager = new UserManager();
        manager.queryUserFollow(this, App.getInstance().getgUserInfo().getServno(), activityHandler, INIT_LIST);
    }

}
