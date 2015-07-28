package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import butterknife.*;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.Log;
import com.core.util.StringUtil;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.nineoldandroids.animation.ValueAnimator;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.AddFriendListHeadersAdapter;
import com.touyan.investment.adapter.FollowListHeadersAdapter;
import com.touyan.investment.adapter.FriendListHeadersAdapter;
import com.touyan.investment.bean.main.InvOfferBean;
import com.touyan.investment.bean.user.*;
import com.touyan.investment.event.OnContactDeletedEvent;
import com.touyan.investment.helper.HanziComp;
import com.touyan.investment.helper.UserInfoComp;
import com.touyan.investment.hx.HXUserUtils;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.IndexableListView;
import com.touyan.investment.mview.PullToRefreshIndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2015/7/23.
 */
public class AddFriendActivity extends AbsActivity {
    UserManager userManager = new UserManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int FRIEND_DATA = 0x03;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.inviteBtn)
    LinearLayout inviteBtn;
    @Bind(R.id.knowBtn)
    LinearLayout knowBtn;
    @Bind(R.id.createGroupBtn)
    LinearLayout createGroupBtn;
    @Bind(R.id.recommendGroupBtn)
    LinearLayout recommendGroupBtn;
    @Bind(R.id.ptflistview)
    PullToRefreshIndexableListView ptflistview;
    @Bind(R.id.listview_empty)
    LinearLayout listviewEmpty;

    IndexableListView listview;

    private float EDITEXT_OFFER; //搜索 动画偏移量

    private final OvershootInterpolator mInterpolator = new OvershootInterpolator();

    private Comparator cmp = new UserInfoComp();

    private ArrayList<UserInfo> friends;

    private ArrayList<UserInfo> friendsSearch;

    private List<String> usernames = null;

    private AddFriendListHeadersAdapter mAdapter;

    private boolean isShowCancel = false;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                case FRIEND_DATA:
                    loadFriendData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private String key;

    private void loadData(CommonResponse resposne, int what) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            if (what == INIT_LIST) {
                SearchUserResult result = (SearchUserResult) resposne.getData();
                friendsSearch = result.getUsers();
            } else {
                if (friendsSearch == null) {
                    friendsSearch = new ArrayList<UserInfo>();
                }
                friendsSearch.addAll(((SearchUserResult) resposne.getData()).getUsers());
            }
            friendsSearch.removeAll(friends);
            hanziSequence();
            mAdapter.refresh(friendsSearch);
        } else {
            //避免第一次应用启动时 创建fragment加载数据多次提示

            CommonUtil.showToast(resposne.getErrorTip());

        }
        ptflistview.onRefreshComplete();
    }

    private void loadFriendData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            BatchInfoResult result = (BatchInfoResult) resposne.getData();
            friends = result.getUserinfo();
            hanziSequence();
            mAdapter.refresh(friends);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void hanziSequence() {
        if (friendsSearch == null) {
            return;
        }
        Collections.sort(friendsSearch, cmp);

    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        initPTFListView();
        findView();

    }

    @Override
    public int getContentView() {
        return R.layout.activity_add_friend;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.add);
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    private void initPTFListView() {

        ptflistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<IndexableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<IndexableListView> refreshView) {
                friendsSearch = null;
                searchData(key);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<IndexableListView> refreshView) {
                searchData(key);
            }
        });

    }

    private void findView() {
        EDITEXT_OFFER = getResources().getDimension(R.dimen.content_gap);
        listview = ptflistview.getRefreshableView();
        listview.setFastScrollEnabled(true);
        listview.setEmptyView(listviewEmpty);
        initListView();
        getDataList();
    }


    @OnTextChanged(R.id.search_et)
    void onTextChanged(CharSequence charSequence) {
        key = charSequence.toString();
        if (!TextUtils.isEmpty(charSequence)) {
            searchData(charSequence.toString());
        } else {
            hanziSequence();
            friendsSearch = new ArrayList<>();
            mAdapter.refresh(friendsSearch);
        }

    }

    private void searchData(String sear) {
        if (StringUtil.isBlank(sear)) {
            return;
        }
        int startIndex = friendsSearch == null || friendsSearch.size() <= 0 ? 0 : friendsSearch.size();
        userManager.searchUsers(this, sear, startIndex, COUNT_MAX, activityHandler, startIndex == 0 ? INIT_LIST : LOAD_DATA);

    }

    @OnFocusChange(R.id.search_et)
    void onFocusChanged(boolean focused) {
        if (focused) {
            if (!isShowCancel) {
                editTextAni(true);
                isShowCancel = true;
            }
        } else {

        }
    }

    @OnClick({R.id.inviteBtn, R.id.knowBtn, R.id.createGroupBtn, R.id.recommendGroupBtn})
    void onClick(View view) {
        int id = view.getId();
        if (id == R.id.inviteBtn) {
            Intent intent = new Intent(this, InviteContactsActivity.class);
            startActivity(intent);
        } else if (id == R.id.knowBtn) {
            Intent intent = new Intent(this, AddfriendMayKnowActivity.class);
            startActivity(intent);
        } else if (id == R.id.createGroupBtn) {
            Intent intent = new Intent(this, CreateGroupActivity.class);
            startActivity(intent);
        } else if (id == R.id.recommendGroupBtn) {
            Intent intent = new Intent(this, HotGroupRecoActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    // 设置输入框的动画
    private void editTextAni(final boolean is) {
        ValueAnimator animation = ValueAnimator.ofFloat(is ? 0 : EDITEXT_OFFER, is ? EDITEXT_OFFER : 0);
        if (is) {
            animation.setStartDelay(400);
        }
        animation.setDuration(400);
        animation.setInterpolator(mInterpolator);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) searchEt.getLayoutParams();
                int margin = (int) value;
                lp.setMargins(margin, margin, margin, 0);
                searchEt.setLayoutParams(lp);
            }
        });
        animation.start();
    }

    @Override
    public void onBackPressed() {
        if (isShowCancel) {
            editTextAni(false);
            isShowCancel = false;
            searchEt.clearFocus();
            searchEt.setText("");
            return;
        }
        super.onBackPressed();
    }

    private void initListView() {
        friendsSearch = new ArrayList<>();

        mAdapter = new AddFriendListHeadersAdapter(this, friendsSearch);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listview));

        assert animationAdapter.getViewAnimator() != null;
        animationAdapter.getViewAnimator().setInitialDelayMillis(500);

        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);

        listview.setAdapter(stickyListHeadersAdapterDecorator);
    }


    private void getDataList() {

        usernames = new ArrayList<>(HXUserUtils.getInstance().getFriendsHashMap().keySet());

        if (usernames != null) {

            BatchInfoResult result = userManager.batchInfo(AddFriendActivity.this, (ArrayList<String>) usernames, new ArrayList<String>(), activityHandler, FRIEND_DATA);
            if (result != null) {
                dialogDismiss();
                friends = result.getUserinfo();
                hanziSequence();
                mAdapter.refresh(friends);
            }

        }

    }


}
