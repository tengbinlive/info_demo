package com.touyan.investment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.enums.BottomMenu;

import java.util.ArrayList;

/**
 * 二级menu activity 界面
 */
public abstract class AbsDetailActivity extends AbsActivity {

    public final static String KEY_DETAIL = "KEY_DETAIL"; // 详情

    private ArrayList<BottomMenu> menEnums;

    private ArrayList<LinearLayout> menLayouts;

    private LinearLayout bottom_title_layout;

    private int currentMenuIndex = -1;

    private int menuIndex = -1;

    public abstract ArrayList<BottomMenu> getMenuEnums();

    /**
     * webview layout
     */
    public RelativeLayout webview_ly;

    private WebView mWebView;

    private Bundle mSavedInstanceState;

    /**
     * 按钮事件
     */
    public interface OnMenuButtonClick {
        /**
         * 点击事件回调
         *
         * @param view   button view
         * @param menu   button 信息
         * @param status 是否选择状态 true 被选择显示状态 ，false 否
         */
        void onClick(View view, BottomMenu menu, boolean status);
    }

    private OnMenuButtonClick onMenuButtonClick;

    public void setOnMenuButtonClick(OnMenuButtonClick onMenuButtonClick) {
        this.onMenuButtonClick = onMenuButtonClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void EInit() {
        menuIndex = getIntent().getIntExtra(KEY, -1);
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return 0;
    }


    private void findView() {
        menEnums = getMenuEnums();
        initBottomTitle();
        switchMenu(menuIndex);
    }

    private void initBottomTitle() {
        if (menEnums == null || menEnums.size() <= 0) {
            return;
        }
        bottom_title_layout = (LinearLayout) findViewById(R.id.bottom_title_layout);
        bottom_title_layout.setWeightSum(menEnums.size());
        if (bottom_title_layout == null) {
            return;
        }
        menLayouts = new ArrayList<LinearLayout>();
        for (final BottomMenu menu : menEnums) {
            LinearLayout menuView = getMenu(menu);
            menuView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != onMenuButtonClick) {
                        boolean status = (Boolean) view.getTag(R.id.main_tab_select);
                        onMenuButtonClick.onClick(view, menu, status);
                    }
                }
            });
            bottom_title_layout.addView(menuView);
            menLayouts.add(menuView);
        }
    }

    private LinearLayout getMenu(BottomMenu menu) {
        LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.tab_main_icon, bottom_title_layout, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        custom_ly.setLayoutParams(layoutParams);
        setIconInfo(custom_ly, menu, false);
        return custom_ly;
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
        custom_ly.setTag(R.id.main_tab_select, false);
    }

    /**
     * 设置按钮选择状态
     *
     * @param custom_ly 当前按钮
     * @param isSelect  是否选择  true 选择
     */
    public void setSelectedBackground(ViewGroup custom_ly, boolean isSelect) {
        BottomMenu menu = (BottomMenu) custom_ly.getTag(R.id.main_tab_menu);
        ImageView icon = (ImageView) custom_ly.findViewById(R.id.menu_icon);
        TextView title = (TextView) custom_ly.findViewById(R.id.menu_title);
        if (!isSelect) {
            icon.setImageResource(menu.getResid_normal());
            title.setTextColor(menu.getTitle_colos_normal());
            title.setText(menu.getTitle());
        } else {
            icon.setImageResource(menu.getResid_press());
            title.setTextColor(menu.getTitle_colos_press());
            title.setText(menu.getTitle_press());
        }
    }

    /**
     * 设置按钮选择状态
     *
     * @param index 排序位置 从左到右
     */
    public void switchMenu(int index) {
        if (index != -1 && currentMenuIndex != index) {
            currentMenuIndex = index;
            setBackOrTag(index);
        }
    }

    private void setBackOrTag(int index) {
        int i = 0;
        for (LinearLayout menu : menLayouts) {
            if (i == index) {
                setSelectedBackground(menu, true);
                menu.setTag(R.id.main_tab_select, true);
            } else {
                setSelectedBackground(menu, false);
                menu.setTag(R.id.main_tab_select, false);
            }
            i++;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mWebView != null) {
            mWebView.saveState(outState);
        }
    }

    @Override
    public void onDestroy() {
        if (webview_ly != null) {
            webview_ly.removeAllViews();
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
            webview_ly = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void initWebView(String url) {

        if (webview_ly == null) {
            webview_ly = new RelativeLayout(this);

            mWebView = new WebView(getApplicationContext());

            mWebView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            WebSettings setting = mWebView.getSettings();

            setting.setDefaultTextEncodingName("utf-8");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }

            if (Build.VERSION.SDK_INT >= 19) {
                setting.setLoadsImagesAutomatically(true);
            } else {
                setting.setLoadsImagesAutomatically(false);
            }

            setting.setJavaScriptEnabled(true);

            setting.setSupportZoom(true);

            setting.setBuiltInZoomControls(true);

            setting.setAppCacheEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                setting.setDisplayZoomControls(true);
            }

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (!view.getSettings().getLoadsImagesAutomatically()) {
                        view.getSettings().setLoadsImagesAutomatically(true);
                    }
                }
            });

            webview_ly.addView(mWebView);

        }
        if (null != mSavedInstanceState) {
            mWebView.restoreState(mSavedInstanceState);
        } else {
            mWebView.loadUrl(url);
        }

    }
}
