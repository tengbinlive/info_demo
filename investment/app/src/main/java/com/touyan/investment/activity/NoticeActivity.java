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
import com.touyan.investment.adapter.NoticeAdapter;
import com.touyan.investment.bean.message.InviteMessage;
import com.touyan.investment.hx.HXCacheUtils;
import com.touyan.investment.manager.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class NoticeActivity extends AbsActivity {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.ll_listEmpty)
    LinearLayout llListEmpty;

    private ArrayList<InviteMessage> messageArrayList;

    private HashMap<String, InviteMessage> messageHashMap;

    private NoticeAdapter mAdapter;

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
     * 处理登陆数据
     *
     * @param resposne
     */
    private void loadData(CommonResponse resposne) {
        if (resposne.isSuccess()) {
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }


    @Override
    public void EInit() {
        super.EInit();
        initListView();
        messageHashMap = HXCacheUtils.getInstance().getInviteMessageHashMap();
        messageArrayList = new ArrayList<>(HXCacheUtils.getInstance().getInviteMessageHashMap().values());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_notice;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.notice);
    }

    private void initListView() {

        mAdapter = new NoticeAdapter(this, null);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(listview);

        listview.setAdapter(animationAdapter);

        listview.setEmptyView(llListEmpty);
    }

    private void getDataList() {
//        ArrayList<String> forms = new ArrayList<>(HXCacheUtils.getInstance().getFriendsHashMap().keySet());
//        ArrayList<String> users = new ArrayList<>();
//        if (usernames != null) {
//            UserManager userManager = new UserManager();
//            userManager.batchInfo(NoticeActivity.this, usernames, new ArrayList<String>(), activityHandler, LOAD_DATA);
//        }
    }

}
