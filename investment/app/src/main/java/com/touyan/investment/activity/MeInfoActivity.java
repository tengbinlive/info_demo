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
import com.touyan.investment.adapter.EditerAdapter;
import com.touyan.investment.adapter.InvestmentPagerAdapter;
import com.touyan.investment.fragment.MeInfoFragment;

import java.util.ArrayList;

public class MeInfoActivity extends AbsActivity implements OnClickListener {

    private ViewPager viewPager;
    private InvestmentPagerAdapter adapter;
    public final static int REWARD_MYORIGINAL = 0;//原创资讯original
    public final static int REWARD_MYPURCHASE = REWARD_MYORIGINAL + 1;//purchase
    private ArrayList<AbsFragment> fragments;
    private int currentPager = REWARD_MYORIGINAL;

    public final static int EDIT_STATE_CHENGED = 100;
    public int currentEditState = EditerAdapter.STATE_EDIT;

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
        setToolbarIntermediateStrID(R.string.me_info);
        setToolbarRightVisbility(View.VISIBLE,View.VISIBLE);
        setToolbarRightStrID(R.string.me_offer_reward_edit);
        setToolbarRightOnClick(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbar_right_btn) {

            switch (currentEditState) {
                case EditerAdapter.STATE_REMOVE:
                    fragments.get(viewPager.getCurrentItem()).onActivityResult(EDIT_STATE_CHENGED, currentEditState, null);
                    changeEditState(EditerAdapter.STATE_EDIT);

                    break;
                case EditerAdapter.STATE_COMPLETE:
                    fragments.get(viewPager.getCurrentItem()).onActivityResult(EDIT_STATE_CHENGED, currentEditState, null);
                    changeEditState(EditerAdapter.STATE_EDIT);

                    break;
                case EditerAdapter.STATE_EDIT:
                    fragments.get(viewPager.getCurrentItem()).onActivityResult(EDIT_STATE_CHENGED, currentEditState, null);
                    changeEditState(EditerAdapter.STATE_COMPLETE);
                    break;
            }

        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {

        fragments = new ArrayList<AbsFragment>();
        fragments.add(MeInfoFragment.newsInstance(REWARD_MYORIGINAL));
        fragments.add(MeInfoFragment.newsInstance(REWARD_MYPURCHASE));

        adapter = new InvestmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout)findViewById(R.id.viewpager_tab);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.tab_offerreward_icon, container, false);
                switch (position) {
                    case REWARD_MYORIGINAL:
                        setIconInfo(custom_ly, R.string.me_original_info);
                        break;
                    case REWARD_MYPURCHASE:
                        setIconInfo(custom_ly, R.string.me_purchase_info);
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
               changeEditState(EditerAdapter.STATE_EDIT);
               fragments.get(i).onActivityResult(EDIT_STATE_CHENGED, EditerAdapter.STATE_COMPLETE, null);
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
    private void setIconInfo(ViewGroup custom_ly, int stringid) {
        TextView title = (TextView) custom_ly.findViewById(R.id.title);
        title.setText(stringid);
    }
    public void changeEditState(int state) {
        switch (state) {
            case EditerAdapter.STATE_REMOVE:
                currentEditState = EditerAdapter.STATE_REMOVE;
                setToolbarRightStrID(R.string.modify_delete);
                break;
            case EditerAdapter.STATE_COMPLETE:
                currentEditState = EditerAdapter.STATE_COMPLETE;
                setToolbarRightStrID(R.string.modify_complete);
                break;
            case EditerAdapter.STATE_EDIT:
                currentEditState = EditerAdapter.STATE_EDIT;
                setToolbarRightStrID(R.string.modify_userinfo_toolbar_title);
                break;
        }
    }
}
