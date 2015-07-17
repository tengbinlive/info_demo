package com.touyan.investment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.Log;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.UserCollectActivity;
import com.touyan.investment.adapter.CollectedInvInfoAdapter;
import com.touyan.investment.adapter.EditerAdapter;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.bean.main.InvInfoResult;
import com.touyan.investment.manager.UserManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public class CollectedInvInfoFragment extends AbsFragment {
    private UserManager manager = new UserManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 5;//加载数据最大值

    private LayoutInflater mInflater;

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private CollectedInvInfoAdapter mAdapter;

    private ArrayList<InvInfoBean> mList;

    public int currentItemIndex;

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
        if (resposne.isSuccess()) {
            if (what == INIT_LIST) {
                InvInfoResult result = (InvInfoResult) resposne.getData();
                mList = result.getInfos();
            } else {
                if (mList == null) {
                    mList = new ArrayList<InvInfoBean>();
                }
                mList.addAll(((InvInfoResult) resposne.getData()).getInfos());
            }
            mAdapter.refresh(mList);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mListView.onRefreshComplete();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        return mInflater.inflate(R.layout.fragment_investment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    // 初始化资源
    private void init() {
        mListView = (PullToRefreshListView) getView().findViewById(R.id.pull_refresh_list);
        initListView();
        dialogShow();
        getDataList();
    }

    private void initListView() {

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList = null;
                getDataList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataList();
            }
        });

        mActualListView = mListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(mActualListView);

        mAdapter = new CollectedInvInfoAdapter(CollectedInvInfoFragment.this, mList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        View ll_listEmpty = getView().findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);
    }


    private void getDataList() {
        int startIndex = mList == null || mList.size() <= 0 ? 0 : mList.size();
        manager.queryCollectedInfos(getActivity(), startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
    }

    @Override
    public void scrollToTop() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AbsDetailActivity.REQUSETCODE && null != data) {
            InvInfoBean bean = (InvInfoBean) data.getSerializableExtra(KEY);
            mList.set(currentItemIndex, bean);
            mAdapter.refresh(mList);
        }

        if (requestCode == UserCollectActivity.EDIT_STATE_CHENGED) {
            switch (resultCode) {
                case EditerAdapter.STATE_REMOVE:
                    mAdapter.updateEditState(EditerAdapter.STATE_REMOVE);

                    break;
                case EditerAdapter.STATE_COMPLETE:
                    mAdapter.updateEditState(EditerAdapter.STATE_COMPLETE);

                    break;
                case EditerAdapter.STATE_EDIT:
                    mAdapter.updateEditState(EditerAdapter.STATE_EDIT);

                    break;
            }

        }
    }


}
