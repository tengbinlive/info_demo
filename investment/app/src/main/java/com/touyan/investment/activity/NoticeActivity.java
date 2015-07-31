package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.Bind;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.NoticeAdapter;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.message.InviteMessage;
import com.touyan.investment.bean.user.BatchInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.event.NewMessageEvent;
import com.touyan.investment.hx.HXCacheUtils;
import com.touyan.investment.manager.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class NoticeActivity extends AbsActivity {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.ll_listEmpty)
    LinearLayout llListEmpty;

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
            BatchInfoResult result = (BatchInfoResult) resposne.getData();
            ArrayList<UserInfo> userinfo = result.getUserinfo();
            ArrayList<GroupDetail> groupinfo = result.getGroupinfo();
            if (null != userinfo) {
                for (UserInfo userInfo : userinfo) {
                    messageHashMap.get(userInfo.getServno()).setFrom(userInfo.getUalias());
                    messageHashMap.get(userInfo.getServno()).setHeadphoto(userInfo.getUphoto());
                }
            }
            if (null != groupinfo) {
                for (GroupDetail groupDetail : groupinfo) {
                    messageHashMap.get(groupDetail.getGroupid()).setGroupName(groupDetail.getGroupname());
                    messageHashMap.get(groupDetail.getGroupid()).setHeadphoto(groupDetail.getGphoto());
                    messageHashMap.get(groupDetail.getGroupid()).setGroupId(groupDetail.getGroupid());
                }
            }
            mAdapter.refresh(new ArrayList<>(messageHashMap.values()));
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }


    @Override
    public void EInit() {
        super.EInit();
        messageHashMap = HXCacheUtils.getInstance().getInviteMessageHashMap();
        initListView();
        getDataList();
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

        mAdapter = new NoticeAdapter(this, new ArrayList<>(HXCacheUtils.getInstance().getInviteMessageHashMap().values()));

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(listview);

        listview.setAdapter(animationAdapter);

        listview.setEmptyView(llListEmpty);
    }

    private void getDataList() {
        ArrayList<InviteMessage> forms = new ArrayList<>(HXCacheUtils.getInstance().getInviteMessageHashMap().values());
        if (forms != null) {
            ArrayList<String> userinfo = new ArrayList<>();
            ArrayList<String> groupinfo = new ArrayList<>();
            for(InviteMessage im:forms){
                String groupid = im.getGroupId();
                if(StringUtil.isNotBlank(groupid)){
                    groupinfo.add(groupid);
                }else{
                    userinfo.add(im.getFrom());
                }
            }
            UserManager userManager = new UserManager();
            userManager.batchInfo(NoticeActivity.this, userinfo, groupinfo, activityHandler, LOAD_DATA);
        }
    }

    //接收到新消息
    public void onEvent(NewMessageEvent event) {
        if (!App.isConflict && !App.isCurrentAccountRemoved) {
            getDataList();
        }
    }

}
