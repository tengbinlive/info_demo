package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.util.CommonUtil;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.MainViewPagerAdapter;
import com.touyan.investment.enums.BottomMenu;
import com.touyan.investment.event.NewMessageEvent;
import com.touyan.investment.fragment.GungFragment;
import com.touyan.investment.fragment.InvestmentFragment;
import com.touyan.investment.fragment.MeFragment;
import com.touyan.investment.hx.HXChatManagerInit;
import com.touyan.investment.mview.BezierView;

import java.util.ArrayList;

import static com.touyan.investment.hx.HXChatManagerInit.*;

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
    private final static int GUNG = INVESTMENT + 1;//拉呱
    private final static int ME = GUNG + 1;//个人

    private final static int UPDATE_UNREADLABEL = 0x07;//更新未读消息

    private SmartTabLayout viewPagerTab;
    private ArrayList<AbsFragment> fragments;

    private BezierView message_bv;

    private int xBV;//消息数量显示描点
    private int yBV;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UNREADLABEL:
                    updateUnreadLabel();
                    break;
                default:
                    break;
            }
        }
    };

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
        message_bv = (BezierView) findViewById(R.id.message_bv);
        fragments = new ArrayList<AbsFragment>();
        fragments.add(new InvestmentFragment());
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
                        setIconInfo(custom_ly, BottomMenu.INVESTMENT, true);
                        break;
                    case GUNG:
                        setIconInfo(custom_ly, BottomMenu.GUNG);
                        break;
                    case ME:
                        setIconInfo(custom_ly, BottomMenu.ME);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return custom_ly;
            }
        });
        viewPagerTab.setTabClickSelectListener(new SmartTabLayout.TabClickSelectListener() {
            @Override
            public void onSelect(View view, boolean isSelect, int position) {
                setSelectedBackground((ViewGroup) view, isSelect);
            }
        });
        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).scrollToTop();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        message_bv.setEndOnBack(new BezierView.EndOnBack() {
            @Override
            public void endOnBack() {
                EMChatManager.getInstance().resetAllUnreadMsgCount();
                activityHandler.sendEmptyMessage(UPDATE_UNREADLABEL);
            }
        });

        activityHandler.sendEmptyMessageDelayed(UPDATE_UNREADLABEL, 300);
    }

    private void setIconInfo(ViewGroup custom_ly, BottomMenu menu, boolean isClick) {
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
        custom_ly.setTag(R.id.main_tab_menu, menu);
    }

    private void setIconInfo(ViewGroup custom_ly, BottomMenu menu) {
        setIconInfo(custom_ly, menu, false);
    }

    private void setSelectedBackground(ViewGroup custom_ly, boolean isSelect) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //接收到新消息
    public void onEvent(NewMessageEvent event) {
        if (!App.isConflict && !App.isCurrentAccountRemoved) {
            activityHandler.sendEmptyMessage(UPDATE_UNREADLABEL);
        }
    }

    /**
     * 刷新未读消息数
     */
    private void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            if (xBV <= 0) {
                int[] location = new int[2];
                View view = viewPagerTab.getTabAt(1);
                view.getLocationOnScreen(location); //获取在当前窗口内的绝对坐标
                xBV = location[0] + (view.getWidth() >> 1) + 50;
                yBV = location[1];
            }
            message_bv.setNewMessage("" + count, xBV, yBV);
            message_bv.setVisibility(View.VISIBLE);
        } else {
            message_bv.setVisibility(View.GONE);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    private int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal;
        int chatroomUnreadMsgCount = 0;
        int inviteMessageSize = getInstance().unreadNoticeCount;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount + inviteMessageSize;
    }

}
