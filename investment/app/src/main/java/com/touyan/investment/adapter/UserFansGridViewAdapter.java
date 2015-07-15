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


/**
 * Created by Administrator on 2015/7/15.
 */
public class UserFansGridViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    private Subscriber[] list;

    private UserFansActivity mContext;


    public UserFansGridViewAdapter(UserFansActivity context, Subscriber[] _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);

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

    public void refresh(Subscriber[] _list) {
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

        holder.userName.setText(list[position].getUser().getUalias());
        ImageLoader.getInstance().displayImage(list[position].getUser().getUphoto(), holder.userHead);
        return convertView;
    }


    class ViewHolder {
        ImageView userHead;
        TextView userName;
    }
}
