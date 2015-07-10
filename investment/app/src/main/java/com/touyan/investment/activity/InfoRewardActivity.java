package com.touyan.investment.activity;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.MoneyAdapter;
import com.touyan.investment.mview.MGridView;

public class InfoRewardActivity extends AbsActivity {

    public String currentMoney;
    private MGridView money_ly;
    private MoneyAdapter mAdapter;

    private final static String[] moneys = new String[]{"18金币", "38金币", "58金币", "88金币", "188金币", "其他金额"};


    @Override
    public void EInit() {
        super.EInit();
        findView();
        initGridView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_info_reward;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0,R.string.cancel);
        setToolbarIntermediateStrID(R.string.bottom_menu_reward);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        money_ly = (MGridView) findViewById(R.id.money_ly);

    }

    private void initGridView() {

        mAdapter = new MoneyAdapter(this, moneys);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(money_ly);

        money_ly.setAdapter(animationAdapter);

    }

}
