package com.touyan.investment.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.FriendListHeadersAdapter;
import com.touyan.investment.bean.user.BatchInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.event.OnContactDeletedEvent;
import com.touyan.investment.helper.UserInfoComp;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.IndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GungFriendFragment extends AbsFragment {

    UserManager userManager = new UserManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;

    private IndexableListView listView;

    private ArrayList<UserInfo> friends;
    private List<String> usernames = null;

    private FriendListHeadersAdapter mAdapter;

    private Comparator cmp = new UserInfoComp();


    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
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
            BatchInfoResult result = (BatchInfoResult) resposne.getData();
            friends = result.getUserinfo();
            hanziSequence();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void hanziSequence() {
        if (friends == null) {
            return;
        }
        Collections.sort(friends, cmp);
        initListView();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        return mInflater.inflate(R.layout.fragment_gung_friend, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    // 初始化资源
    private void init() {
        listView = (IndexableListView) getView().findViewById(R.id.stickylistheaders_listview);
        listView.setFastScrollEnabled(true);
        View ll_listEmpty = getView().findViewById(R.id.ll_listEmpty);
        listView.setEmptyView(ll_listEmpty);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            listView.setFitsSystemWindows(true);
        }

        dialogShow();
        getDataList();
    }

    private void initListView() {


        mAdapter = new FriendListHeadersAdapter(this.getActivity(), friends);

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
        activityHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMContactManager.getInstance().getContactUserNames();
                    userManager.batchInfo(GungFriendFragment.this.getActivity(), (ArrayList<String>) usernames, new ArrayList<String>(), activityHandler, LOAD_DATA);

                } catch (EaseMobException e) {

                }
            }
        });
    }

    @Override
    public void scrollToTop() {

    }

    public void onEventMainThread(OnContactDeletedEvent event) {
        usernames.removeAll(event.getUsernameList());
        userManager.batchInfo(this.getActivity(), (ArrayList<String>) usernames, new ArrayList<String>(), activityHandler, LOAD_DATA);
    }
}
