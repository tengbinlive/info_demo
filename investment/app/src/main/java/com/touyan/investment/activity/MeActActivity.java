package com.touyan.investment.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InvestmentPagerAdapter;
import com.touyan.investment.fragment.MeActivityFragment;
import com.touyan.investment.fragment.MeActivityPartakeFragment;

import java.util.ArrayList;

public class MeActActivity extends AbsActivity implements OnClickListener {

    private ViewPager viewPager;
    private InvestmentPagerAdapter adapter;
    public final static int REWARD_MYRELEASE = 0;//我发布的
    public final static int REWARD_MYPARTAKE = REWARD_MYRELEASE + 1;//我参与的

    private int currentPager = REWARD_MYRELEASE;
    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_meofferreward;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.me_activity);
        setToolbarRightVisbility(View.VISIBLE,View.VISIBLE);
        setToolbarRightStrID(R.string.me_offer_reward_edit);
    }
    @Override
    public void onClick(View v) {

    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {

        ArrayList<AbsFragment> fragments = new ArrayList<AbsFragment>();
        fragments.add(MeActivityFragment.newsInstance(REWARD_MYRELEASE));
        fragments.add(MeActivityPartakeFragment.newsInstance(REWARD_MYPARTAKE));

        adapter = new InvestmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout)findViewById(R.id.viewpager_tab);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.tab_offerreward_icon, container, false);
                switch (position) {
                    case REWARD_MYRELEASE:
                        setIconInfo(custom_ly, R.string.me_myrelease);
                        break;
                    case REWARD_MYPARTAKE:
                        setIconInfo(custom_ly, R.string.me_mypartake);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return custom_ly;
            }
        });

        viewPagerTab.setViewPager(viewPager);

    }
    private void setIconInfo(ViewGroup custom_ly, int stringid) {
        TextView title = (TextView) custom_ly.findViewById(R.id.title);
        title.setText(stringid);
    }

}
