package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.Bind;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.MyExpandableListItemAdapter;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.message.TopMessageList;
import com.touyan.investment.bean.message.TopMessageListResult;
import com.touyan.investment.bean.message.TopMessages;
import com.touyan.investment.manager.MessageManager;

import java.sql.Date;
import java.util.ArrayList;

public class TopMessageActivity extends AbsActivity {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.ll_listEmpty)
    LinearLayout llListEmpty;

    private final static int LOAD_DATA = 0;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 处理数据
     *
     * @param resposne
     */
    private void loadData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            TopMessageListResult result = (TopMessageListResult) resposne.getData();
            ArrayList<TopMessageList> list = new ArrayList<>();
            TopMessageList topMessageList = new TopMessageList();

            GroupDetail groupDetal = new GroupDetail();
            groupDetal.setGroupname("测试 这是一条扩展消息");
            topMessageList.setGroupDetal(groupDetal);

            ArrayList<TopMessages> topMessages = new ArrayList<>();
            for(int i = 0 ; i<1 ;i++) {
                TopMessages messages = new TopMessages();
                messages.setMtitle("测试 消息内容");
                messages.setContnt("测试 消息内容");
                messages.setToptim(new Date(System.currentTimeMillis()));
                topMessages.add(messages);
            }
            topMessageList.setTopMessages(topMessages);

            for(int i = 0 ; i<15 ;i++) {
                list.add(topMessageList);
            }
            initListView(list);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    /**
     * 获取置顶消息
     */
    private void getTopMessage() {
        dialogShow();
        MessageManager manager = new MessageManager();
        manager.topMessageList(this, activityHandler, LOAD_DATA);
    }


    @Override
    public void EInit() {
        super.EInit();
        getTopMessage();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_top_message;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.groups_top_message);
    }


    private void initListView(ArrayList<TopMessageList> list) {

        MyExpandableListItemAdapter mAdapter = new MyExpandableListItemAdapter(this, list);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(listview);

        listview.setAdapter(animationAdapter);

        listview.setEmptyView(llListEmpty);
    }
}
