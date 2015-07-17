package com.touyan.investment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import com.rey.material.widget.CheckBox;
import com.touyan.investment.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public abstract class EditerAdapter extends BaseAdapter {

    private CheckBox checkBox;
    private int currentState = 0;
    public ArrayList<Integer> checkedItemList;

    public EditerAdapter() {
        checkedItemList = new ArrayList<Integer>();
    }


    public static final int STATE_EDIT = 0;
    public static final int STATE_REMOVE = 1;
    public static final int STATE_COMPLETE = 2;


    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }


    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public void initCheckBox(Context mContext, ViewGroup layout) {

        checkBox = new CheckBox(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMargins(0, mContext.getResources().getDimensionPixelOffset(R.dimen.content_gap), mContext.getResources().getDimensionPixelOffset(R.dimen.content_gap), 0);
        checkBox.setLayoutParams(layoutParams);
        checkBox.applyStyle(R.style.RedCheckBox);
        checkBox.setChecked(false);
        layout.addView(checkBox, 0);


    }

    public void showCheckBox(ViewGroup layout) {
        layout.getChildAt(0).setVisibility(View.VISIBLE);
    }

    public void hideCheckBox(ViewGroup layout) {
        layout.getChildAt(0).setVisibility(View.INVISIBLE);
    }

}
