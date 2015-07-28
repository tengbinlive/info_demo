package com.touyan.investment.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.ListView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.GroupListAdapter;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.user.BatchInfoResult;
import com.touyan.investment.hx.HXCacheUtils;
import com.touyan.investment.manager.UserManager;

import java.util.ArrayList;
import java.util.List;

public class GungGroupFragment extends AbsFragment {
    UserManager userManager = new UserManager();
    private static final int LOAD_DATA = 0x02;//加载数据处理
    private LayoutInflater mInflater;

    private View ll_listEmpty,groupsEmpty;

    private GroupListAdapter adapter = null;
    private ListView listView = null;
    private ArrayList<GroupDetail> list = new ArrayList<>();
    private List<String> groupids = null;
    private int Tag =-1;
    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        if (resposne.isSuccess()) {
            BatchInfoResult result = (BatchInfoResult) resposne.getData();
            List<GroupDetail> listGroups = result.getGroupinfo();
            sortDate(listGroups);
            hanziSequence();
        } else {
            ll_listEmpty.setVisibility(View.GONE);
            groupsEmpty.setVisibility(View.VISIBLE);
            listView.setEmptyView(groupsEmpty);
            //CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        View viewGroup = mInflater.inflate(R.layout.fragment_gung_group, container, false);
        init(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // 初始化资源
    private void init(View viewGroup) {
        listView = (ListView)viewGroup.findViewById(R.id.group_list);
        groupsEmpty = viewGroup.findViewById(R.id.group_listEmpty);
        groupsEmpty.setVisibility(View.GONE);
        ll_listEmpty = viewGroup.findViewById(R.id.ll_listEmpty);
        listView.setEmptyView(ll_listEmpty);

        getGroupsList();//发送请求
    }

    private void getGroupsList(){

        groupids = new ArrayList<>(HXCacheUtils.getInstance().getGroupsHashMap().keySet());
        if (groupids != null) {
            BatchInfoResult result = userManager.batchInfo(getActivity(), new ArrayList<String>(),(ArrayList<String>) groupids, activityHandler, LOAD_DATA);
//            if (result != null) {
//                List<GroupDetail> listGroups = result.getGroupinfo();
//                sortDate(listGroups);
//                hanziSequence();
//            }

        }

    }
    private void hanziSequence() {
        if (list == null) {
            return;
        }
        initListView();
    }
    private void sortDate(List<GroupDetail> listGroups){
        ArrayList<GroupDetail> myCreateGroups = new ArrayList<GroupDetail>();
        ArrayList<GroupDetail> myJoinedGroups = new ArrayList<GroupDetail>();

        for (int i=0 ;i<listGroups.size();i++){
            if (listGroups.get(i).getOwner().equals(App.getInstance().getgUserInfo().getServno())){
                myCreateGroups.add(listGroups.get(i));
            }else {
                myJoinedGroups.add(listGroups.get(i));
            }
        }
        if (myCreateGroups.size()>0){
            GroupDetail groupDetal1= new GroupDetail();
            groupDetal1.setGroupname("我创建的群");
            list.add(groupDetal1);
            list.addAll(myCreateGroups);
        }
        if (myJoinedGroups.size()>0){
            GroupDetail groupDetal2= new GroupDetail();
            groupDetal2.setGroupname("我加入的群");
            list.add(groupDetal2);
            list.addAll(myJoinedGroups);
            Tag = myCreateGroups.size();
        }

        if (list.size()<=0){
            ll_listEmpty.setVisibility(View.GONE);
            groupsEmpty.setVisibility(View.VISIBLE);
            listView.setEmptyView(groupsEmpty);
        }else {
            initListView();
        }
    }

    private void initListView() {

        if (adapter==null) {
            registerForContextMenu(listView);
            adapter = new GroupListAdapter(mInflater, list, Tag);
            SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(adapter);
            animationAdapter.setAbsListView(listView);
            listView.setAdapter(animationAdapter);
        }
        else{
            adapter.refresh(list);
        }
    }

    @Override
    public void scrollToTop() {

    }
}
