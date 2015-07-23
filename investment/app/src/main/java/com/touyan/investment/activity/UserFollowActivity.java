package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.nineoldandroids.animation.ValueAnimator;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.FollowListHeadersAdapter;
import com.touyan.investment.bean.user.QueryUserFansResult;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.helper.HanziComp;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.IndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 2015/7/20.
 */
public class UserFollowActivity extends AbsActivity {

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private float EDITEXT_OFFER; //搜索 动画偏移量

    private final OvershootInterpolator mInterpolator = new OvershootInterpolator();

    private Comparator cmp = new HanziComp();

    private IndexableListView listView;

    private EditText search_et;

    private ArrayList<Subscriber> subscribers;

    private ArrayList<Subscriber> subscribersSearch;

    private FollowListHeadersAdapter mAdapter;

    private boolean isShowCancel = false;

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
        if (resposne.isSuccess()) {
            QueryUserFansResult result = (QueryUserFansResult) resposne.getData();
            subscribers = result.getSubscribers();
            hanziSequence();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void hanziSequence() {
        if (subscribers == null) {
            return;
        }
        Collections.sort(subscribers, cmp);
        initListView();
    }


    @Override
    public void EInit() {
        super.EInit();
        findView();
        init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_follow;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.user_follow);
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        listView = (IndexableListView) findViewById(R.id.listview);
        search_et = (EditText) findViewById(R.id.search_et);
        listView.setFastScrollEnabled(true);
        View ll_listEmpty = findViewById(R.id.ll_listEmpty);
        listView.setEmptyView(ll_listEmpty);

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    searchData(charSequence.toString());
                } else {
                    mAdapter.refresh(subscribers);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        search_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    if (!isShowCancel) {
                        editTextAni(true);
                        isShowCancel = true;
                    }
                }
            }
        });

    }

    // 设置输入框的动画
    private void editTextAni(final boolean is) {
        ValueAnimator animation = ValueAnimator.ofFloat(is?0:EDITEXT_OFFER, is?EDITEXT_OFFER:0);
        if(is) {
            animation.setStartDelay(400);
        }
        animation.setDuration(400);
        animation.setInterpolator(mInterpolator);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) search_et.getLayoutParams();
                int margin = (int)value;
                lp.setMargins(margin,margin,margin,0);
                search_et.setLayoutParams(lp);
            }
        });
        animation.start();
    }

    private void searchData(String sear) {
        subscribersSearch = new ArrayList<>();
        for (Subscriber bean : subscribers) {
            String name = bean.getUser().getUalias();
            boolean is = name.length() > sear.length();
            if (is) {
                if (name.contains(sear)) {
                    subscribersSearch.add(bean);
                }
            } else {
                if (sear.contains(name)) {
                    subscribersSearch.add(bean);
                }
            }
        }

        mAdapter.refresh(subscribersSearch);
    }

    // 初始化资源
    private void init() {
        EDITEXT_OFFER =  getResources().getDimension(R.dimen.content_gap);
        dialogShow();
        getDataList();
    }

    private void initListView() {

        mAdapter = new FollowListHeadersAdapter(this, subscribers);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listView));

        assert animationAdapter.getViewAnimator() != null;
        animationAdapter.getViewAnimator().setInitialDelayMillis(500);

        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);

        listView.setAdapter(stickyListHeadersAdapterDecorator);
    }

    private void getDataList() {
        UserManager manager = new UserManager();
        manager.queryUserFollow(this, App.getInstance().getgUserInfo().getServno(), activityHandler, INIT_LIST);
    }

    @Override
    public void onBackPressed() {
        if(isShowCancel){
            editTextAni(false);
            isShowCancel = false;
            search_et.clearFocus();
            return;
        }
        super.onBackPressed();
    }

}
