package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.core.CommonResponse;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.FriendsPagerAdapter;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.fragment.InvActFragment;
import com.touyan.investment.fragment.InvInfoFragment;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class FriendsActivity extends AbsActivity {

    private InvestmentManager manager = new InvestmentManager();

    private final static int FRIEND = 0;

    private final static int GROUP = FRIEND + 1;

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    private ViewPager viewPager;

    private FriendsPagerAdapter adapter;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        dialogDismiss();
        if (what == INIT_LIST) {
            addTestData();
        } else {
            addTestData();
        }
//        if (resposne.isSuccess()) {
//            if (what == INIT_LIST) {
//                review_ly.removeAllViews();
//                addTestData();
//             } else {
//                addTestData();
//            }
//        } else {
//            CommonUtil.showToast(resposne.getErrorTip());
//        }
    }

    private void addTestData() {
    }

    @Override
    public void EInit() {
        super.EInit();
        findView();
        getDataList(INIT_LIST);
    }


    private void getDataList(int what) {
        manager.LoginAct(this, "", "" + COUNT_MAX, activityHandler, what);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_friends;
    }

    // 初始化资源
    private void findView() {

        ArrayList<AbsFragment> fragments = new ArrayList<AbsFragment>();
        fragments.add(new InvInfoFragment());
        fragments.add(new InvActFragment());

        adapter = new FriendsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpager_tab);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                RelativeLayout custom_ly = (RelativeLayout) mInflater.inflate(R.layout.tab_friend_icon, container, false);
                switch (position) {
                    case FRIEND:
                        setIconInfo(custom_ly, BottomMenu.FRIEND, true);
                        break;
                    case GROUP:
                        setIconInfo(custom_ly, BottomMenu.GROUP, true);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return custom_ly;
            }
        });

        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setTabClickSelectListener(new SmartTabLayout.TabClickSelectListener() {
            @Override
            public void onSelect(View view, boolean isSelect, int position) {
                setSelectedBackground((ViewGroup) view, isSelect);
            }
        });
        viewPagerTab.setViewPager(viewPager);
    }

    private void setIconInfo(ViewGroup custom_ly, BottomMenu menu, boolean isClick) {
        ImageView icon = (ImageView) custom_ly.findViewById(R.id.menu_icon);
        if (!isClick) {
            icon.setImageResource(menu.getResid_normal());
        } else {
            icon.setImageResource(menu.getResid_press());
        }
        custom_ly.setTag(R.id.main_tab_menu, menu);
    }

    private void setSelectedBackground(ViewGroup custom_ly, boolean isSelect) {
        BottomMenu menu = (BottomMenu) custom_ly.getTag(R.id.main_tab_menu);
        ImageView icon = (ImageView) custom_ly.findViewById(R.id.menu_icon);
        if (!isSelect) {
            icon.setImageResource(menu.getResid_normal());
        } else {
            icon.setImageResource(menu.getResid_press());
        }
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.friends);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRight(R.drawable.friends_add);
    }

}
