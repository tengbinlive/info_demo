package com.touyan.investment.activity;

import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.Bind;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.MyExpandableListItemAdapter;
import com.touyan.investment.bean.message.TopMessageList;

import java.util.ArrayList;

public class NoticeActivity extends AbsActivity {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.ll_listEmpty)
    LinearLayout llListEmpty;

    @Override
    public int getContentView() {
        return R.layout.activity_notice;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.notice);
    }

    private void initListView(ArrayList<TopMessageList> list) {

        MyExpandableListItemAdapter mAdapter = new MyExpandableListItemAdapter(this, list);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(listview);

        listview.setAdapter(animationAdapter);

        listview.setEmptyView(llListEmpty);
    }

}
