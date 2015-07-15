package com.touyan.investment.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InvestmentPagerAdapter;

public class MeOfferRewardActivity extends AbsActivity implements OnClickListener {

    private ViewPager viewPager;
    private InvestmentPagerAdapter adapter;
    @Override
    public void EInit() {
        super.EInit();
       // setSwipeBackEnable(false);
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_meofferreward;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.me_offer_reward);
        setToolbarRightStrID(R.string.me_offer_reward_edit);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginout_btn) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {

    }

}
