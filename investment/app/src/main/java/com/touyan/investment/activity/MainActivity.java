package com.touyan.investment.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.util.CommonUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.MainViewPagerAdapter;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.fragment.GungFragment;
import com.touyan.investment.fragment.InvestmentFragment;
import com.touyan.investment.fragment.MeFragment;
import com.touyan.investment.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AbsActivity {
    /**
     * 两次点击返回之间的间隔时间, 这个时间内算为双击
     */
    private static final int EXIT_DOUBLE_CLICK_DIFF_TIME = 2000;

    /**
     * 记录第一次点击返回的时间戳
     */
    private long exitClickTimestamp = 0L;

    private final static int INVESTMENT = 0;//投研社
    private final static int RECOMMEND = INVESTMENT + 1;//荐股
    private final static int GUNG = RECOMMEND + 1;//拉呱
    private final static int ME = GUNG + 1;//个人

    private SmartTabLayout viewPagerTab;
    private ArrayList<AbsFragment> fragments;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doubleTouchToExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 按两次退出键才退出.
     */
    public void doubleTouchToExit() {
        long clickTime = System.currentTimeMillis();
        // 如果双击时间在规定时间范围内,则退出
        if (clickTime - exitClickTimestamp < EXIT_DOUBLE_CLICK_DIFF_TIME) {
            App.getInstance().exit();
            finish();
            System.exit(0);// 退出程序
        } else {
            exitClickTimestamp = clickTime;
            CommonUtil.showToast(R.string.press_more_times_for_exit);
        }
    }


    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private void findView() {
        fragments = new ArrayList<AbsFragment>();
        fragments.add(new InvestmentFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new GungFragment());
        fragments.add(new MeFragment());
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpager_tab);

        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.tab_main_icon, container, false);
                switch (position) {
                    case INVESTMENT:
                        setIconInfo(custom_ly,BottomMenu.INVESTMENT, true);
                        break;
                    case RECOMMEND:
                        setIconInfo(custom_ly,BottomMenu.RECOMMEND);
                        break;
                    case GUNG:
                        setIconInfo(custom_ly,BottomMenu.GUNG);
                        break;
                    case ME:
                        setIconInfo(custom_ly,BottomMenu.ME);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return custom_ly;
            }
        });
        viewPagerTab.setTabClickSelectListener(new SmartTabLayout.TabClickSelectListener() {
            @Override
            public void onSelect(View view, boolean isSelect,int position) {
                setSelectedBackground((ViewGroup) view,isSelect);
            }
        });
        viewPagerTab.setViewPager(viewPager);
    }

    private void setIconInfo(ViewGroup custom_ly, BottomMenu menu,boolean isClick) {
        ImageView icon = (ImageView) custom_ly.findViewById(R.id.menu_icon);
        TextView title = (TextView) custom_ly.findViewById(R.id.menu_title);
        title.setText(menu.getTitle());
        if (!isClick) {
            icon.setImageResource(menu.getResid_normal());
            title.setTextColor(menu.getTitle_colos_normal());
        } else {
            icon.setImageResource(menu.getResid_press());
            title.setTextColor(menu.getTitle_colos_press());
        }
        custom_ly.setTag(R.id.main_tab_menu,menu);
    }

    private void setIconInfo(ViewGroup custom_ly, BottomMenu menu) {
        setIconInfo(custom_ly, menu,false);
    }

    private void setSelectedBackground(ViewGroup custom_ly,boolean isSelect) {
        BottomMenu menu = (BottomMenu) custom_ly.getTag(R.id.main_tab_menu);
        ImageView icon = (ImageView) custom_ly.findViewById(R.id.menu_icon);
        TextView title = (TextView) custom_ly.findViewById(R.id.menu_title);
        title.setText(menu.getTitle());
        if (!isSelect) {
            icon.setImageResource(menu.getResid_normal());
            title.setTextColor(menu.getTitle_colos_normal());
        } else {
            icon.setImageResource(menu.getResid_press());
            title.setTextColor(menu.getTitle_colos_press());
        }
    }

}
