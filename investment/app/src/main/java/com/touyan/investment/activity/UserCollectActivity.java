package com.touyan.investment.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InvestmentPagerAdapter;
import com.touyan.investment.fragment.InvActFragment;
import com.touyan.investment.fragment.InvInfoFragment;
import com.touyan.investment.fragment.InvOfferFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public class UserCollectActivity extends AbsActivity {

    private LayoutInflater mInflater;

    private SmartTabLayout viewPagerTab;
    private ViewPager viewPager;
    private InvestmentPagerAdapter adapter;

    private final static int INVESTMENT_NEWS = 0;//资讯
    private final static int INVESTMENT_ACT = INVESTMENT_NEWS + 1;//活动
    private final static int INVESTMENT_OFFER = INVESTMENT_ACT + 1;//悬赏

    private int currentPager = INVESTMENT_NEWS;


    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();
        initViewPager(getSupportFragmentManager());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_collect;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.user_collect);
        setToolbarRight(R.string.modify_userinfo_toolbar_title);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpager_tab);
    }

    private void initViewPager(FragmentManager fm) {
        ArrayList<AbsFragment> fragments = new ArrayList<AbsFragment>();
        fragments.add(new InvInfoFragment());
        fragments.add(new InvActFragment());
        fragments.add(new InvOfferFragment());

        adapter = new InvestmentPagerAdapter(fm, fragments);

        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.tab_investment_icon, container, false);
                switch (position) {
                    case INVESTMENT_NEWS:
                        setIconInfo(custom_ly, R.string.investment_info, R.string.investment_info_es);
                        break;
                    case INVESTMENT_ACT:
                        setIconInfo(custom_ly, R.string.investment_act, R.string.investment_act_es);
                        break;
                    case INVESTMENT_OFFER:
                        setIconInfo(custom_ly, R.string.investment_offer, R.string.investment_offer_es);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return custom_ly;
            }
        });

        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPager = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setIconInfo(ViewGroup custom_ly, int stringid, int es_stringid) {
        TextView title = (TextView) custom_ly.findViewById(R.id.title);
        TextView title_es = (TextView) custom_ly.findViewById(R.id.title_es);
        title.setText(stringid);
        title_es.setText(es_stringid);
    }
}
