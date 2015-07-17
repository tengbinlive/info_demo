package com.touyan.investment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.touyan.investment.R;
import com.touyan.investment.activity.InfoRewardActivity;
import com.touyan.investment.activity.ModifyUserTagActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/14.
 */
public class UserTagGridViewAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater mInflater;

    private String[] list;

    private ModifyUserTagActivity mContext;

    private ArrayList<Integer> selectItemList;

    private int blackColos;

    private int whiteColos;

    public ArrayList<Integer> getSelectItemList() {
        return selectItemList.size() <= 0 ? null : selectItemList;
    }

    public UserTagGridViewAdapter(ModifyUserTagActivity context, String[] _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        blackColos = context.getResources().getColor(R.color.textcolor_222);
        whiteColos = context.getResources().getColor(R.color.white);
        selectItemList = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void refresh(String[] _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_usertag_gridview, null);
            holder = new ViewHolder();
            holder.tag_bt = (Button) convertView.findViewById(R.id.tag_bt);
            holder.tag_bt.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (selectItemList.contains(position)) {
            holder.tag_bt.setSelected(true);
            holder.tag_bt.setTextColor(whiteColos);
        } else {
            holder.tag_bt.setSelected(false);
            holder.tag_bt.setTextColor(blackColos);
        }
        holder.tag_bt.setTag(R.id.button_index, position);
        holder.tag_bt.setTag(R.id.button_value, list[position]);
        holder.tag_bt.setText(list[position]);
        return convertView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tag_bt) {
            String mo = (String) view.getTag(R.id.button_value);
            int position = (Integer) view.getTag(R.id.button_index);
            if (selectItemList.contains(position)) {
                selectItemList.remove((Integer) position);
                view.setSelected(false);
                notifyDataSetChanged();
            } else {
                if (selectItemList.size() >= 3) {
                    selectItemList.remove(0);
                }
                selectItemList.add(position);
                view.setSelected(true);
                notifyDataSetChanged();
            }

        }
    }

    class ViewHolder {
        Button tag_bt;
    }
}
