package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.core.CommonResponse;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.R;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class OfferDetailActivity extends AbsDetailActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private InvestmentManager manager = new InvestmentManager();

    //评论列表
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
        TextView adoption_tv = (TextView) custom_ly.findViewById(R.id.adoption_tv);
        adoption_tv.setVisibility(View.VISIBLE);
        return custom_ly;
    }

    @Override
    public void EInit() {
        super.EInit();
        findView();
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
        return R.layout.activity_offer_detail;
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.offer_detail);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRight(R.drawable.detail_share);
    }

    private void findView() {
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
        review_ly = (LinearLayout) findViewById(R.id.review_ly);

        LinearLayout scrollview_ly = (LinearLayout) findViewById(R.id.scrollview_ly);

        setOnMenuButtonClick(new OnMenuButtonClick() {
            @Override
            public void onClick(View view, BottomMenu menu, boolean status) {
            }
        });

        initWebView("http://121.40.50.223:8080/investors/reward/af0c40533a088a9a1bc8517adeded090.html");

        scrollview_ly.addView(webview_ly, 0);
    }

    @Override
    public ArrayList<BottomMenu> getMenuEnums() {
        ArrayList<BottomMenu> menus = new ArrayList<BottomMenu>();
        menus.add(BottomMenu.SHARE);
        menus.add(BottomMenu.COLLECT);
        menus.add(BottomMenu.REPLY);
        return menus;
    }

}
