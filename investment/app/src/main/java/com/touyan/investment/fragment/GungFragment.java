package com.touyan.investment.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.ActDetailActivity;
import com.touyan.investment.activity.FriendsActivity;
import com.touyan.investment.adapter.RecommendNewsAdapter;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.manager.MessageManager;
import com.touyan.investment.mview.BezierView;

import java.util.ArrayList;

public class GungFragment extends AbsFragment {


    private MessageManager manager = new MessageManager();

    private LayoutInflater mInflater;

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private RecommendNewsAdapter mAdapter;
    private SwingBottomInAnimationAdapter animationAdapter;

    private BezierView bezierview;

    private ArrayList<String> mArrayList;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne) {
        dialogDismiss();
        testData();
//        if (resposne.isSuccess()) {
//            if (what == INIT_LIST) {
//                review_ly.removeAllViews();
//                addTestData();
//             } else {
//                addTestData();
//            }
//        } else {
//            CommonUtil.showToast(resposne.getErrorTip());
//        }
        mListView.onRefreshComplete();
    }

    private void testData() {
        mArrayList = new ArrayList<String>();
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mAdapter.refresh(mArrayList);
        animationAdapter.reset();
    }


    private void initListView(View viewGroup) {

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataList();
            }
        });

        mActualListView = mListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(mActualListView);

        mAdapter = new RecommendNewsAdapter(getActivity(), mArrayList);

        animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        View ll_listEmpty = viewGroup.findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);
    }

    private void getDataList() {
        manager.topMessage(getActivity(), "", "" + COUNT_MAX, activityHandler, INIT_LIST);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        View viewGroup = mInflater.inflate(R.layout.fragment_gung, container, false);
        init(viewGroup);
        initActionBar(viewGroup);
        return viewGroup;
    }

    // 初始化资源
    private void init(View viewGroup) {
        mListView = (PullToRefreshListView) viewGroup.findViewById(R.id.pull_refresh_list);
        initListView(viewGroup);
        getDataList();
    }

    public void initActionBar(View viewGroup) {
        bezierview = (BezierView) viewGroup.findViewById(R.id.bezierview);
        TextView menuLeft = (TextView) viewGroup.findViewById(R.id.toolbar_left_btn);
        TextView toolbar_right_tv = (TextView) viewGroup.findViewById(R.id.toolbar_right_tv);
        TextView toolbar_intermediate_tv = (TextView) viewGroup.findViewById(R.id.toolbar_intermediate_tv);
        RelativeLayout menuRight = (RelativeLayout) viewGroup.findViewById(R.id.toolbar_right_btn);

        toolbar_intermediate_tv.setText(R.string.main_gung);
        menuRight.setVisibility(View.VISIBLE);
        toolbar_right_tv.setVisibility(View.VISIBLE);
        setToolbarMenuAnchor(menuLeft, R.drawable.notice_nomral, AbsActivity.LEFT);
        setToolbarMenuAnchor(toolbar_right_tv, R.drawable.group_icon, AbsActivity.RIGHT);
        menuLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                bezierview.setNewMessage("2");
            }
        });

        menuRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                toFriends();
            }
        });
    }

    private void toFriends() {
        Intent mIntent = new Intent(getActivity(), FriendsActivity.class);
        startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    public void setToolbarMenuAnchor(TextView view, int iconid, int anchor) {
        Drawable drawable = getResources().getDrawable(iconid);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (anchor == AbsActivity.TOP) {
            view.setCompoundDrawables(null, drawable, null, null);
        } else if (anchor == AbsActivity.BOTTOM) {
            view.setCompoundDrawables(null, null, null, drawable);
        } else if (anchor == AbsActivity.LEFT) {
            view.setCompoundDrawables(drawable, null, null, null);
        } else if (anchor == AbsActivity.RIGHT) {
            view.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @Override
    public void scrollToTop() {
    }
}
