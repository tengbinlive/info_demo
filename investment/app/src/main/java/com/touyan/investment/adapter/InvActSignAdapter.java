package com.touyan.investment.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.UserFansDetailsActivity;
import com.touyan.investment.bean.main.InvActJoinUsersBean;

import java.util.ArrayList;

public class InvActSignAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<InvActJoinUsersBean> list;

    private Activity mContext;

    public InvActSignAdapter(Activity context, ArrayList<InvActJoinUsersBean> _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
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
            convertView = mInflater.inflate(R.layout.item_inv_act_sign, null);
            holder = new ViewHolder();
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    InvActJoinUsersBean bean = list.get(position);
                    UserFansDetailsActivity.toOthersDetail(mContext, App.getInstance().getgUserInfo().getServno(), bean.getServno());
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InvActJoinUsersBean bean = list.get(position);
        holder.head.setTag(position);
        ImageLoader.getInstance().displayImage(bean.getUphoto(), holder.head);
        holder.name.setText(bean.getUalias());
        return convertView;
    }

    class ViewHolder {
        SelectableRoundedImageView head;
        TextView name;
    }

}