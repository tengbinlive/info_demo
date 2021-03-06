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
import com.touyan.investment.activity.MeOfferRewardActivity;
import com.touyan.investment.activity.OfferDetailActivity;
import com.touyan.investment.adapter.EditerAdapter;
import com.touyan.investment.adapter.MyOfferAdapter;
import com.touyan.investment.bean.main.InvOfferBean;
import com.touyan.investment.bean.main.InvOfferListResult;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class MeOfferRewFragment extends AbsFragment {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理
    private static final int DELETE_COMPLETE = 0X03;
    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;
    private ArrayList<Integer> checkedItems;
    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private MyOfferAdapter mAdapter;

    private ArrayList<InvOfferBean> mList;

    private String userID;

    private boolean isInit = false;

    private int currentItemIndex;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    loadData1((CommonResponse) msg.obj, what);

                    break;
                case DELETE_COMPLETE:
                    deleteComplete((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData1(CommonResponse resposne, int what) {
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

    private void deleteComplete(CommonResponse resposne) {
        if (resposne.isSuccess()) {

            for (int i = 0; i < checkedItems.size(); i++) {
                int item = checkedItems.get(i);
                mList.remove(item);
            }
            mAdapter.refresh(mList);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        userID = getArguments().getString("userID");
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

        mAdapter = new MyOfferAdapter(getActivity(), mList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentItemIndex = i - 1;
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
        manager.myReleaseOfferList(getActivity(),userID, startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
    }

    @Override
    public void scrollToTop() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AbsDetailActivity.REQUSETCODE && null != data) {
            InvOfferBean bean = (InvOfferBean) data.getSerializableExtra(KEY);
            mList.set(currentItemIndex, bean);
            mAdapter.refresh(mList);
        } else if (resultCode == RECODE_RELEASE && null != data) {
            InvOfferBean bean = (InvOfferBean) data.getSerializableExtra(KEY);
            int size = mList.size();
            if (size <= 0) {
                mList.add(bean);
            } else {
                mList.add(0, bean);
            }
            mAdapter.refresh(mList);
        }

        if (requestCode == MeOfferRewardActivity.EDIT_STATE_CHENGED) {
            switch (resultCode) {
                case EditerAdapter.STATE_REMOVE:
                    mAdapter.updateEditState(EditerAdapter.STATE_EDIT);
                    checkedItems = mAdapter.checkedItemList;
                    manager.deletemyReleaseOffer(getActivity(), mAdapter.getIdList(), activityHandler, DELETE_COMPLETE);
                    break;
                case EditerAdapter.STATE_COMPLETE:
                    mAdapter.updateEditState(EditerAdapter.STATE_EDIT);
                    break;
                case EditerAdapter.STATE_EDIT:
                    mAdapter.updateEditState(EditerAdapter.STATE_COMPLETE);
                    break;
            }
        }
    }


}
