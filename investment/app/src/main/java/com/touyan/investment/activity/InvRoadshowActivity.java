package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InvActAdapter;
import com.touyan.investment.bean.main.InvActBean;
import com.touyan.investment.bean.main.InvActListResult;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class InvRoadshowActivity extends AbsActivity {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 5;//加载数据最大值

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private InvActAdapter mAdapter;

    private ArrayList<InvActBean> mList;

    private int currentIndex;

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
                InvActListResult result = (InvActListResult) resposne.getData();
                mList = result.getActives();
            } else {
                if (mList == null) {
                    mList = new ArrayList<InvActBean>();
                }
                mList.addAll(((InvActListResult) resposne.getData()).getActives());
            }
            mAdapter.refresh(mList);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mListView.onRefreshComplete();
    }


    @Override
    public void EInit() {
        super.EInit();
        init();
    }

    // 初始化资源
    private void init() {
        mListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        initListView();
        dialogShow();
        getDataList();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_filter;
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.act_release_roadshow);
    }

    @Override
    public void onResume() {
        super.onResume();
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

        mAdapter = new InvActAdapter(this, mList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentIndex  = i-2;
                toActDetail(mList.get(currentIndex));
            }
        });

        View ll_listEmpty = findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);
    }

    private void toActDetail(InvActBean bean) {
        Intent mIntent = new Intent(this, ActDetailActivity.class);
        mIntent.putExtra(ActDetailActivity.KEY_DETAIL,bean);
        startActivityForResult(mIntent, AbsDetailActivity.REQUSETCODE);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void getDataList() {
        int startIndex = mList == null || mList.size() <= 0 ? 0 : mList.size();
        manager.actList(this,InvActBean.TYPE_ROADSHOW, startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AbsDetailActivity.REQUSETCODE) {
            mList.get(currentIndex).setIsJoin(InvActBean.STATUS_BY);
            mAdapter.refresh(mList);
        }
    }

}
