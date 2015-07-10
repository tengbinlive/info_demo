package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.touyan.investment.R;

public class SignGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private String[] list;

    public SignGridAdapter(Context context, String[] _list) {
        this.list = _list;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.item_inv_act_reward_sign, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.sign_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list[position]);
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }


}