package com.touyan.investment.activity;

import android.support.v4.view.ViewPager;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.GuideViewPagerAdapter;
import com.touyan.investment.fragment.GuideFragment;

import java.util.ArrayList;

public class GuideActivity extends AbsActivity {

    private GuideViewPagerAdapter adapter;

    private int mPosition;

    private int mState = -1;

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        mState = -1;
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }

    private void findView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        // init view pager
        ArrayList<AbsFragment> fragments = new ArrayList<AbsFragment>();
        fragments.add(GuideFragment.newInstance(R.drawable.guide_01));
        fragments.add(GuideFragment.newInstance(R.drawable.guide_02));
        fragments.add(GuideFragment.newInstance(R.drawable.guide_03));
        fragments.add(GuideFragment.newInstance(R.drawable.guide_04));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mState < 0) {
                    mState = state;
                }
                if ((mPosition == adapter.getCount() - 1) && state == 0 && mState > 0) {
                    mState = state;
                    toLogining();
                }
            }
        });

        adapter = new GuideViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }


    private void toLogining() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.fade, R.anim.launcher_out);
    }

}
