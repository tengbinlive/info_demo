package com.touyan.investment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.core.CommonResponse;
import com.core.openapi.OpenApiSimpleResult;
import com.core.util.CommonUtil;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.ActDetailActivity;
import com.touyan.investment.activity.MeActActivity;
import com.touyan.investment.adapter.EditerAdapter;
import com.touyan.investment.adapter.MyRaleaseActAdapter;
import com.touyan.investment.bean.main.InvActBean;
import com.touyan.investment.bean.main.InvActListResult;
import com.touyan.investment.bean.main.MyActListResult;
import com.touyan.investment.bean.main.MyPartakeActListResult;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;
import java.util.List;

public class MeActivityFragment extends AbsFragment {

    public final static int REWARD_MYRELEASE = 0;//我发布的
    public final static int REWARD_MYPARTAKE = REWARD_MYRELEASE + 1;//我参与的

    private InvestmentManager manager = new InvestmentManager();

   // public final  static int REQUSETCODE = 1;

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理
    private static final int DELETE_COMPLETE = 0X03;
    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private MyRaleaseActAdapter mAdapter;

    //private BGABanner mBanner;

    private ArrayList<InvActBean> mList;

    private boolean isInit = false;

    private int currentIndex;

    private int viewType;//根据这个类型去判断调用那个接口。
    private ArrayList<Integer> checkedItems;
    private String userID;

    public static MeActivityFragment newsInstance( int viewType,String userID)
    {
        MeActivityFragment meActivityFragment = new MeActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt( "viewType", viewType );
        bundle.putString("userID", userID);
        meActivityFragment.setArguments( bundle );
        return meActivityFragment;
    }

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                case DELETE_COMPLETE:
                    deleteComplete((CommonResponse) msg.obj);
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
                MyActListResult result  = (MyActListResult) resposne.getData();
                mList = result.getActivitys();
            } else {
                if (mList == null) {
                    mList = new ArrayList<InvActBean>();
                }
                mList.addAll(((MyActListResult) resposne.getData()).getActivitys());
            }
            mAdapter.refresh(mList);
        } else {
            //避免第一次应用启动时 创建fragment加载数据多次提示
            if(isInit) {
                CommonUtil.showToast(resposne.getErrorTip());
            }else {
                isInit = true;
            }
        }
        mListView.onRefreshComplete();
    }
    private void deleteComplete(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            OpenApiSimpleResult result = (OpenApiSimpleResult) resposne.getData();

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
        viewType = getArguments().getInt( "viewType" );
        userID = getArguments().getString("userID");
        return mInflater.inflate(R.layout.fragment_investment_act, container, false);
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
                dialogShow();
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

        mAdapter = new MyRaleaseActAdapter(getActivity(), mList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentIndex  = i-1;
                          toActDetail(mList.get(currentIndex));
            }
        });

        View ll_listEmpty = getView().findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);
    }

    private void toActDetail(InvActBean bean) {
        Intent mIntent = new Intent(getActivity(), ActDetailActivity.class);
        mIntent.putExtra(ActDetailActivity.KEY_DETAIL,bean);
        startActivityForResult(mIntent, AbsDetailActivity.REQUSETCODE);
        getActivity().overridePendingTransition(R.anim.push_translate_in_right, 0);
    }


    private void getDataList() {
        int startIndex = mList == null || mList.size() <= 0 ? 0 : mList.size();
        manager.myReleaseActList(getActivity(),userID, startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AbsDetailActivity.REQUSETCODE) {
            mList.get(currentIndex).setIsJoin(InvActBean.STATUS_BY);
            mAdapter.refresh(mList);
        }else if(resultCode == RECODE_RELEASE && null != data){
            InvActBean bean = (InvActBean) data.getSerializableExtra(KEY);
            int size = mList.size();
            if(size<=0){
                mList.add(bean);
            }else{
                mList.add(0,bean);
            }
            mAdapter.refresh(mList);
        }

        if (requestCode == MeActActivity.EDIT_STATE_CHENGED) {
            switch (resultCode) {
                case EditerAdapter.STATE_REMOVE:
                    mAdapter.updateEditState(EditerAdapter.STATE_EDIT);
                    checkedItems = mAdapter.checkedItemList;
                    manager.deletemyReleaseAct(getActivity(), mAdapter.getIdList(), activityHandler, DELETE_COMPLETE);
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

    @Override
    public void scrollToTop() {
    }

}
