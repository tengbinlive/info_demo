package com.touyan.investment.activity;

import android.view.View;
import android.widget.ListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InvActSignAdapter;

import java.util.ArrayList;

public class SignDetailActivity extends AbsActivity {

    //列表
    private ListView mList;
    private InvActSignAdapter mAdapter;

    private ArrayList<String> arrayList;

    @Override
    public void EInit() {
        super.EInit();
        findView();
        testData();
        initListView();
    }

    private void testData() {
        arrayList = new ArrayList<String>();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
    }

    private void initListView() {

        mAdapter = new InvActSignAdapter(this, arrayList);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(mList);

        mList.setAdapter(animationAdapter);

        View ll_listEmpty = findViewById(R.id.ll_listEmpty);
        mList.setEmptyView(ll_listEmpty);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_sign_detail;
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.sign_detail);
    }

    private void findView() {
        mList = (ListView) findViewById(R.id.listview);
    }

}
