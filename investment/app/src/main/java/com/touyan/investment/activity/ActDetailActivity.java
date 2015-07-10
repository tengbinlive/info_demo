package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.core.CommonResponse;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.SignGridAdapter;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.MGridView;

import java.util.ArrayList;

public class ActDetailActivity extends AbsDetailActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private final static String[] signs = new String[]{"道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然", "道法自然"};

    private MGridView mGridView;

    private SignGridAdapter mAdapter;

    private InvestmentManager manager = new InvestmentManager();

    private LinearLayout review_ly;

    //列表
    private PullToRefreshScrollView mScrollView;

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
        if (what == INIT_LIST) {
            review_ly.removeAllViews();
            addTestData();
        } else {
            addTestData();
        }
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
        mScrollView.onRefreshComplete();
    }

    private void addTestData() {
        review_ly.addView(getReView());
        review_ly.addView(getReView());
        review_ly.addView(getReView());
        review_ly.addView(getReView());
        review_ly.addView(getReView());
    }

    private LinearLayout getReView() {
        LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.item_inv_review, review_ly, false);
        return custom_ly;
    }

    @Override
    public void EInit() {
        super.EInit();
        findView();
        initGridView();
        initmScrollView();
        getDataList(INIT_LIST);
    }

    private void initmScrollView() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataList(INIT_LIST);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataList(LOAD_DATA);
            }
        });
    }

    private void getDataList(int what) {
        manager.LoginAct(this, "", "" + COUNT_MAX, activityHandler, what);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_act_detail;
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.act_detail);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRight(R.drawable.detail_share);
    }

    private void findView() {
        mGridView = (MGridView) findViewById(R.id.mgridview);
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
        review_ly = (LinearLayout) findViewById(R.id.review_ly);

        LinearLayout scrollview_ly = (LinearLayout) findViewById(R.id.scrollview_ly);

        setOnMenuButtonClick(new OnMenuButtonClick() {
            @Override
            public void onClick(View view, BottomMenu menu, boolean status) {
            }
        });

        initWebView("http://121.40.50.223:8080/investors/info/0cc81dce8232333cbb3790efcc720c22.html");

        scrollview_ly.addView(webview_ly, 0);
    }

    private void initGridView() {

        mAdapter = new SignGridAdapter(this, signs);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mGridView);

        mGridView.setAdapter(animationAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toSignDetail();
            }
        });

    }

    private void toSignDetail() {
        Intent mIntent = new Intent(this, SignDetailActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    @Override
    public ArrayList<BottomMenu> getMenuEnums() {
        ArrayList<BottomMenu> menus = new ArrayList<BottomMenu>();
        menus.add(BottomMenu.SHARE);
        menus.add(BottomMenu.REVIEW);
        menus.add(BottomMenu.COLLECT);
        menus.add(BottomMenu.SIGN);
        return menus;
    }

}
