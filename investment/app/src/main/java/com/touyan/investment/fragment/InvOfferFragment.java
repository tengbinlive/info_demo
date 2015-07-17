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
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.OfferDetailActivity;
import com.touyan.investment.adapter.InvOfferAdapter;
import com.touyan.investment.bean.main.InvActBean;
import com.touyan.investment.bean.main.InvOfferBean;
import com.touyan.investment.bean.main.InvOfferListResult;
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

    private ArrayList<InvOfferBean> mList = new ArrayList<InvOfferBean>();

    private boolean isInit = false;

    private int currentItemIndex;

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
                InvOfferListResult result = (InvOfferListResult) resposne.getData();
                mList = result.getRewards();
            } else {
                if (mList == null) {
                    mList = new ArrayList<InvOfferBean>();
                }
                mList.addAll(((InvOfferListResult) resposne.getData()).getRewards());
            }
            mAdapter.refresh(mList);
        } else {
            //避免第一次应用启动时 创建fragment加载数据多次提示
            if (isInit) {
                CommonUtil.showToast(resposne.getErrorTip());
            } else {
                isInit = true;
            }
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
                currentItemIndex = i-1;
                toOfferDetail(mList.get(currentItemIndex));
            }
        });

        View ll_listEmpty = getView().findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);

    }

    private void toOfferDetail(InvOfferBean bean) {
        Intent mIntent = new Intent(getActivity(), OfferDetailActivity.class);
        mIntent.putExtra(KEY, bean);
        startActivityForResult(mIntent, AbsDetailActivity.REQUSETCODE);
        getActivity().overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void getDataList() {
        int startIndex = mList == null || mList.size() <= 0 ? 0 : mList.size();
        manager.offerList(getActivity(), startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
    }

    @Override
    public void scrollToTop() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AbsDetailActivity.REQUSETCODE &&null!=data) {
            InvOfferBean bean = (InvOfferBean) data.getSerializableExtra(KEY);
            mList.set(currentItemIndex, bean);
            mAdapter.refresh(mList);
        }else if(resultCode == RECODE_RELEASE && null != data){
            InvOfferBean bean = (InvOfferBean) data.getSerializableExtra(KEY);
            int size = mList.size();
            if(size<=0){
                mList.add(bean);
            }else{
                mList.add(0,bean);
            }
            mAdapter.refresh(mList);
        }
    }


}
