package com.touyan.investment.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.core.CommonResponse;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.FriendListHeadersAdapter;
import com.touyan.investment.adapter.GroupListAdapter;
import com.touyan.investment.manager.InvestmentManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.ArrayList;
import java.util.List;

public class GungGroupFragment extends AbsFragment {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理
    private static final int LOAD_DATA = 0x02;//加载数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private LayoutInflater mInflater;

    //列表
   // private StickyListHeadersListView listView;
    private FriendListHeadersAdapter mAdapter;

    private ArrayList<String> mList;



    private GroupListAdapter adapter = null;
    private ListView listView = null;
    private List<String> list = new ArrayList<String>();
    private List<String> listTag = new ArrayList<String>();
    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {


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
//        View ll_listEmpty = viewGroup.findViewById(R.id.ll_listEmpty);
//        listView.setEmptyView(ll_listEmpty);
        setData();
        adapter = new GroupListAdapter(getActivity(), list, listTag);
        listView = (ListView)(viewGroup).findViewById(R.id.group_list);
        listView.setAdapter(adapter);


    }

    private void initListView() {


//        FriendListHeadersAdapter mAdapter = new FriendListHeadersAdapter(getActivity(), null);
//
//        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
//
//        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
//        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listView));
//
//        assert animationAdapter.getViewAnimator() != null;
//        animationAdapter.getViewAnimator().setInitialDelayMillis(500);
//
//        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
//        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);
//
//        listView.setAdapter(stickyListHeadersAdapterDecorator);
    }


    @Override
    public void scrollToTop() {
    }

    public void setData(){
        list.add("我创建的群");
        listTag.add("我创建的群");
        for(int i=0;i<3;i++){
            list.add("浅笑无痕"+i);
        }
        list.add("我加入的群");
        listTag.add("我加入的群");
        for(int i=0;i<3;i++){
            list.add("投研社"+i);
        }
    }

    private static class GroupListAdapter extends ArrayAdapter<String>{

        private List<String> listTag = null;
        public GroupListAdapter(Context context, List<String> objects, List<String> tags) {
            super(context, 0, objects);
            this.listTag = tags;
        }

        @Override
        public boolean isEnabled(int position) {
            if(listTag.contains(getItem(position))){
                return false;
            }
            return super.isEnabled(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(listTag.contains(getItem(position))){
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_gung_group_tag, null);
            }else{
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_gung_group, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);
            textView.setText(getItem(position));
            return view;
        }
    }
}
