package com.touyan.investment.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.ListView;
import com.core.CommonResponse;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.GroupListAdapter;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.manager.UserManager;

import java.util.ArrayList;
import java.util.List;

public class GungGroupFragment extends AbsFragment {

    private UserManager manager = new UserManager();
    private static final int INIT_LIST = 0x01;//初始化数据处理
    private LayoutInflater mInflater;

    private View ll_listEmpty,groupsEmpty;

    private ArrayList<String> mList;

    private GroupListAdapter adapter = null;
    private ListView listView = null;
    private List<GroupDetail> list = new ArrayList<>();
    private int Tag =-1;
    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        if (resposne.isSuccess()) {

        } else {
            ll_listEmpty.setVisibility(View.GONE);
            groupsEmpty.setVisibility(View.VISIBLE);
            listView.setEmptyView(groupsEmpty);
           // CommonUtil.showToast(resposne.getErrorTip());
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

//        manager.queryGroupsByUserId(getActivity(), activityHandler, INIT_LIST);
    }

    private void initListView() {
        registerForContextMenu(listView);
        adapter = new GroupListAdapter(mInflater, list, Tag);
        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);

    }

    @Override
    public void scrollToTop() {

    }
}
