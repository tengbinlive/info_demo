package com.touyan.investment.mview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.touyan.investment.R;

/**
 * 投研社 - left menu
 */
public class FilterView extends PopupWindow {

    private View conentView;

    public FilterView(final Activity context,View _conentView,int aniStyle) {
        conentView = _conentView;
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(aniStyle);

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent,int x,int y) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            super.showAsDropDown(parent, x, y);
        } else {
            this.dismiss();
        }
    }
}
