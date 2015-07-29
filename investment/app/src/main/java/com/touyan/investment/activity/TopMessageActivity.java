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
import com.touyan.investment.bean.message.TopMessageList;
import com.touyan.investment.bean.message.TopMessageListResult;
import com.touyan.investment.manager.MessageManager;

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
            initListView(result.getAllTopMessage());
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
