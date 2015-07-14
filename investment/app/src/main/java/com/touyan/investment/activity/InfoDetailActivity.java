package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.joooonho.SelectableRoundedImageView;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.bean.main.InvReplysBean;
import com.touyan.investment.bean.main.InvReplysResult;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class InfoDetailActivity extends AbsDetailActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 5;//加载数据最大值

    private int currentPager = 0;

    private InvInfoBean invInfoBean;

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
        if (resposne.isSuccess()) {
            InvReplysResult replysResult = (InvReplysResult) resposne.getData();
            if (what == INIT_LIST) {
                currentPager = 0;
                review_ly.removeAllViews();
            } else {
                currentPager += COUNT_MAX;
            }
            addData(replysResult.getReplys());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mScrollView.onRefreshComplete();
    }

    private void addData(ArrayList<InvReplysBean> replys) {
        if (replys == null) {
            return;
        }
        for (InvReplysBean replysBean : replys) {
            review_ly.addView(getReView(replysBean));
        }
    }

    private LinearLayout getReView(InvReplysBean replysBean) {
        LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.item_inv_review, review_ly, false);
        TextView name = (TextView) custom_ly.findViewById(R.id.name);
        TextView date = (TextView) custom_ly.findViewById(R.id.date);
        TextView value = (TextView) custom_ly.findViewById(R.id.value);
        SelectableRoundedImageView head = (SelectableRoundedImageView) custom_ly.findViewById(R.id.head);
        String dateStr = DateUtil.ConverToString(replysBean.getRptime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        date.setText(dateStr);
        return custom_ly;
    }

    @Override
    public void EInit() {
        invInfoBean = (InvInfoBean) getIntent().getSerializableExtra(KEY_DETAIL);
        super.EInit();
        findView();
        initmScrollView();
        getDataList(0);
    }

    private void initmScrollView() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataList(currentPager);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataList(currentPager);
            }
        });
    }

    private void getDataList(int currentPager) {
        int what = currentPager <= 0 ? INIT_LIST : LOAD_DATA;
        manager.queryReplys(this, invInfoBean.getInfoid(), currentPager, COUNT_MAX, activityHandler, what);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_info_detail;
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.info_detail);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRight(R.drawable.detail_share);
    }

    private void findView() {
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
        review_ly = (LinearLayout) findViewById(R.id.review_ly);
        TextView review_title = (TextView) findViewById(R.id.review_title);
        TextView offer_title = (TextView) findViewById(R.id.offer_title);
        review_title.setText("评论 " + invInfoBean.getReplyNum());
        offer_title.setText("已打赏" + invInfoBean.getRewardsAmount() + "金币");
        if (InvInfoBean.PUBL_NO.equals(invInfoBean.getIspubl())) {
            ViewStub stub = (ViewStub) findViewById(R.id.info_detail_stub);
            stub.inflate();
            TextView title = (TextView) findViewById(R.id.title);
            TextView title_charge = (TextView) findViewById(R.id.title_charge);
            title.setText(invInfoBean.getItitle());
            title_charge.setText("资讯加密了，需要支付" + invInfoBean.getCharge() + "金币才能查看哦 ！");
            Button unpacking_bt = (Button) findViewById(R.id.unpacking_bt);
            unpacking_bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmDialog(InfoDetailActivity.this, "点击确定将支付" + invInfoBean.getCharge() + "金币", "取消", new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogDismiss();
                        }
                    }, "确定", new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogDismiss();
                        }
                    });
                }
            });

        } else {
            initWebView(invInfoBean.getH5url());
        }

        setOnMenuButtonClick(new OnMenuButtonClick() {
            @Override
            public void onClick(View view, BottomMenu menu, boolean status) {
                if (menu == BottomMenu.REWARD) {
                    toInfoReward();
                }
            }
        });
    }

    @Override
    public ArrayList<BottomMenu> getMenuEnums() {
        ArrayList<BottomMenu> menus = new ArrayList<BottomMenu>();
        menus.add(BottomMenu.SHARE);
        menus.add(BottomMenu.REVIEW);
        menus.add(BottomMenu.COLLECT);
        menus.add(BottomMenu.REWARD);
        return menus;
    }

    private void toInfoReward() {
        Intent mIntent = new Intent(this, InfoRewardActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }
}
