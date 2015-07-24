package com.touyan.investment.mview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.touyan.investment.R;

/**
 *
 */
public class MessageView extends Dialog {

    private View conentView;

    public MessageView(Context context) {
        super(context);
        conentView = (RelativeLayout) context.getLayoutInflater().inflate(R.layout.dialog_message, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(aniStyle);

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
