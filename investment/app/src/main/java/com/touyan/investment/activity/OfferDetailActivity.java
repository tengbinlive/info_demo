package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.*;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.enums.YesOrNoEnum;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.BottomView;

import java.util.ArrayList;

public class OfferDetailActivity extends AbsDetailActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int LOAD_COLLECT = 0x03;//收藏

    private static final int LOAD_ADOPTION = 0x04;//采纳

    private static final int COUNT_MAX = 8;//加载数据最大值

    private int currentPager = 0;

    private boolean isStore = false;

    private View collectView;

    private TextView review_num_tv;

    private TextView adoptTv;

    private BottomView mBottomView;

    private InvestmentManager manager = new InvestmentManager();

    private InvOfferBean invOfferBean;

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
                case LOAD_COLLECT:
                    loadDataCollect((CommonResponse) msg.obj);
                    break;
                case LOAD_ADOPTION:
                    loadDataAdoption((CommonResponse) msg.obj);
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

    private void addReplyLayout(ArrayList<InvReplysBean> replys) {
        if (replys == null) {
            return;
        }
        for (InvReplysBean replysBean : replys) {
            review_ly.addView(getReView(replysBean));
        }
    }

    private void loadDataAdoption(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            adoptTv.setVisibility(View.VISIBLE);
            adoptTv.setText("最佳");
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadDataCollect(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            isStore = true;
            setBackOrTag(2, true);
            View view = collectView.findViewById(R.id.menu_icon);
            if (view != null) {
                Util.viewScaleAnimation(view);
            }
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private LinearLayout getReView(final InvReplysBean replysBean) {
        LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.item_inv_review, review_ly, false);
        UserInfo userInfo = replysBean.getUser();
        TextView name = (TextView) custom_ly.findViewById(R.id.name);
        TextView date = (TextView) custom_ly.findViewById(R.id.date);
        TextView value = (TextView) custom_ly.findViewById(R.id.value);
        TextView adoption_tv = (TextView) custom_ly.findViewById(R.id.adoption_tv);
        SelectableRoundedImageView head = (SelectableRoundedImageView) custom_ly.findViewById(R.id.head);
        String dateStr = DateUtil.ConverToString(replysBean.getRptime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        name.setText(userInfo.getUalias());
        date.setText(dateStr);
        value.setText(replysBean.getContnt());
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), head);
        if (invOfferBean.getAdoptcount() > 0) {
            if (YesOrNoEnum.YES.getCode().equals(replysBean.getIadopt())) {
                adoption_tv.setVisibility(View.VISIBLE);
                adoption_tv.setText("最佳");
            }
        } else if (App.getInstance().getgUserInfo().getServno().equals(invOfferBean.getPubsid())){
            adoption_tv.setVisibility(View.VISIBLE);
            adoption_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adoptTv = (TextView) view;
                    dialogShow(R.string.carrying);
                    manager.loadAdoption(OfferDetailActivity.this, replysBean.getRpuser(), replysBean.getMesgid(), invOfferBean.getAmount(), replysBean.getRepyid(), activityHandler, LOAD_ADOPTION);
                }
            });
        }

        return custom_ly;
    }

    @Override
    public void EInit() {
        invOfferBean = (InvOfferBean) getIntent().getSerializableExtra(KEY);
        super.EInit();
        findView();
        initmScrollView();
        currentPager = 0;
        getDataReplyList(INIT_LIST);
    }

    private void initmScrollView() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currentPager = 0;
                getDataReplyList(INIT_LIST);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDataReplyList(LOAD_DATA);
            }
        });
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

        review_num_tv = (TextView) findViewById(R.id.review_num_tv);

        review_num_tv.setText("回答 " + invOfferBean.getReplyCount());

        LinearLayout scrollview_ly = (LinearLayout) findViewById(R.id.scrollview_ly);

        setOnMenuButtonClick(new OnMenuButtonClick() {
            @Override
            public void onClick(View view, BottomMenu menu, boolean status) {
                if (menu == BottomMenu.SHARE) {
                    selectShare(invOfferBean);
                } else if (menu == BottomMenu.REPLY) {
                    toReview(invOfferBean);
                } else if (menu == BottomMenu.COLLECT) {
                    if (isStore) {
                        View icon = view.findViewById(R.id.menu_icon);
                        if (icon != null) {
                            Util.viewScaleAnimation(icon);
                        }
                        return;
                    }
                    collectView = view;
                    getCollect();
                }
            }
        });

        initWebView(invOfferBean.getH5url());

        scrollview_ly.addView(webview_ly, 0);
    }

    private void getDataReplyList(int what) {
        manager.queryReplys(this, invOfferBean.getRewdid(), currentPager, COUNT_MAX, activityHandler, what);
    }

    private void getCollect() {
        manager.storeMsg(this, invOfferBean.getRewdid(), InvCollectParam.TYPE_INFO, activityHandler, LOAD_COLLECT);
    }

    private void toReview(InvOfferBean bean) {
        Intent mIntent = new Intent(this, InvReviewActivity.class);
        mIntent.putExtra(KEY, bean.getRewdid());
        mIntent.putExtra(InvReviewActivity.KEY_TYPE, InvReViewParam.TYPE_OFFER);
        startActivity(mIntent);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void selectShare(InvOfferBean bean) {
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
    public ArrayList<BottomMenu> getMenuEnums() {
        ArrayList<BottomMenu> menus = new ArrayList<BottomMenu>();
        menus.add(BottomMenu.SHARE);
        menus.add(BottomMenu.COLLECT);
        menus.add(BottomMenu.REPLY);
        return menus;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUSETCODE && null != data) {
            InvReplysBean replysBean = (InvReplysBean) data.getSerializableExtra(KEY);
            if (null != replysBean) {
                if (currentPager <= 0) {
                    review_ly.addView(getReView(replysBean));
                } else {
                    review_ly.addView(getReView(replysBean), 0);
                }
                int replyNum = invOfferBean.getReplyCount() + 1;
                invOfferBean.setReplyCount(replyNum);
                review_num_tv.setText("回答 " + replyNum);
                Intent intent = new Intent();
                intent.putExtra(KEY, invOfferBean);
                setResult(REQUSETCODE, intent);
                currentPager++;
            }
        }
    }

}
