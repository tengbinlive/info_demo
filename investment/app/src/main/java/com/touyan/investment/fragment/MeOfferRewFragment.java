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
import com.touyan.investment.activity.MeInfoActivity;
import com.touyan.investment.activity.MeOfferRewardActivity;
import com.touyan.investment.activity.OfferDetailActivity;
import com.touyan.investment.adapter.InvOfferAdapter;
import com.touyan.investment.bean.main.InvOfferBean;
import com.touyan.investment.bean.main.InvOfferListResult;
import com.touyan.investment.bean.main.MyPartakeOfferListResult;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class MeOfferRewFragment extends AbsFragment {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private InvOfferAdapter mAdapter;

    private ArrayList<InvOfferBean> mList;

    private boolean isInit = false;

    private int currentItemIndex;

    private int viewType;//根据这个类型去判断调用那个接口。
    public static MeOfferRewFragment newsInstance( int viewType)
    {
        MeOfferRewFragment meInfoFragment = new MeOfferRewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt( "viewType", viewType );
        meInfoFragment.setArguments( bundle );
        return meInfoFragment;
    }

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    if (viewType == MeOfferRewardActivity.REWARD_MYRELEASE){
                        loadData1((CommonResponse) msg.obj, what);
                    }else if (viewType == MeOfferRewardActivity.REWARD_MYPARTAKE){
                        loadData2((CommonResponse) msg.obj, what);
                    }

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


    private void loadData2(CommonResponse resposne, int what) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            if (what == INIT_LIST) {
                MyPartakeOfferListResult result = (MyPartakeOfferListResult) resposne.getData();
                mList = result.getRetRewards();
            } else {
                if (mList == null) {
                    mList = new ArrayList<InvOfferBean>();
                }
                mList.addAll(((MyPartakeOfferListResult) resposne.getData()).getRetRewards());
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
        viewType = getArguments().getInt( "viewType" );
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
        if (viewType == MeOfferRewardActivity.REWARD_MYRELEASE){
            manager.myReleaseOfferList(getActivity(), startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
        }else if (viewType == MeOfferRewardActivity.REWARD_MYPARTAKE){
            manager.myPartakeOfferList(getActivity(), startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
        }
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
        }
    }


}
