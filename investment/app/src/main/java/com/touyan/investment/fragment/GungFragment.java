package com.touyan.investment.fragment;

import android.content.Intent;
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
import com.core.util.CommonUtil;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.FriendsActivity;
import com.touyan.investment.adapter.GungNewsAdapter;
import com.touyan.investment.bean.message.TopMessageListResult;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.MessageManager;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.BezierView;

import java.util.ArrayList;
import java.util.List;

public class GungFragment extends AbsFragment {

    private UserManager manager = new UserManager();

    private LayoutInflater mInflater;

    private static final int INIT_LIST = 0x01;//初始化数据处理

    //列表
    private PullToRefreshListView mListView;

    private ListView mActualListView;

    private GungNewsAdapter mAdapter;

    private SwingBottomInAnimationAdapter animationAdapter;

    private BezierView bezierview;

    private TopMessageListResult result;

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
        if (resposne.isSuccess()) {
            TopMessageListResult result = (TopMessageListResult) resposne.getData();
//            mAdapter.refresh(mArrayList);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mListView.onRefreshComplete();
    }

    private void testData() {
//        mArrayList = new ArrayList<String>();
//        mArrayList.add("");
//        mArrayList.add("");
//        mArrayList.add("");
//        mArrayList.add("");
//        mArrayList.add("");
//        mArrayList.add("");
//        mAdapter.refresh(mArrayList);
//        animationAdapter.reset();
    }


    private void initListView(View viewGroup) {

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                getDataList();
            }
        });

        mActualListView = mListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(mActualListView);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        View ll_listEmpty = viewGroup.findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);
    }

    private void getDataList() {
        manager.batchInfo(getActivity(),null,null ,activityHandler, INIT_LIST);
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
        initConversation();
    }

    private void initConversation() {
        String username = App.getInstance().getgUserInfo().getUalias();
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        List<EMMessage> messages = conversation.getAllMessages();

        mAdapter = new GungNewsAdapter(getActivity(), messages);

        animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);
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
        Util.setTextViewDrawaleAnchor(getActivity(), menuLeft, R.drawable.notice_nomral, AbsActivity.LEFT);
        Util.setTextViewDrawaleAnchor(getActivity(), toolbar_right_tv, R.drawable.group_icon, AbsActivity.RIGHT);
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

    @Override
    public void scrollToTop() {
    }
}
