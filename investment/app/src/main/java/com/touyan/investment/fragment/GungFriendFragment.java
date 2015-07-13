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
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.FriendListHeadersAdapter;
import com.touyan.investment.manager.InvestmentManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.ArrayList;

public class GungFriendFragment extends AbsFragment {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;

    //列表
    private StickyListHeadersListView listView;
    private FriendListHeadersAdapter mAdapter;

    private ArrayList<String> mList;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        dialogDismiss();
        initListView();

//        if (resposne.isSuccess()) {
//            if (what == INIT_LIST) {
//                mList = (ArrayList<MainInvActResult>) resposne.getData();
//            } else {
//                if (mList == null) {
//                    mList = new ArrayList<MainInvActResult>();
//                }
//                mList.addAll((ArrayList<MainInvActResult>) resposne.getData());
//            }
//            mAdapter.refresh(mList);
//        } else {
//            CommonUtil.showToast(resposne.getErrorTip());
//        }
//        mListView.onRefreshComplete();
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
        listView = (StickyListHeadersListView) getView().findViewById(R.id.stickylistheaders_listview);

        View ll_listEmpty = getView().findViewById(R.id.ll_listEmpty);
        listView.setEmptyView(ll_listEmpty);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            listView.setFitsSystemWindows(true);
        }

        dialogShow();
        getDataList();
    }

    private void initListView() {

        testData();

        FriendListHeadersAdapter mAdapter = new FriendListHeadersAdapter(getActivity(), mList);

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
        int startIndex = mList == null || mList.size() <= 0 ? 1 : mList.size();
        manager.LoginAct(getActivity(), "", "" + COUNT_MAX, activityHandler, startIndex == 1 ? INIT_LIST : LOAD_DATA);
    }

    @Override
    public void scrollToTop() {
    }

    private void testData() {
        if (mList == null) {
            mList = new ArrayList<String>();
        }
        mList.add("A");
        mList.add("B");
        mList.add("C");
        mList.add("D");
        mList.add("E");
        mList.add("A");
        mList.add("B");
        mList.add("C");
        mList.add("D");
        mList.add("E");
        mList.add("A");
        mList.add("B");
        mList.add("C");
        mList.add("D");
        mList.add("E");
    }

}
