package com.touyan.investment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.ActDetailActivity;
import com.touyan.investment.activity.OfferDetailActivity;
import com.touyan.investment.adapter.InvActAdapter;
import com.touyan.investment.adapter.InvOfferAdapter;
import com.touyan.investment.bean.main.MainInvActResult;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class InvOfferFragment extends AbsFragment {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private InvOfferAdapter mAdapter;

    private ArrayList<MainInvActResult> mList;

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
        testData();
        if (resposne.isSuccess()) {
            if (what == INIT_LIST) {
                mList = (ArrayList<MainInvActResult>) resposne.getData();
            } else {
                if (mList == null) {
                    mList = new ArrayList<MainInvActResult>();
                }
                mList.addAll((ArrayList<MainInvActResult>) resposne.getData());
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
        return mInflater.inflate(R.layout.fragment_investment_offer, container, false);
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

        mAdapter = new InvOfferAdapter(getActivity(), mList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toOfferDetail();
            }
        });

        View ll_listEmpty = getView().findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);

    }

    private void toOfferDetail() {
        Intent mIntent = new Intent(getActivity(), OfferDetailActivity.class);
        startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void getDataList() {
        int startIndex = mList == null || mList.size() <= 0 ? 1 : mList.size();
        manager.LoginAct(getActivity(), "", "" + COUNT_MAX, activityHandler, startIndex == 1 ? INIT_LIST : LOAD_DATA);
    }

    private void testData(){
        if(mList==null) {
            mList = new ArrayList<MainInvActResult>();
        }
        mList.add(new MainInvActResult());
        mList.add(new MainInvActResult());
        mList.add(new MainInvActResult());
        mList.add(new MainInvActResult());
        mList.add(new MainInvActResult());
        mList.add(new MainInvActResult());
        mList.add(new MainInvActResult());
        mAdapter.refresh(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void scrollToTop() {
    }

}