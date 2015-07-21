package com.touyan.investment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import com.rey.material.widget.CheckBox;
import com.touyan.investment.R;
import com.touyan.investment.activity.UserCollectActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/17.
 */
public abstract class EditerAdapter extends BaseAdapter {

    interface OnCheckBoexListener {
        void OnCheckBoexListener(View view, int index);
    }

    private RelativeLayout.LayoutParams layoutParams;
    public ArrayList<Integer> checkedItemList;
    private Activity mContext;
    private OnCheckBoexListener checkBoexListener;
    private CheckBox mCheckBoex;

    public void setCheckBoexListener(OnCheckBoexListener checkBoexListener) {
        this.checkBoexListener = checkBoexListener;
    }

    public void setCheckBoex(CheckBox checkBoex) {
        this.mCheckBoex = checkBoex;
    }

    public EditerAdapter(Activity context) {
        mContext = context;
        checkedItemList = new ArrayList<Integer>();
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
    }

    public static final int STATE_EDIT = 0;
    public static final int STATE_REMOVE = 1;
    public static final int STATE_COMPLETE = 2;

    private int currentState = STATE_EDIT;

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public abstract View getChildView(int i, View view, ViewGroup viewGroup);

    public abstract int getCheckBoxLayout();

    public abstract ArrayList<String> getIdList();

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = getChildView(i, convertView, viewGroup);
            ViewGroup checkBoxLayout = (RelativeLayout) convertView.findViewById(getCheckBoxLayout());
            addCheckBox(checkBoxLayout);
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) checkBoxLayout.findViewById(R.id.item_checkbox);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Integer index = (Integer) compoundButton.getTag(R.id.checkbox_status);
                    if (b) {
                        if (checkedItemList.indexOf(index) == -1) {
                            checkedItemList.add(index);
                        }
                    } else {
                        checkedItemList.remove(index);
                    }

                    if (null != checkBoexListener) {
                        checkBoexListener.OnCheckBoexListener(compoundButton, index);
                    }
                }
            });
            convertView.setTag(R.id.item_checkbox, holder);
        } else {
            convertView = getChildView(i, convertView, viewGroup);
            holder = (ViewHolder) convertView.getTag(R.id.item_checkbox);
        }

        holder.checkBox.setTag(R.id.checkbox_status, i);
        if (checkedItemList.contains(i)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        switch (getCurrentState()) {
            case STATE_EDIT:
                checkedItemList = new ArrayList<Integer>();
                holder.checkBox.setVisibility(View.GONE);
                break;
            case STATE_COMPLETE:
                checkedItemList = new ArrayList<Integer>();
                holder.checkBox.setVisibility(View.VISIBLE);
                break;
            case STATE_REMOVE:
                holder.checkBox.setVisibility(View.VISIBLE);
                break;
        }
        return convertView;
    }

    private CheckBox initCheckBox() {
        if (null == this.mCheckBoex) {
            CheckBox checkBox = new CheckBox(mContext);
            checkBox.setLayoutParams(layoutParams);
            checkBox.applyStyle(R.style.RedCheckBox);
            checkBox.setId(R.id.item_checkbox);
            checkBox.setChecked(false);
            return checkBox;
        } else {
            return this.mCheckBoex;
        }
    }

    private void addCheckBox(ViewGroup layout) {
        layout.addView(initCheckBox());
    }

    public void updateEditState(int editState) {
        this.setCurrentState(editState);
            notifyDataSetChanged();
    }

    class ViewHolder {
        CheckBox checkBox;
    }

}
