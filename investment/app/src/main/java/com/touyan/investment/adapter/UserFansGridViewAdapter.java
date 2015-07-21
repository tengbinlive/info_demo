package com.touyan.investment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;

import com.touyan.investment.activity.UserFansActivity;
import com.touyan.investment.bean.user.Subscriber;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/7/15.
 */
public class UserFansGridViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    private ArrayList<Subscriber> list;

    private UserFansActivity mContext;


    public UserFansGridViewAdapter(UserFansActivity context, ArrayList<Subscriber> _list) {
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

    public void refresh(ArrayList<Subscriber> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_userfans_gridview, null);
            holder = new ViewHolder();
            holder.userHead = (ImageView) convertView.findViewById(R.id.user_head);
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userName.setText(list.get(position).getUser().getUalias());
        ImageLoader.getInstance().displayImage(list.get(position).getUser().getUphoto(), holder.userHead);
        return convertView;
    }


    class ViewHolder {
        ImageView userHead;
        TextView userName;
    }
}
