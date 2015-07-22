package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.StringUtil;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.joooonho.SelectableRoundedImageView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.SignGridAdapter;
import com.touyan.investment.bean.main.*;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.enums.YesOrNoEnum;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.BottomView;
import com.touyan.investment.mview.MGridView;

import java.util.ArrayList;

public class ActDetailActivity extends AbsDetailActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int LOAD_DETAIL = 0x04;//详情

    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int LOAD_COLLECT = 0x03;//收藏

    private static final int LOAD_SIGN = 0x05;//报名

    private static final int LOAD_JOIN = 0x06;//人员信息

    private static final int COUNT_MAX = 8;//加载数据最大值

    private BottomView mBottomView;

    private MGridView mGridView;

    private SignGridAdapter mAdapter;

    private InvestmentManager manager = new InvestmentManager();

    private ArrayList<InvActJoinUsersBean> joinUsers;

    private LinearLayout review_ly;

    private InvActBean invActBean;

    private InvActDetailResult invActDetailResult;

    //列表
    private PullToRefreshScrollView mScrollView;

    private boolean isStore = false;

    private boolean isSign = false;

    private View collectView;

    private int currentPager = 0;

    private TextView sign_num_tv;

    private TextView review_num_tv;

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
                case LOAD_SIGN:
                    loadDataSign((CommonResponse) msg.obj);
                    break;
                case LOAD_JOIN:
                    loadDataJoin((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        if (resposne.isSuccess()) {
            InvReplysResult replysResult = (InvReplysResult) resposne.getData();
            ArrayList<InvReplysBean> beans = replysResult.getReplys();
            int size = beans==null?0:beans.size();
            if (what == INIT_LIST) {
                review_ly.removeAllViews();
            }
            currentPager += size;
            addReplyLayout(beans);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mScrollView.onRefreshComplete();
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

    private void loadDataJoin(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            InvActJoinResult result = (InvActJoinResult) resposne.getData();
            sign_num_tv.setText("已报名 " + result.getJionNum());
            initGridView(result.getJoinUsers());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadDataSign(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            setResult(REQUSETCODE);
            isSign = true;
            setBackOrTag(3, true);
            View view = collectView.findViewById(R.id.menu_icon);
            if (view != null) {
                Util.viewScaleAnimation(view);
            }
            getJoinUser();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadDataDetail(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            invActDetailResult = (InvActDetailResult) resposne.getData();
            initData();
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

    private LinearLayout getReView(final InvReplysBean replysBean) {
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
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFansDetailsActivity.toOthersDetail(ActDetailActivity.this, App.getInstance().getgUserInfo().getServno(), replysBean.getUser().getServno());
            }
        });
        return custom_ly;
    }

    @Override
    public void EInit() {
        invActBean = (InvActBean) getIntent().getSerializableExtra(KEY_DETAIL);
        super.EInit();
        findView();
        initmScrollView();
        getDetail();
        getDataReplyList(INIT_LIST);
        getJoinUser();
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

    private void getDataReplyList(int what) {
        if(what==INIT_LIST){
            currentPager = 0;
        }
        manager.queryReplys(this, invActBean.getActvid(), currentPager, COUNT_MAX, activityHandler, what);
    }

    private void getDetail() {
        dialogShow();
        manager.queryActDetail(this, invActBean.getActvid(), activityHandler, LOAD_DETAIL);
    }

    private void getJoinUser() {
        manager.queryActJoinUser(this, invActBean.getActvid(), activityHandler, LOAD_JOIN);
    }

    private void getCollect() {
        manager.storeMsg(this, invActBean.getActvid(), InvCollectParam.TYPE_INFO, activityHandler, LOAD_COLLECT);
    }

    private void getSign() {
        manager.actSign(this, invActBean.getActvid(), "" + invActBean.getCharge(), activityHandler, LOAD_SIGN);
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
        sign_num_tv = (TextView) findViewById(R.id.sign_num_tv);
        review_num_tv = (TextView) findViewById(R.id.review_num_tv);
        mGridView = (MGridView) findViewById(R.id.mgridview);
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
        review_ly = (LinearLayout) findViewById(R.id.review_ly);

        LinearLayout scrollview_ly = (LinearLayout) findViewById(R.id.scrollview_ly);

        String h5url = invActBean.getH5url();
        if(StringUtil.isNotBlank(h5url)) {
            initWebView(h5url);
        }

        scrollview_ly.addView(webview_ly, 0);

        setOnMenuButtonClick(new OnMenuButtonClick() {
            @Override
            public void onClick(View view, BottomMenu menu, boolean status) {
                if (menu == BottomMenu.REWARD) {
                } else if (menu == BottomMenu.SHARE) {
                    selectShare(invActBean);
                } else if (menu == BottomMenu.REVIEW) {
                    toReview(invActBean);
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
                } else if (menu == BottomMenu.SIGN) {
                    if (isSign) {
                        View icon = view.findViewById(R.id.menu_icon);
                        if (icon != null) {
                            Util.viewScaleAnimation(icon);
                        }
                        return;
                    }
                    collectView = view;
                    getSign();
                }else if (menu == BottomMenu.SIGN_DETAIL) {
                    toSignDetail();
                }
            }
        });

        String joinStatus = invActBean.getIsJoin();
        String pubsid = invActBean.getPubsid();
        if (InvActBean.STATUS_BY.equals(joinStatus)&&!pubsid.equals(App.getInstance().getgUserInfo().getServno())) {
            isSign = true;
            setBackOrTag(3, true);
        }

    }

    private void initData() {
        InvActBean bean = invActDetailResult.getDetail();
        String h5url = invActBean.getH5url();
        if(StringUtil.isBlank(h5url)) {
            initWebView(bean.getH5url());
        }
        int replyNum = bean.getReplyCount();
        if (YesOrNoEnum.YES.getCode().equals(bean.getIsStore())) {
            isStore = true;
            setBackOrTag(2, true);
        }
        review_num_tv.setText("评论 " + replyNum);
    }

    private void initGridView(ArrayList<InvActJoinUsersBean> _joinUsers) {

        if (_joinUsers == null || _joinUsers.size() <= 0) {
            return;
        }

        joinUsers = _joinUsers;

        mGridView.setVisibility(View.VISIBLE);

        mAdapter = new SignGridAdapter(this, joinUsers);

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
        if(null==joinUsers||joinUsers.size()<=0){
            CommonUtil.showToast("暂无详情");
            return;
        }
        Intent mIntent = new Intent(this, SignDetailActivity.class);
        mIntent.putExtra(KEY,joinUsers);
        startActivity(mIntent);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    @Override
    public ArrayList<BottomMenu> getMenuEnums() {
        ArrayList<BottomMenu> menus = new ArrayList<BottomMenu>();
        menus.add(BottomMenu.SHARE);
        menus.add(BottomMenu.REVIEW);
        menus.add(BottomMenu.COLLECT);
        if (invActBean.getPubsid().equals(App.getInstance().getgUserInfo().getServno())) {
            menus.add(BottomMenu.SIGN_DETAIL);
        }else{
            menus.add(BottomMenu.SIGN);
        }
        return menus;
    }

    private void toReview(InvActBean bean) {
        Intent mIntent = new Intent(this, InvReviewActivity.class);
        mIntent.putExtra(KEY, bean.getActvid());
        mIntent.putExtra(InvReviewActivity.KEY_TYPE, InvReViewParam.TYPE_ACT);
        startActivity(mIntent);
        overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void selectShare(InvActBean bean) {
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
        if (resultCode == REQUSETCODE && null != data) {
            InvReplysBean replysBean = (InvReplysBean) data.getSerializableExtra(KEY);
            if (null != replysBean) {
                if (currentPager <= 0) {
                    review_ly.addView(getReView(replysBean));
                } else {
                    review_ly.addView(getReView(replysBean), 0);
                }
                InvActBean bean = invActDetailResult.getDetail();
                int replyNum = bean.getReplyCount() + 1;
                invActDetailResult.getDetail().setReplyCount(replyNum);
                review_num_tv.setText("评论 " + replyNum);
                currentPager++;
            }
        }
    }

}
