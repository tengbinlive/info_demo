package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.core.CommonResponse;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.HotGroupRecomAdapter;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.message.QueryHotGroupsResult;
import com.touyan.investment.bean.user.MayknowFriendResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.manager.MessageManager;

import java.util.ArrayList;

public class HotGroupRecoActivity extends AbsActivity{

    private MessageManager manager = new MessageManager();
    private static final int LOAD_DATA = 0x02;//加载数据处理
    //列表
    private ListView mListView;
    private HotGroupRecomAdapter mAdapter;

    private ArrayList<GroupDetail> mList = new ArrayList<GroupDetail>();

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void EInit() {
        super.EInit();
        initListView();
        dialogShow();
        getDataList();
    }

    private void loadData(CommonResponse resposne, int what) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            QueryHotGroupsResult queryHotGroupsResult = (QueryHotGroupsResult) resposne.getData();
            mList.addAll(queryHotGroupsResult.getGroups());
            mAdapter.refresh(mList);
        }
    }
    @Override
    public int getContentView() {
        return R.layout.activity_hotgroup_recom;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.hotgroup);
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initListView() {
        mListView = (ListView)findViewById(R.id.pull_refresh_list);
        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(mListView);

        mAdapter = new HotGroupRecomAdapter(this, mList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mListView);

        mListView.setAdapter(animationAdapter);

        View ll_listEmpty = findViewById(R.id.ll_listEmpty);
        mListView.setEmptyView(ll_listEmpty);
    }

    private void getDataList() {
        manager.queryHotgroups(this, activityHandler, LOAD_DATA);
    }
}
