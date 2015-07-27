package com.touyan.investment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
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
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.FriendsActivity;
import com.touyan.investment.adapter.GungNewsAdapter;
import com.touyan.investment.bean.message.ConversationBean;
import com.touyan.investment.bean.message.GroupDetal;
import com.touyan.investment.bean.user.BatchInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.BezierView;

import java.util.*;

public class GungFragment extends AbsFragment {

    private UserManager manager = new UserManager();

    private View rootView;

    private BezierView bezierview;

    private LayoutInflater mInflater;

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int FINISH_LIST = 0x02;//结束listview

    private TextView menuLeft;

    //列表
    private PullToRefreshListView mListView;

    private ListView mActualListView;

    private GungNewsAdapter mAdapter;

    private Hashtable<String, ConversationBean> conversationHT;

    private ArrayList<ConversationBean> conversationArray;

    private ArrayList<String> userids;

    private ArrayList<String> groupids;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj);
                    break;
                case FINISH_LIST:
                    mListView.onRefreshComplete();
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            BatchInfoResult result = (BatchInfoResult) resposne.getData();
            processData(result);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        mListView.onRefreshComplete();
    }

    private void processData(BatchInfoResult result) {
        ArrayList<UserInfo> userinfos = result.getUserinfo();
        ArrayList<GroupDetal> groupinfos = result.getGroupinfo();
        if (null != userinfos) {
            for (UserInfo userInfo : userinfos) {
                conversationHT.get(userInfo.getServno()).setObject(userInfo);
            }
        }

        if (null != groupinfos) {
            for (GroupDetal groupDetal : groupinfos) {
                conversationHT.get(groupDetal.getGroupid()).setObject(groupDetal);
            }
        }
        conversationArray = new ArrayList<>(conversationHT.values());
        refreshAdapter();
    }

    /**
     *  刷新对话列表
     */
    private void refreshAdapter() {
        conversationArray = new ArrayList<>();
        //添加消息置顶item
        conversationArray.add(new ConversationBean());
        //所有会话
        conversationArray.addAll(conversationHT.values());
        mAdapter.refresh(conversationArray);
    }


    private void initListView(View viewGroup) {

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }
        });

        mActualListView = mListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(mActualListView);

        mActualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.closeAllItems();
            }
        });

        mAdapter = new GungNewsAdapter(getActivity(), null);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mActualListView);

        mActualListView.setAdapter(animationAdapter);

        View ll_listEmpty = viewGroup.findViewById(R.id.ll_listEmpty);
        mActualListView.setEmptyView(ll_listEmpty);
    }

    private void getDataList() {
        BatchInfoResult result = manager.batchInfo(getActivity(), userids, groupids, activityHandler, INIT_LIST);
        if (null != result) {
            processData(result);
            activityHandler.sendEmptyMessageDelayed(FINISH_LIST, 1000);
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        rootView = mInflater.inflate(R.layout.fragment_gung, container, false);
        initActionBar(rootView);
        init(rootView);
        return rootView;
    }

    // 初始化资源
    private void init(View viewGroup) {
        bezierview = (BezierView) viewGroup.findViewById(R.id.bezierview);
        bezierview.setEndOnBack(new BezierView.EndOnBack() {
            @Override
            public void endOnBack() {
                clearConversation();
                updateUnreadLabel();
                refresh();
            }
        });
        mListView = (PullToRefreshListView) viewGroup.findViewById(R.id.pull_refresh_list);
        initListView(viewGroup);
        refresh();
    }

    /**
     * 获取所有会话
     *
     * @return +
     */
    private void loadConversationsWithRecentChat() {
        // 获取所有会话，包括陌生人
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
        userids = new ArrayList<>();
        groupids = new ArrayList<>();
        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
         * 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变
         * 避免并发问题
         */
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    //if(conversation.getType() != EMConversationType.ChatRoom){
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                    //}
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conversationHT = new Hashtable<>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            EMConversation conver = sortItem.second;
            if (conver.isGroup()) {
                groupids.add(conver.getUserName());
            } else {
                userids.add(conver.getUserName());
            }
            ConversationBean bean = new ConversationBean();
            bean.setConversation(sortItem.second);
            conversationHT.put(sortItem.second.getUserName(), bean);
        }
    }

    /**
     * 根据最后一条消息的时间排序
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    //接收到新消息
    public void onEvent(EMConversation event) {
        refresh();
        updateUnreadLabel();
    }

    /**
     * 刷新页面
     */
    private void refresh() {
        loadConversationsWithRecentChat();
        getDataList();
    }

    public void initActionBar(View viewGroup) {
        menuLeft = (TextView) viewGroup.findViewById(R.id.toolbar_left_btn);
        TextView toolbar_right_tv = (TextView) viewGroup.findViewById(R.id.toolbar_right_tv);
        TextView toolbar_intermediate_tv = (TextView) viewGroup.findViewById(R.id.toolbar_intermediate_tv);
        RelativeLayout menuRight = (RelativeLayout) viewGroup.findViewById(R.id.toolbar_right_btn);

        toolbar_intermediate_tv.setText(R.string.main_gung);
        menuRight.setVisibility(View.VISIBLE);
        toolbar_right_tv.setVisibility(View.VISIBLE);
        Util.setTextViewDrawaleAnchor(getActivity(), menuLeft, R.drawable.notice_nomral, AbsActivity.LEFT);
        Util.setTextViewDrawaleAnchor(getActivity(), toolbar_right_tv, R.drawable.group_icon, AbsActivity.RIGHT);
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

    /**
     * 刷新未读消息数
     */
    private void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            bezierview.setNewMessage("" + count);
            bezierview.setVisibility(View.VISIBLE);
        } else {
            bezierview.setVisibility(View.GONE);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    private int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }


    /**
     * 清除所有未读消息
     *
     * @return
     */
    private void clearConversation() {
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            conversation.resetUnreadMsgCount();
        }
    }

    @Override
    public void scrollToTop() {
        if (!App.isConflict && !App.isCurrentAccountRemoved) {
            updateUnreadLabel();
        }
    }
}
