package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvActJoinUsersBean;

import java.util.ArrayList;

public class SignGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<InvActJoinUsersBean>  list;

    public SignGridAdapter(Context context,ArrayList<InvActJoinUsersBean> _list) {
        this.list = _list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void refresh(ArrayList<InvActJoinUsersBean> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_act_reward_sign, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.sign_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InvActJoinUsersBean bean = list.get(position);
        holder.textView.setText(bean.getUalias());
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }


}