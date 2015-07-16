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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.*;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.enums.YesOrNoEnum;
import com.touyan.investment.fragment.InvInfoFragment;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.BottomView;

import java.util.ArrayList;

public class InfoDetailActivity extends AbsDetailActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int LOAD_DETAIL = 0x04;//详情

    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int LOAD_COLLECT = 0x03;//收藏

    private static final int LOAD_BUY_INFO = 0x05;//收藏

    private static final int COUNT_MAX = 8;//加载数据最大值

    private int currentPager = 0;

    private InvInfoBean invInfoBean;

    private InvInfoDetailResult infoDetailResult;

    private InvestmentManager manager = new InvestmentManager();

    private LinearLayout review_ly;

    private BottomView mBottomView;

    private View collectView;

    private LinearLayout scrollview_ly;

    private ViewStub stub;

    private TextView reward_title;

    //列表
    private PullToRefreshScrollView mScrollView;

    private boolean isStore = false;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                case LOAD_COLLECT:
                    loadDataCollect((CommonResponse) msg.obj);
                    break;
                case LOAD_DETAIL:
                    loadDataDetail((CommonResponse) msg.obj);
                    break;
                case LOAD_BUY_INFO:
                    loadDataBuyInfo((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        if (resposne.isSuccess()) {
            InvReplysResult replysResult = (InvReplysResult) resposne.getData();
            if (what == INIT_LIST) {
                currentPager = 0;
                review_ly.removeAllViews();
            } else {
                currentPager += COUNT_MAX;
            }
            addReplyLayout(replysResult.getReplys());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mScrollView.onRefreshComplete();
    }

    private void loadDataCollect(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            isStore = true;
            setBackOrTag(2, true);
            View view  = collectView.findViewById(R.id.menu_icon);
            if(view!=null) {
                Util.viewScaleAnimation(view);
            }
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadDataDetail(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            infoDetailResult= (InvInfoDetailResult) resposne.getData();
            initData();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadDataBuyInfo(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            scrollview_ly = (LinearLayout) findViewById(R.id.scrollview_ly);
            stub.setVisibility(View.GONE);
            initWebView(invInfoBean.getH5url());
            scrollview_ly.addView(webview_ly, 0);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void addReplyLayout(ArrayList<InvReplysBean> replys) {
        if (replys == null) {
            return;
        }
        for (InvReplysBean replysBean : replys) {
            review_ly.addView(getReView(replysBean));
        }
    }

    private LinearLayout getReView(InvReplysBean replysBean) {
        LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.item_inv_review, review_ly, false);
        UserInfo userInfo = replysBean.getUser();
        TextView name = (TextView) custom_ly.findViewById(R.id.name);
        TextView date = (TextView) custom_ly.findViewById(R.id.date);
        TextView value = (TextView) custom_ly.findViewById(R.id.value);
        SelectableRoundedImageView head = (SelectableRoundedImageView) custom_ly.findViewById(R.id.head);
        String dateStr = DateUtil.ConverToString(replysBean.getRptime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        name.setText(userInfo.getUalias());
        date.setText(dateStr);
        value.setText(replysBean.getContnt());
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), head);
        return custom_ly;
    }

    @Override
    public void EInit() {
        invInfoBean = (InvInfoBean) getIntent().getSerializableExtra(KEY_DETAIL);
        super.EInit();
        findView();
        initmScrollView();
        getDetail();
        getDataReplyList(INIT_LIST);
    }
    
    private void initData(){
        if(YesOrNoEnum.YES.getCode().equals(infoDetailResult.getIsStore())) {
            isStore = true;
            setBackOrTag(2, true);
        }
        TextView review_title = (TextView) findViewById(R.id.review_title);
        reward_title = (TextView) findViewById(R.id.reward_title);
        review_title.setText("评论 " + infoDetailResult.getReplyCount());
        reward_title.setText("已打赏" + invInfoBean.getRewardsAmount() + "金币");
        if (YesOrNoEnum.YES.equals(infoDetailResult.getIsBuy())) {
            stub = (ViewStub) findViewById(R.id.info_detail_stub);
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
                            dialogShow("正在购买");
                            getBuyInfo();
                        }
                    });
                }
            });

        } else {
            scrollview_ly = (LinearLayout) findViewById(R.id.scrollview_ly);
            initWebView(invInfoBean.getH5url());
            scrollview_ly.addView(webview_ly, 0);
        }

        setOnMenuButtonClick(new OnMenuButtonClick() {
            @Override
            public void onClick(View view, BottomMenu menu, boolean status) {
                if (menu == BottomMenu.REWARD) {
                    toInfoReward(invInfoBean);
                } else if (menu == BottomMenu.SHARE) {
                    selectPict(invInfoBean);
                } else if (menu == BottomMenu.REVIEW) {
                    toReview(invInfoBean);
                }else if (menu == BottomMenu.COLLECT) {
                    if(isStore){
                        View icon  = view.findViewById(R.id.menu_icon);
                        if(icon!=null) {
                            Util.viewScaleAnimation(icon);
                        }
                        return;
                    }
                    collectView = view;
                    getCollect();
                }
            }
        });
    }

    private void initmScrollView() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataReplyList(INIT_LIST);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataReplyList(LOAD_DATA);
            }
        });
    }

    private void getDataReplyList(int what) {
        manager.queryReplys(this, invInfoBean.getInfoid(), currentPager, COUNT_MAX, activityHandler, what);
    }

    private void getDetail() {
        dialogShow();
        manager.queryInfoDetail(this, invInfoBean.getInfoid(), activityHandler, LOAD_DETAIL);
    }

    private void getCollect() {
        manager.storeMsg(this,invInfoBean.getInfoid(),InvCollectParam.TYPE_INFO, activityHandler, LOAD_COLLECT);
    }

    private void getBuyInfo() {
        manager.buyInfo(this, invInfoBean.getInfoid(), invInfoBean.getCharge(), activityHandler, LOAD_BUY_INFO);
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

    private void toInfoReward(InvInfoBean bean) {
        Intent mIntent = new Intent(this, InfoRewardActivity.class);
        mIntent.putExtra(KEY,bean);
        startActivityForResult(mIntent,InvInfoFragment.REQUSETCODE);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void toReview(InvInfoBean invInfoBean) {
        Intent mIntent = new Intent(this, InvReviewActivity.class);
        mIntent.putExtra(KEY,invInfoBean.getInfoid());
        mIntent.putExtra(InvReviewActivity.KEY_TYPE, InvReViewParam.TYPE_INFO);
        startActivity(mIntent);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void selectPict(InvInfoBean invInfoBean) {
        if (mBottomView != null) {
            mBottomView.showBottomView(true);
            return;
        }
        mBottomView = new BottomView(this, R.style.BottomViewTheme_Defalut, R.layout.bottom_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);
        TextView shareFriend = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_2);
        TextView shareGroup = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_3);
        TextView cancel = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_cancel);

        ShareButtonOnClickListener listener = new ShareButtonOnClickListener();
        shareFriend.setOnClickListener(listener);
        shareGroup.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        mBottomView.showBottomView(true);
    }

    class ShareButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_tv_2:
                case R.id.bottom_tv_3:
                    break;
                case R.id.bottom_tv_cancel:
                    mBottomView.dismissBottomView();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == InvInfoFragment.REQUSETCODE&&null!=data) {
            Double amount = data.getDoubleExtra(KEY,invInfoBean.getRewardsAmount());
            reward_title.setText("已打赏" + amount + "金币");
        }
    }

}
